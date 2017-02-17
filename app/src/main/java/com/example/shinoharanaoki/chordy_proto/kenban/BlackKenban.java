package com.example.shinoharanaoki.chordy_proto.kenban;

import android.util.Log;

import com.example.shinoharanaoki.chordy_proto.util.DegreeUtil;
import com.example.shinoharanaoki.chordy_proto.view.KeyboardView;

/**
 * Created by naoki on 2017/02/15.
 */

public class BlackKenban extends Kenban {

    private static final String TAG = BlackKenban.class.getSimpleName();
    private final BlackKenban self = this;

    public static short BlackKenbanTotal = 0;

    private float dropMarkMargin = 30;

    public BlackKenban(float x, float y, int position){

        centerX = x;
        centerY = y;
        this.position = position;

        /*if(isBlackKenban){
            stroke = new KenbanStroke();
        }*/
        string = new BlackKenban.KenbanString();
        edge = new BlackKenban.KenbanEdge();
        dropMark = new BlackKenban.DropMark(centerX, centerY, dropMarkMargin);

        Log.d(TAG, "BlackKenban: " + ++BlackKenbanTotal + "個目");
    }

    @Override
    public void setupForOnDraw(int displayMode){

        /*BlackKenban本体,および中の文字の色*/
        stroke.setColor(colorStroke);
        string.setColor(colorStringColorLight);
        if(isScaleNote){
            setColor(colorBkenbanScaleNote);
        }else{
            setColor(colorBkenbanNonScaleNote);
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
            setColor(colorStringColorLight);
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
