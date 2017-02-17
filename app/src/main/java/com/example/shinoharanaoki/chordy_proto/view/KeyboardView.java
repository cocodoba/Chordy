package com.example.shinoharanaoki.chordy_proto.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.shinoharanaoki.chordy_proto.data.Chord;
import com.example.shinoharanaoki.chordy_proto.data.Scalable;
import com.example.shinoharanaoki.chordy_proto.data.Scale;
import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;
import com.example.shinoharanaoki.chordy_proto.factory.ScaleFactory;
import com.example.shinoharanaoki.chordy_proto.kenban.BlackKenban;
import com.example.shinoharanaoki.chordy_proto.kenban.Kenban;
import com.example.shinoharanaoki.chordy_proto.factory.SoundLoader;
import com.example.shinoharanaoki.chordy_proto.kenban.KenbanNotesInitializer;
import com.example.shinoharanaoki.chordy_proto.kenban.WhiteKenban;
import com.example.shinoharanaoki.chordy_proto.util.ArrayUtil;

import static com.example.shinoharanaoki.chordy_proto.util.DegreeUtil.*;

import java.util.ArrayList;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class KeyboardView extends View{

    private static final String TAG = KeyboardView.class.getSimpleName();
    private final KeyboardView self = this;

    public static final int MODE_SCALE = 0;
    public static final int MODE_CHORD = 1;
    public static final int MODE_CHORD_ON_SCALE = 2;
    private int usageMode = MODE_SCALE;

    public static final boolean DISPLAY_ONTOUCH = false;
    public static final boolean DISPLAY_ALWAYS = true;
    private boolean dropMarkDisplayMode = DISPLAY_ONTOUCH;

    public static final int NOTENAME = 0;
    public static final int DEGREE = 1;
    private int stringOnKenban = NOTENAME;
    private int stringOnDropMark = DEGREE;

    private Kenban[] kenbans;
    private int keyboardSize = 72;//Kenbanの総数
    private float initX = 100; //キーボードの一番左端として指定する座標
    private float initY = 185; //キーボードの一番下辺として指定する座標
    private int pitchOffset = 0; //例えば本来CのKenbanをBbやEbにするような場合に指定

    //TEST ChordSequenceViewに移動？
    private Chord[] chordSequence;

    private SoundLoader soundLoader;
    private SoundPool soundPool;

    public KeyboardView(Context context){
        super(context);
        initialize(context);
    }

    /**レイアウトファイルの中にViewをはめ込む場合はAttributeSetが必要*/
    public KeyboardView(Context context, AttributeSet attrs){
        super(context, attrs);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initialize(Context context) {

        /**
         * 初期化その1 Kenbanをキーボードとして並べる
         */
        kenbans = createKeyboard(keyboardSize, initX, initY);

        /**
         * 初期化その2 各Kenbanに音を割り当てる
         */
        soundLoader = new SoundLoader(context);
        soundPool = soundLoader.getSoundPool();

        /**
         * 初期化その3 各Kenbanが表示しうる音名を各Kenbanに設定
         *
         *   ArrayListを12キー分つくる
         *   12キー毎に合致するArrayListの参照を渡すー72Kenban分
         */

        ArrayList<Note>[] noteArrays;
        KenbanNotesInitializer initializer = new KenbanNotesInitializer(pitchOffset);
        noteArrays = initializer.getNotes();
        initializer.setNotesOnKenbans(kenbans, noteArrays);

        /**
         * 初期化その4(最後) とりあえず全体表示スケールのデフォルト値をCとして、各Kenbanに
         * Cメジャースケールに対応した音名を表示させる
         */
        ScaleFactory sf = new ScaleFactory();
        Scale scale = sf.getScale(Note.C,ScaleType.MAJOR);
        resetFullKeyboardState(scale);

        /**AndroidStudioのプレビュー画面でのエラー回避*/
        if (isInEditMode()) {
            // 編集モードだったら処理終了
            return ;
        }
    }

    //キーボード上の全てのKenbanの状態を変更する
    //TODO 特定の範囲のKenbanだけを対象にできるようにする
    public void resetFullKeyboardState(Scalable scaleOrChord){

        int offset = 0;
        Note tonica = scaleOrChord.getTonica();
        ScaleType scaleType = scaleOrChord.getScaleType();

        //処理の基準になるKenbanを探す
        int firstKenbanPosition =getFirstKenbanPositionByNote(kenbans, scaleOrChord.getTonica());
        offset = firstKenbanPosition;  //13 - firstKenbanPosition;から変更
        setKeyboardScaleDegrees(scaleOrChord, offset);
        setKeyboardSignatures(scaleOrChord, offset);
        if(scaleOrChord instanceof Scale) {
            for(Kenban kenban : kenbans){
                kenban.reloadNoteNameOnScale();
            }
            setScaleNotes(scaleOrChord.getDegrees());
        }
        if(scaleOrChord instanceof Chord){
            setChordTones(scaleOrChord.getDegrees());
            for(Kenban kenban : kenbans){
                kenban.reloadNoteNameOnChord();
            }
        }


        postInvalidate();
    }

    //検索用
    //ここで設定した度数を使い、タッチ時や再生時に該当するKenbanの検索を行う
    private void setKeyboardScaleDegrees(Scalable scaleOrChord, int Offset){
        //すべてのKenbanに現在のスケール\に応じた度数を振る
        //TODO 特定の範囲のKenbanだけを対象にできるようにする
        //完全1度から長7度までの12音分の度数を用意 ここではテンション等9度以上は無視
        int[] degreeArray =
                ArrayUtil.getLoopNumbersWithOffset(P1,M7, Offset, keyboardSize);

        //用意した度数の並びをKenbanに順番に振る
        int i = 0;
        if(scaleOrChord instanceof Scale) {
            for (Kenban kenban : kenbans) {
                kenban.degreeOnScale = degreeArray[i++];
            }
        }
        if(scaleOrChord instanceof Chord){
            for (Kenban kenban : kenbans) {
                kenban.degreeOnChordScale = degreeArray[i++];
            }
        }
    }

    public void onPlay(){

    }

    //!!表示用
    //ここで設定した♭♯♮をもとに各Kenbanが表示する音名が決定される
    public void setKeyboardSignatures(Scalable scaleOrChord, int offset){
        //すべてのKenbanに対し、どの音名を表示させるかを決定
        //TODO 特定の範囲のKenbanだけを対象にできるようにする
        int[] signaturesArray =
                ArrayUtil.changeStartPointOnArray(scaleOrChord.getSignatures(), offset, keyboardSize);
        //用意した♭♯♮定数を各Kenbanに振る
        int i = 0;
        if(scaleOrChord instanceof Scale) {
            for (Kenban kenban : kenbans) {
                kenban.scaleSignature = signaturesArray[i++];
            }
        }
        if(scaleOrChord instanceof Chord){
            for (Kenban kenban : kenbans) {
                kenban.chordScaleSignature = signaturesArray[i++];
            }
        }
    }

    private void setScaleNotes(int[] scaleNoteDegrees){
        for (Kenban kenban : kenbans){
            for(int degree : scaleNoteDegrees){
                if(kenban.degreeOnScale == degree){
                    kenban.isScaleNote = true;
                }
            }
        }
    }

    private void setChordScaleNote(int[] chordScaleNoteDegrees){
        for (Kenban kenban : kenbans){
            for(int degree : chordScaleNoteDegrees){
                if(kenban.degreeOnChordScale == degree){
                    kenban.isChordScaleNote = true;
                }
            }
        }
    }

    private void setChordTones(int[] chordToneDegrees){
        for (Kenban kenban : kenbans){
            for(int degree : chordToneDegrees){
                if(kenban.degreeOnChordScale == degree){
                    kenban.isChordTone = true;
                }
            }
        }
    }

    //指定した音名をもつKenbanのうち一番最初のKenbanの順位を取得する
    private int getFirstKenbanPositionByNote(Kenban[] kenbans, Note note)
            throws NullPointerException{

        Kenban correspondingKenban = null;
        loop:
        for(Kenban kenban : kenbans){
            ArrayList<Note> noteList = kenban.noteNameList;
            if(noteList.contains(note)){
                correspondingKenban = kenban;
                break loop;  //最初にKenbanが見つかった時点でForループを終了する
            }
        }
        if(correspondingKenban == null){
            throw new NullPointerException(
                    "指定した音名'"+note.toString()+"'を持つKenbanオブジェクトが見つかりませんでした");
        }
        return correspondingKenban.position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Kenban, Stroke, String, Edge, DropMark を一括で描画
        for(Kenban kenban : this.kenbans) {
            kenban.draw(canvas, stringOnDropMark);
        }
    }

    boolean now_moving = false;
    float down_x;
    float down_y;
    float threshold = 10; //TODO user setting

    private SparseArray<PointF> mActivePointers = new SparseArray<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get pointer index from the event object
        int pointer_index = event.getActionIndex();
        // get pointer ID
        int pointerId = event.getPointerId(pointer_index);
        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        PointF touch_point;
        PointF release_point;
        float pressure;

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
                touch_point = new PointF();
                touch_point.x = event.getX(pointer_index);
                touch_point.y = event.getY(pointer_index);
                pressure = event.getPressure(pointer_index);
                mActivePointers.put(pointerId, touch_point);

                for (Kenban kenban : kenbans) {
                    if(kenban.checkTouch(touch_point)){
                        Log.d(TAG, "onTouchEvent: KeyBoard[" + kenban.position + "] is touched");
                        kenban.isOnPlay = true;
                        soundPool.play(kenban.position, 1.0f, 1.0f, 0, 0, 1);
                        break;
                    }
                    down_x = touch_point.x;
                    down_y = touch_point.y;
                    now_moving = true;
                }
                Log.d(TAG, "onTouchEvent: ACTION_DOWN Touch Pressure = "+ pressure );
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                // We have a new pointer. Lets add it to the list of pointers
                touch_point = new PointF();
                touch_point.x = event.getX(pointer_index);
                touch_point.y = event.getY(pointer_index);
                pressure = event.getPressure(pointer_index);
                mActivePointers.put(pointerId, touch_point);

                now_moving = true;
                for (Kenban kenban : kenbans) {
                    if(kenban.checkTouch(touch_point)){
                        Log.d(TAG, "onTouchEvent: KeyBoard[" + kenban.position + "] is touched");
                        kenban.isOnPlay = true;
                        soundPool.play(kenban.position, 1.0f, 1.0f, 0, 0, 1);
                        break;
                    }
                }
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN Touch Pressure = "+ pressure );
                break;

            case MotionEvent.ACTION_POINTER_UP:

                release_point = mActivePointers.get(event.getPointerId(pointer_index));
                if (release_point != null) {
                    release_point.x = event.getX(pointer_index);
                    release_point.y = event.getY(pointer_index);

                    for (Kenban kenban : kenbans) {
                        if (kenban.checkTouch(release_point)) {
                            Log.d(TAG, "onTouchEvent ACTION_POINTER_UP: " +
                                    "Keyboard[" + kenban.position + "] is released");
                            kenban.isOnPlay = false;
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_CANCEL: //何もしない


            case MotionEvent.ACTION_UP:

                release_point = mActivePointers.get(event.getPointerId(pointer_index));
                if (release_point != null) {
                    release_point.x = event.getX(pointer_index);
                    release_point.y = event.getY(pointer_index);

                    for (Kenban kenban : kenbans) {
                        if (kenban.checkTouch(release_point)) {
                            Log.d(TAG, "onTouchEvent ACTION_UP: " +
                                    "Keyboard[" + kenban.position + "] is released");
                            kenban.isOnPlay = false;
                            now_moving = false;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:

                PointF point = mActivePointers.get(event.getPointerId(pointer_index));
                float x_moving = event.getX(pointer_index);

                if (now_moving) {
                    if(point.x<=x_moving) {
                        for (Kenban kenban : kenbans) {
                            kenban.centerX += x_moving-point.x;
                        }
                    }else {
                        for (Kenban kenban : kenbans) {
                            kenban.centerX -= point.x-x_moving;
                        }
                    }
                    point.x = x_moving;
                    now_moving = true;
                }

                break;

            default:
                assert true;    // 何もしない
                break;
        }

        /**画面を更新する
         *
         * View # invalidate()は，メインスレッドがアイドル状態になり次第、
         * ビューがonDraw()経由で再描画されるように，システムにお願いする
         * */
        invalidate();    // (13)

        return true;    // (14)
    }

    private final int[] alignment_pattern = {5, 7}; //C~Eまでが5個、F~Bまでが７個

    public Kenban[] createKeyboard(int KeyBoardSize, float leftX, float bottomY){

        //TODO Kenban一個ずつの大きさ、縦横比を設定するメソッドを別に作る

        float init_x = leftX; //キーボードの一番左端として指定する座標
        float init_y = bottomY; //キーボードの一番下辺として指定する座標
        float upper_y = 100; //上の段の高さを設定
        float x_gap = 105; //Kenban間の横の間隔
        //int color = Color.BLUE; //Kenban自身で設定するから要らない
        boolean pattern_CtoE = true;

        /*TODO
        alignment_pattern = {5, 7}を消し、以下に変更する

        int patternCtoE = 5;
        int patternFtoB = 7;
        */

        int count_align = 0; //CtoE(1~5), FtoB(1~7)のうち今何個目まで作ったか

        //FIXME 生成されるKenbanインスタンスの数が配列の長さより９個くらいなぜか多くなってしまう

        Kenban[] kenbans = new Kenban[KeyBoardSize];

        float y = upper_y;

        for (int position = 0; position< keyboardSize; position++) {
            /**上下交互に並べる*/
            if (y != init_y) { /*下の段(白鍵)*/
                y = init_y;
                kenbans[position] = new WhiteKenban(init_x, y, position);
            } else { /*上の段(黒鍵)*/
                y = upper_y;
                kenbans[position] = new BlackKenban(init_x, y, position);
            }
            init_x += x_gap/2; //間隔
            count_align++;

            if(pattern_CtoE && count_align==alignment_pattern[0]){
                /*下の段のパターンの最後(E)*/
                kenbans[position] = new WhiteKenban(init_x, y, position);
                y = upper_y;
                init_x +=x_gap/2;
                pattern_CtoE = false;
                count_align=0;
            }
            if(!pattern_CtoE && count_align==alignment_pattern[1]){
                /*下の段のパターンの最後(B)*/
                kenbans[position] = new WhiteKenban(init_x, y, position);
                y = upper_y;
                init_x +=x_gap/2;
                pattern_CtoE = true;
                count_align=0;
            }
        }


        /*TODO 上のコードを以下のように作り変える


            if(nowPattern == patternCtoE){

                for (int position = 0; position< keyboardSize; position++) {
                    *//**上下交互に並べる*//*
                    if (y != initY) { *//*下の段*//*
                        y = initY;
                        //kana = noteName[n];
                        //n++;
                    } else { *//*上の段*//*
                        y = upper_y;
                        //kana = "";
                    }
                    initX += x_gap/2; //間隔
                    kenbans = new Kenban(initX, y, position,kana);
                    count_align++;
            }
            if(nowPattern = patternFtoB){

                for (int position = 0; position< keyboardSize; position++) {
                    *//**上下交互に並べる*//*
                    if (y != initY) { *//*下の段*//*
                        y = initY;
                        //kana = noteName[n];
                        //n++;
                    } else { *//*上の段*//*
                        y = upper_y;
                        //kana = "";
                    }
                    initX += x_gap/2; //間隔
                    kenbans = new Kenban(initX, y, position,kana);
                    count_align++;
            }
        }*/

        return kenbans;
    }
    
}
