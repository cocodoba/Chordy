package com.example.shinoharanaoki.chordy_proto.kenban;

import android.util.Log;

import com.example.shinoharanaoki.chordy_proto.util.DegreeUtil;
import com.example.shinoharanaoki.chordy_proto.view.KeyboardView;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class WhiteKenban extends Kenban{
    private static final String TAG = WhiteKenban.class.getSimpleName();
    private final WhiteKenban self = this;

    public static short WhiteKenbanTotal = 0;

    private float dropMarkMargin = 30;

    public WhiteKenban(float x, float y, int position){

        centerX = x;
        centerY = y;
        this.position = position;

        /*if(isWhiteKenban){
            stroke = new KenbanStroke();
        }*/
        string = new KenbanString();
        edge = new KenbanEdge();
        dropMark = new DropMark(centerX, centerY, dropMarkMargin);

        Log.d(TAG, "WhiteKenban: " + ++WhiteKenbanTotal + "個目");
    }

    @Override
    public void setupForOnDraw(int displayMode){

        /*WhiteKenban本体,および中の文字の色*/
        stroke.setColor(colorStroke);
        string.setColor(colorStringColorDark);
        if (isScaleNote){
            setColor(colorWkenbanScaleNote);
        }else{
            setColor(colorWkenbanNonScaleNote);
        }
        if(degreeOnScale == DegreeUtil.P1){
            setColor(colorTonicaOnScale);
        }

        /*Edge,DropMarkの表示*/
        switch(displayMode){
            case KeyboardView.MODE_SCALE:
                if (isOnTouched){
                    if(isScaleNote){
                        edge.setColor(colorOnTouchActive);
                    }else{
                        edge.setColor(colorOnTouchWrong);
                    }
                }
                /*else{
                    edge.setColor(Color.TRANSPARENT);
                }*/
                break;
            case KeyboardView.MODE_CHORD:

                if(isChordScaleNote){
                    edge.setColor(colorChordScaleNote);
                }
                if(isChordTone){
                    edge.setColor(colorChordTone);
                }
                if(degreeOnChordScale == DegreeUtil.P1){
                    edge.setColor(colorTonicaOnChord);
                }
                if(isAvoidNote){
                    edge.setColor(colorAvoid);
                }
                if (isOnTouched){
                    if(isChordScaleNote){
                        edge.setColor(colorOnTouchActive);
                    }else{
                        edge.setColor(colorOnTouchWrong);
                    }
                }
                /*else{
                    edge.setColor(Color.TRANSPARENT);
                }*/
                break;
            case KeyboardView.MODE_CHORD_ON_SCALE:
                if(isChordScaleNote){
                    edge.setColor(colorChordScaleNote);
                }
                if(isChordTone){
                    edge.setColor(colorChordTone);
                }
                if(degreeOnChordScale == DegreeUtil.P1){
                    edge.setColor(colorTonicaOnChord);
                }
                if(isAvoidNote){
                    edge.setColor(colorAvoid);
                }
                if (isOnTouched){
                    if(isChordScaleNote){
                        edge.setColor(colorOnTouchActive);
                    }else{
                        edge.setColor(colorOnTouchWrong);
                    }
                }
                /*else{
                    edge.setColor(Color.TRANSPARENT);
                }*/
                break;
        }
    }

    private class KenbanString extends Kenban.KenbanString{
        //表示する文字列の指定はKeyBoardView.onDraw()の中でdrawText()
        public KenbanString(){
            super();
            setColor(colorStringColorDark);
        }
    }

    private class KenbanStroke extends Kenban.KenbanStroke{

    }

    //TODO DropMarkをstaticインナークラスにするか考える
    class DropMark extends Kenban.DropMark{

        public DropMark(float kenbanX, float kenbanY, float size){
            super(kenbanX,kenbanY,dropMarkMargin);
        }

    }

    private class KenbanEdge extends Kenban.KenbanEdge{

        private boolean isOnDisplay = false;
    }

}
