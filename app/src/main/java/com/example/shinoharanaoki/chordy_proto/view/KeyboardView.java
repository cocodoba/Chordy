package com.example.shinoharanaoki.chordy_proto.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.shinoharanaoki.chordy_proto.Chord;
import com.example.shinoharanaoki.chordy_proto.Kenban;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class KeyboardView extends View implements Runnable{

    private static final String TAG = KeyboardView.class.getSimpleName();
    private final KeyboardView self = this;

    private Context context;

    private float touch_x;    // 画面のタッチされた X 座標    // (1)
    private float touch_y;    // 画面のタッチされた Y 座標    // (2)

    private Canvas canvas;
    private Paint paint_ball;
    private Paint paint_ball_number;

    private Kenban[] kenban;
    private int keyboard_size = 72;

    //TEST
    private Chord[] chord_sequence;
    //TEST
    private String[] note_name = {"C","D","E","F","G","A","B"};
    //TEST
    public int nowKey = C;
    private int[] now_key_scale;
    public String nowKeyString;

    public KeyboardView(Context context){
        super(context);
        this.context = context;
        initialize();
    }

    /**レイアウトファイルの中にViewをはめ込む場合はこのコンストラクタが必要*/
    public KeyboardView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initialize() {

        kenban = new Kenban[keyboard_size];

        nowKeyString = "C major";

        createKeyboard(kenban);

        /*鍵盤ごとの絶対音を指定*/
        //TODO CからBbなどに変更できるようにoffset値を用意する
        int num = 0;
        int hight = 1;
        for (int position = 0; position< keyboard_size; position++) {
            kenban.absolute_note_name = num;
            kenban.octave_hight = hight;
            if (num<OCTAVE) {
                num++;
            }else if(num==OCTAVE){
                kenban.absolute_note_name = num;
                kenban.octave_hight = hight;
                num = 0;
                hight++;
            }
            Log.i(TAG, "initialize: kenban["+position+"] ... octave_hight= "+ kenban.octave_hight + "  " +
                    "  absolute_note_name="+ kenban.absolute_note_name);
        }

        now_key_scale = Scale.getScale(); //TODO パラメータ: major.minor...

        /*キーの初期値を指定してKeyBoardに反映*/
        setScaleLabelOnCurrentKey();

        /**AndroidStudioのプレビュー画面でのエラー回避*/
        if (isInEditMode()) {
            // 編集モードだったら処理終了
            return ;
        }

        audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて(同時に鳴らせる音の数)
                .setMaxStreams(8)
                .build();

        // wav をロードしておく
        sounds = setSoundPool();
    }

    


    public void setScaleLabelOnCurrentKey(){
        for (int position = 0; position< keyboard_size; position++) {
            kenban.is_scale_note = false;
            kenban.position_from_tonic = Scale.getKeyPositionFromTonic(nowKey, kenban.absolute_note_name);
            kenban.degree_name_on_key = Scale.getKeyDegreeFromTonic(kenban.position_from_tonic);
            //TODO
            //kenban.indicator_on_key = Scale.getActualNoteIndicator(Scale.keyStringToInt(nowKeyString),kenban.position_from_tonic);//TEST
            Log.i(TAG, "initialize: kenban["+position+"] ...  absolute_note_name="+ kenban.position_from_tonic);
            Log.d(TAG, "setScaleLabelOnCurrentKey: position_from_tonic = " + kenban.position_from_tonic);
        }
        for (int scale_note : now_key_scale){
            for (Kenban kenban : this.kenban) {
                if(kenban.position_from_tonic == scale_note){
                    kenban.is_scale_note = true;
                }
            }
        }
        postInvalidate();
    }

    //TODO
    /**public void setupKeyBoardScaleOfNowRoot(){
        for (int position = 0; position< keyboard_size; position++) {
            //kenban.is_avoid_note = false;
            kenban.position_from_root = chord.getPositionFromRoot(kenban.absolute_note_name);
            kenban.degree_name_on_key = Scale.getKeyDegreeFromTonic(kenban.position_from_tonic);

            //kenban.indicator_on_key = Scale.getActualNoteIndicator(Scale.keyStringToInt(nowKeyString),kenban.position_from_tonic);//TEST
            Log.i(TAG, "initialize: kenban["+position+"] ...  absolute_note_name="+ kenban.position_from_tonic);
            Log.d(TAG, "setScaleLabelOnCurrentKey: position_from_tonic = " + kenban.position_from_tonic);
        }
        for (int scale_note : now_key_scale){
            for (Kenban kenban : this.kenban) {
                if(kenban.position_from_tonic == scale_note){
                    kenban.is_scale_note = true;
                }
            }
        }
        postInvalidate();
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        // 円を描画する

        for(Kenban kenban : this.kenban) {

            paint_ball.setColor(kenban.color);
            canvas.drawCircle(kenban.cx, kenban.cy, radius, paint_ball);  // (6)

            //円の中に書く数字を描画する(キーのスケール番号を表示)
            float num_x = kenban.cx -25; //Stringは中心ではなく左端を起点に描画されるので微調整
            float num_y = kenban.cy +12;
            paint_ball_number.setTextSize(35);
            if (kenban.is_scale_note) {
                paint_ball_number.setColor(Color.WHITE);
                if(kenban.position_from_tonic == Scale._TONIC){
                    paint_ball_number.setColor(Color.YELLOW);
                    paint_ball_number.setStrokeWidth(5);
                }
                canvas.drawText(kenban.degree_name_on_key, num_x, num_y, paint_ball_number);
            } else {
                paint_ball_number.setColor(Color.DKGRAY);
                canvas.drawText(kenban.degree_name_on_key, num_x, num_y, paint_ball_number);
            }

            //円の中に書く数字(コードのスケール番号 position from root)を描画する
            num_x = kenban.cx -8; //Stringは中心ではなく左端を起点に描画されるので微調整
            num_y = kenban.cy +32;
            paint_ball_number.setTextSize(20);
            paint_ball_number.setColor(Color.GREEN);
            canvas.drawText(String.valueOf(kenban.degree_name_on_chord), num_x, num_y, paint_ball_number);

            //円の下に書く文字を描画する
            num_x = kenban.cx -15; //Stringは中心ではなく左端を起点に描画されるので微調整
            num_y = kenban.cy +100;
            paint_ball_number.setTextSize(40);
            paint_ball_number.setColor(Color.BLUE);
            canvas.drawText(kenban.note_name, num_x, num_y, paint_ball_number);
            //TODO
            //canvas.drawText(Scale.NoteIndicatorToString(kenban.indicator_on_key), num_x, num_y, paint_ball_number);
        }
    }

    @Override
    public void run() {
        Log.d(TAG, "run: \"Thread Start!!\"");
        int now_progress = 0;
        while (isThreadRunning) {
                    /*いったんすべてのボールの色を元に戻す*/
            for (Kenban kenban : this.kenban) {
                kenban.color = Color.BLUE;
            }
            postInvalidate();

            /** コード表示1. シーケンスから次に表示する「コード+時間」を取り出す*/
            ChordTerm chord_term = chord_sequence[now_progress];
            /** コード表示2. 「コード+時間」からコードを取り出す*/
            Chord chord = chord_term.getChord();
            Log.d(TAG, "run: chord_root =" +chord.getRoot());

            nowKey = chord.getKey();
            nowKeyString = chord_term.getKeyString();
            setScaleLabelOnCurrentKey(); //コードのキーに合わせてキーボード表示を変更

            for (Kenban kenban : this.kenban) {
                /**コード表示3. 現在のコードルートに応じて、鍵盤にルートからのポジション番号と、文字表示用のインジケータを割り振る*/
                kenban.position_from_root = chord.getPositionFromRoot(kenban.absolute_note_name);
                Log.d(TAG, "run: positionFromRoot = " + kenban.position_from_root);
                kenban.degree_name_on_chord  = Chord.getKeyDegreeFromTonic(kenban.position_from_root);
                /**コード表示4. コードにディミニッシュかオーギュメンテッドのフラグがあれば、該当する鍵盤のインジケータを変更する*/
                //kenban[i].setflags(chord);
            }

            /** コード表示5-(A). コードからコードトーンを度数の形で出力する(キーボード全体に表示するため)*/
            //int[] chord_tones = chord.getChordTriad(); //FIXME
            int root = Chord._I;
            int third_or_fourth = chord.getThirdOrFourth();
            int fifth = chord.getFifth();
            Log.d(TAG, "run: root = " + root + ", third or fourth = " + third_or_fourth + ", fifth = " +fifth);

            /** コード表示6. ルートからのポジション番号と、コードトーンの度数が一致するものを照合する(キーボード全体)*/

            for (Kenban kenban : this.kenban) {
                if (kenban.position_from_root == root) {
                    kenban.color = Color.RED;
                }else if(kenban.position_from_root == third_or_fourth) {
                    kenban.color = Color.parseColor("aqua");
                }else if(kenban.position_from_root == fifth) {
                    kenban.color = Color.parseColor("aqua");
                }
            }

            /*for (int chord_tone : chord_tones) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position_from_root == chord_tone) {
                        kenban.color = Color.parseColor("aqua");
                    }
                }
            }*/


            /** コード表示7-(B). キーボードのポジション番号と、6thノートの数字が一致するものを照合して音を鳴らす*/
            if (chord.getSixth()!=OMITTED) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position == chord.getSixth()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            /** コード表示7-(C). キーボードのポジション番号と、セブンスノートの数字が一致するものを照合して音を鳴らす*/
            if (chord.getSeventh()!=OMITTED) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position == chord.getSeventh()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            /** コード表示7-(C). キーボードのポジション番号と、ナインスノートの数字が一致するものを照合して音を鳴らす*/
            if (chord.getNinth()!=OMITTED) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position == chord.getNinth()) {
                        kenban.color = Color.GREEN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            /** コード表示7-(C). キーボードのポジション番号と、イレブンスノートの数字が一致するものを照合して音を鳴らす*/
            if (chord.getEleventh()!=OMITTED) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position == chord.getEleventh()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            /** コード表示7-(C). キーボードのポジション番号と、サーティーンスノートの数字が一致するものを照合して音を鳴らす*/
            if (chord.getThirteenth()!=OMITTED) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position == chord.getThirteenth()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            /** コード表示5-(B). コードからコードトーンを、高さ、転回等指定したうえで出力する(実際に鳴らすため)*/
            int[] chord_sounds = chord.generateChordSounds(); //TODO テンションも含めて全て取得できるようにする
            /** コード表示7-(A). キーボードのポジション番号と、コードサウンド配列の数字が一致するものを照合して音を鳴らす*/
            for (int chord_sound : chord_sounds) {
                for (Kenban kenban : this.kenban) {
                    if (kenban.position == chord_sound) {
                        kenban.color = Color.YELLOW;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            /**画面を更新する(onDraw()へ)
             *
             * UIスレッドではないほかのスレッドから画面を更新させたい場合は、
             * invalidate()ではなく、postInvalidate()を利用する
             * */
            postInvalidate();

                    /*次の更新までの間隔を設ける*/
            try {
                Thread.sleep(chord_term.getTerm());
            }catch (InterruptedException e){
                Log.e(TAG, "run:InterruptedException", e );
            }

            now_progress++;
            if(now_progress ==chord_sequence.length){
                now_progress =0;
            }
            Log.d(TAG, "run: \"Thread Running!!\"");
        }
    }


    boolean now_moving = false;
    float down_x;
    float down_y;
    float threshold = 10; //TODO user setting

    private SparseArray<PointF> mActivePointers = new SparseArray<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {    // (7)

        // get pointer index from the event object
        int pointer_index = event.getActionIndex();
        // get pointer ID
        int pointerId = event.getPointerId(pointer_index);
        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        PointF touch_point;
        PointF release_point;
        float pressure;

        touch_x = event.getX();    // (10)
        touch_y = event.getY();    // (11)

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
                touch_point = new PointF();
                touch_point.x = event.getX(pointer_index);
                touch_point.y = event.getY(pointer_index);
                mActivePointers.put(pointerId, touch_point);

                pressure = event.getPressure(pointer_index);

                for (int i=0;i<keyboard_size;i++) {
                    if(kenban[i].checkTouch(touch_point)){
                        Log.d(TAG, "onTouchEvent: KeyBoard[" + i + "] is touched");
                        kenban[i].color = Color.YELLOW;
                        soundPool.play(sounds[i], 1.0f, 1.0f, 0, 0, 1);

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
                mActivePointers.put(pointerId, touch_point);

                pressure = event.getPressure(pointer_index);

                for (int i=0;i<keyboard_size;i++) {
                    if(kenban[i].checkTouch(touch_point)){
                        Log.d(TAG, "onTouchEvent: KeyBoard[" + i + "] is touched");
                        kenban[i].color = Color.YELLOW;
                        soundPool.play(sounds[i], 1.0f, 1.0f, 0, 0, 1);

                        now_moving = true;

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

                    for (int i=0;i<keyboard_size;i++) {
                        if (kenban[i].checkTouch(release_point)) {
                            Log.d(TAG, "onTouchEvent ACTION_POINTER_UP: Keyboard[" + i + "] is released");
                            kenban[i].color = Color.BLUE;
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                release_point = mActivePointers.get(event.getPointerId(pointer_index));
                if (release_point != null) {
                    release_point.x = event.getX(pointer_index);
                    release_point.y = event.getY(pointer_index);

                    for (int i=0;i<keyboard_size;i++) {
                        if (kenban[i].checkTouch(release_point)) {
                            Log.d(TAG, "onTouchEvent ACTION_UP: Keyboard[" + i + "] is released");
                            kenban[i].color = Color.BLUE;
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
                        for (int position=0;position<keyboard_size;position++) {
                            kenban.cx += x_moving-point.x;
                        }
                    }else {
                        for (int position=0;position<keyboard_size;position++) {
                            kenban.cx -= point.x-x_moving;
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

    private final int[] alignment_pattern = {5, 7};

    public void createKeyboard(Kenban[] kenban){

        float init_x = 100;
        float init_y = 185;
        float upper_y = 100;
        float x_gap = 105;
        int color = Color.BLUE;
        boolean pattern_CtoE = true;
        int count_align = 0;
        int n = 0;
        String kana;

        //FIXME 生成されるKenbanインスタンスの数が配列の長さより９個くらいなぜか多くなってしまう
        float y = upper_y;
        for (int position = 0; position< keyboard_size; position++) {
            /**上下交互に並べる*/
            if (y != init_y) { /*下の段*/
                y = init_y;
                kana = note_name[n];
                n++;
            } else { /*上の段*/
                y = upper_y;
                kana = "";
            }
            init_x += x_gap/2; //間隔
            kenban = new Kenban(init_x, y, position,kana);
            count_align++;

            if(pattern_CtoE && count_align==alignment_pattern[0]){
                /*下の段のパターンの最後*/
                n--;
                kenban = new Kenban(init_x, y, position, note_name[n]);
                y = upper_y;
                init_x +=x_gap/2;
                pattern_CtoE = false;
                n++;
                count_align=0;
            }
            if(!pattern_CtoE && count_align==alignment_pattern[1]){
                /*下の段のパターンの最後*/
                n--;
                kenban = new Kenban(init_x, y, position, note_name[n]);
                y = upper_y;
                init_x +=x_gap/2;
                pattern_CtoE = true;
                n =0;
                count_align=0;
            }
        }
    }



    
}
