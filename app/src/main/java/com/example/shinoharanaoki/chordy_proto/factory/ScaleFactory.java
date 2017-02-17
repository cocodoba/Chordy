package com.example.shinoharanaoki.chordy_proto.factory;

import com.example.shinoharanaoki.chordy_proto.data.Scale;
import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;
import com.example.shinoharanaoki.chordy_proto.util.DegreeUtil;
import com.example.shinoharanaoki.chordy_proto.util.SignatureUtil;

/**
 * Created by naoki on 2017/02/04.
 */

public class ScaleFactory {


    public Scale getScale(Note tonica, ScaleType scaleType){

        Scale scale = new Scale(tonica, scaleType);

        switch(scaleType){
            case MAJOR:
                break; //何もしない
            case MINOR:
                flatNotes(scale,3,6,7);
                break;
            case IONIAN:
                break; //何もしない
            case DORIAN:
                flatNotes(scale,3,7);
                break;
            case PHRYGIAN:
                flatNotes(scale,2,3,6,7);
                break;
            case LYDIAN:
                sharpNotes(scale,4);
                break;
            case MIXOLYDIAN:
                flatNotes(scale,7);
                break;
            case AEORIAN:
                flatNotes(scale,3,6,7);
                break;
            case LOCRIAN:
                flatNotes(scale,2,3,5,6,7);
                break;
            case MELODIC_MINOR:
                flatNotes(scale,3);
                break;
            case MELODIC_MINOR_3:
                sharpNotes(scale,4,5);
                break;
            case HARMONIC_MINOR:
                flatNotes(scale,3,6);
                break;
            case HARMONIC_MINOR_3:
                sharpNotes(scale,5);
                break;
            case ALTERED: //DegreeShift...b2 b3 b4 b5 b6 b7
                flatNotes(scale,2,3,4,5,6,7);
                break;


            //TODO これ以降まだ未完成

            case AUGUMENTED: //b3 #5 add natural3rd & omit 4th
                flatNotes(scale,3,6,7);
                break;
            case WHOLETONE: //DegreeShift...#4 #5 b7 !Omit6th!
                flatNotes(scale,3,6,7);
                break;
            case DIMINISHED://DegreeShift...b3 b5 b6 !Add natural6th!
                flatNotes(scale,3,6,7);
                break;
            case FUNCTIONAL_DIMINISHED: //DegreeShift...b2 b3 b4 b5 b6 !Add natural6th!
                flatNotes(scale,3,6,7);
                break;
            case COMBINATION_DIMINISHED://DegreeShift...b2 b3 #4 b7 !Add natural3rd natural6th!
                flatNotes(scale,3,6,7);
                break;
            case BLUENOTE://DegreeShift...b3 b5 b7
                flatNotes(scale,3,6,7);
                break;
            case PENTATONIC_1:
                flatNotes(scale,3,6,7);
                break;
            case PENTATONIC_2:
                flatNotes(scale,3,6,7);
                break;
            case PENTATONIC_3:
                flatNotes(scale,3,6,7);
                break;
            case PENTATONIC_4:
                flatNotes(scale,3,6,7);
                break;
            case PENTATONIC_5:
                flatNotes(scale,3,6,7);
                break;
            case RYUKYU:
                flatNotes(scale,3,6,7);
                break;
        }
        return scale;
    }

    //スケールノート5~8音の中から、任意の音(複数可)を半音下げる処理を行う
    private void flatNotes(Scale scale, int...whichScaleNotes){
        int[] whichNotes = whichScaleNotes;
        for(int index : whichNotes) {
            index -= 1; //配列の番号として利用するための調整
            int originalDegree = scale.scaleNoteDegreesArray[index];
            int newDegree = originalDegree - DegreeUtil.m2; //元の度数から半音分引く
            scale.scaleNoteDegreesArray[index] = newDegree;
            scale.signaturesArray[newDegree] = SignatureUtil.FLAT;
        }
    }

    //スケールノート5~8音の中から、任意の音(複数可)を全音下げる処理を行う
    private void doubleFlatNotes(Scale scale, int...whichScaleNotes){
        int[] whichNotes = whichScaleNotes;
        for(int index : whichNotes) {
            index -= 1; //配列の番号として利用するための調整
            int originalDegree = scale.scaleNoteDegreesArray[index];
            int newDegree = originalDegree - DegreeUtil.M2; //元の度数から全音分引く
            scale.scaleNoteDegreesArray[index] = newDegree;
            scale.signaturesArray[newDegree] = SignatureUtil.WFLAT;
        }
    }

    //スケールノート5~8音の中から、任意の音(複数可)を半音上げる処理を行う
    private void sharpNotes(Scale scale, int...whichScaleNotes){
        int[] whichNotes = whichScaleNotes;
        for(int index : whichNotes) {
            index -= 1; //配列の番号として利用するための調整
            int originalDegree = scale.scaleNoteDegreesArray[index];
            int newDegree = originalDegree + DegreeUtil.m2; //元の度数から半音分足す
            scale.scaleNoteDegreesArray[index] = newDegree;
            scale.signaturesArray[newDegree] = SignatureUtil.SHARP;
        }
    }

    //スケールノート5~8音の中から、任意の音(複数可)を全音上げる処理を行う
    private void doubleSharpNotes(Scale scale, int...whichScaleNotes){
        int[] whichNotes = whichScaleNotes;
        for(int index : whichNotes) {
            index -= 1; //配列の番号として利用するための調整
            int originalDegree = scale.scaleNoteDegreesArray[index];
            int newDegree = originalDegree + DegreeUtil.M2; //元の度数から全音分足す
            scale.scaleNoteDegreesArray[index] = newDegree;
            scale.signaturesArray[newDegree] = SignatureUtil.WSHARP;
        }
    }
    //TODO
    private void omitNotes(Scale scale, int...whichScaleNotes){

    }
    //TODO
    private void insertNotes(Scale scale, int...whichScaleNotes){

    }

}
