package com.example.shinoharanaoki.chordy_proto.kenban;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.view.KeyboardView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by naoki on 2017/02/15.
 */


//TODO 抽象クラスにすることを考える
public abstract class Kenban extends Paint {


    public static int colorWkenbanScaleNote = Color.WHITE;
    public static int colorWkenbanNonScaleNote = Color.GRAY;
    public static int colorBkenbanScaleNote = Color.rgb(216,101,255);
    public static int colorBkenbanNonScaleNote = Color.DKGRAY;
    public static int colorStroke = Color.BLACK;
    public static int colorTonicaOnScale = Color.rgb(255,140,101);

    public static int colorChordScaleNote = Color.argb(150,255,255,101);
    public static int colorChordTone = Color.argb(150,255,178,101);
    public static int colorTonicaOnChord = Color.argb(150,255,140,101);
    public static int colorAvoid = Color.argb(150,152,152,0);

    public static int colorOnTouchActive = Color.YELLOW;
    public static int colorOnTouchWrong = Color.rgb(101,0,203);
    public static int colorStringColorDark = Color.BLACK;
    public static int colorStringColorLight = Color.WHITE;

    public int position; //KeybordViewでのKenban配列の中での順番
                         //度数の設定、タッチ判定、等で使用する
                         //加えてSoundPool.play()の引数になる予定
    public int octave;
    public Note noteNameOnScale;
    public Note noteNameOnChord;
    public int scaleSignature; //スケールの♭♯♮等
    public int degreeOnScale;
    public int chordScaleSignature; //コードスケールの♭♯♮等
    public int degreeOnChordScale;

    public ArrayList<Note> noteNameList;

    public boolean isScaleNote;
    public boolean isChordScaleNote;
    public boolean isChordTone;
    public boolean isAvoidNote;
    public boolean isOnPlay; //スレッドで再生中
    public boolean isOnTouched; //タッチされている

    protected Kenban.KenbanString string;
    protected Kenban.KenbanStroke stroke;
    protected Kenban.KenbanEdge edge;
    protected Kenban.DropMark dropMark;

    public float centerX;    // Kenbanの中心となる X 座標
    public float centerY;    // Kenbanの中心となる  Y 座標
    public static float radius = 50;    // 半径 ＝ 大きさ

    public static short kenbanTotal = 0;

    public Kenban(){
        setStyle(Style.FILL);
        setAntiAlias(true);

        string = new KenbanString();
        stroke = new KenbanStroke();
        edge = new KenbanEdge();
        dropMark = new DropMark(centerX, centerY, 80);

        Log.d(TAG, "WhiteKenban: " + ++kenbanTotal + "個目");
    }

    public abstract void setupForOnDraw(int displayMode);

    public void reloadNoteNameOnScale(){
        /*KeyboardView.setSignatures()でKenbanにSignature を設定した後、
        音名を再読み込みするための処理*/
        noteNameOnScale = noteNameList.get(scaleSignature);
    }

    public void reloadNoteNameOnChord(){
        /*KeyboardView.setSignatures()でKenbanにSignature を設定した後、
        音名を再読み込みするための処理*/
        noteNameOnChord = noteNameList.get(chordScaleSignature);
    }

    public void draw(Canvas canvas, int stringTypeForDropMark){
        string.draw(canvas);
        stroke.draw(canvas);
        edge.draw(canvas);
        dropMark.draw(canvas, stringTypeForDropMark);
    }

    class KenbanString extends Paint{
        float x;
        float y;
        //表示する文字列の指定はKeyBoardView.onDraw()の中でdrawText()
        public KenbanString(){
            setAntiAlias(true);
            setTextSize(35);
        }
        public void draw(Canvas canvas){
            canvas.drawText(noteNameOnScale.toString(),x,y,this);
        }
    }
    class KenbanStroke extends Paint{
        float x;
        float y;
        float radius;
        public KenbanStroke(){
            setStyle(Style.STROKE);
            setAntiAlias(true);
            setStrokeWidth(5);
        }
        public void draw(Canvas canvas){
            canvas.drawCircle(x,y,radius,this);
        }
    }
    class KenbanEdge extends Paint{
        float x;
        float y;
        float radius;
        boolean isOnDisplay = false;
        public KenbanEdge(){
            setStyle(Style.STROKE);
            setStrokeWidth(15);
        }
        public void draw(Canvas canvas){
            canvas.drawCircle(x,y,radius,this);
        }
    }

    //TODO DropMarkをstaticインナークラスにするか考える
    class DropMark extends Paint{
        float x;
        float y;
        float radius;
        Path path;

        public DropMark(float x, float y, float marginY){
            setStyle(Style.FILL);
            setAntiAlias(true);

            this.x = x;
            this.y = y + marginY;
            this.radius = Kenban.radius;
            path = new Path();
        }

        public void draw(Canvas canvas, int stringMode){
            //丸の半径...DropMarkの大きさの基準になる

            //下部の三角形を描画
            path.moveTo(x,65.7f);
            path.lineTo(0.87f,0.8f);
            path.lineTo(7.6f,4.7f);
            canvas.drawPath(path, this);

            //上部の円を描画
            float circleX = x;
            float circleY = radius;
            canvas.drawCircle(circleX,circleY,this.radius,this);
            //中の文字を描画
            if(stringMode == KeyboardView.DEGREE){
                canvas.drawText(Integer.toString(degreeOnChordScale),circleX,circleY,this);
            }
            if(stringMode == KeyboardView.NOTENAME){
                canvas.drawText(noteNameOnChord.toString(),circleX,circleY,this);
            }
        }
    }

    public boolean checkTouch(PointF point){

        float x = point.x;
        float y = point.y;

        if((x< centerX +radius&&x> centerX -radius)&&(y< centerY +radius&&y> centerY -radius)){
            return true;
        }else{return false;}
    }

    public Kenban.KenbanString getString(){
        return string;
    };

    public Kenban.KenbanStroke getStroke(){
        return stroke;
    }

    public Kenban.KenbanEdge getEdge(){
        return edge;
    }

    public Kenban.DropMark getDropMark(){
        return dropMark;
    }
}
