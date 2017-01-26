package com.example.shinoharanaoki.chordy_proto;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class Kenban {
    private static final String TAG = Kenban.class.getSimpleName();
    private final Kenban self = this;

    public int position;

    /**個別の鍵盤オブジェクトと紐付けられた音名(不変)*/
    public int absolute_note_name; //FIXME finalにしたい  Note.C、 Note.G_FLAT_F_SHARP などが入る
    public int octave_hight; //FIXME finalにしたい

    /*現在のキーに対応する*/
    public int position_from_tonic; //Scale._TONIC, Scale.SUPERTONIC などが入る
    public int indicator_on_key;
    public String degree_name_on_key;
    public boolean is_scale_note = false;
    /*現在のコードによって変化する*/
    public int position_from_root;
    public int chord_interval; // Chord._ROOT, Chord._FLAT_SECOND などが入る
    public int chord_scale_note_name;
    public String degree_name_on_chord;
    public boolean is_triad_note;
    public boolean is_tension_note;
    public boolean is_avoid_note;

    public boolean is_chord_scale_note;

    public float cx;    // 図形を描画する X 座標    // (1)
    public float cy;    // 図形を描画する Y 座標    // (2)
    public float radius = 50;    // 角の丸み    // (3)
    public int color;
    public String note_name;

    public static int index = 1;

    // ペイントオブジェクトを設定する
    paint_ball = new Paint();
    paint_ball.setAntiAlias(true);
    paint_ball.setColor(Color.BLUE);    // (4)
    paint_ball.setStyle(Paint.Style.FILL);    // (5)

    paint_ball_number = new Paint();
    paint_ball_number.setAntiAlias(true);
    paint_ball_number.setStyle(Paint.Style.FILL);    // (5)

    public Kenban(float x, float y, int pos, String string){
        cx = x;
        cy = y;
        radius = 50;
        this.color = Color.BLUE;
        note_name = string;
        position = pos;

        Log.d(TAG, "Balloon: " + index + "個目");
        index++;
    }


    public void TouchResponse(){
        color = ;
        playSound();
    }

    public boolean checkTouch(PointF point){

        float x = point.x;
        float y = point.y;

        if((x<cx+radius&&x>cx-radius)&&(y<cy+radius&&y>cy-radius)){
            return true;
        }else{return false;}
    }

}
