package com.example.shinoharanaoki.chordy_proto.data;

import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;
import com.example.shinoharanaoki.chordy_proto.util.SignatureUtil;

import static com.example.shinoharanaoki.chordy_proto.util.DegreeUtil.*;

/**
 * 定数名(FLAT,SHARP)省略のためSignatureUtilをStaticインポート済み
 */

public class Scale implements Scalable {

    //Data 1 Scale tonic or Chord root
    public Note tonica;

    //Data 2 Scaleの種類
    public ScaleType scaleType;

    //Data 3 Keyboardに適用される♭♯♮の並び
    public int[] signaturesArray;

    //Data 4 どのKenbanがスケールノートに該当するかを参照するための度数の配列
    //例：初期値 = Majorだったら P1, M2, M3, P4, P5, M6, M7
    public int[] scaleNoteDegreesArray = {P1, M2, M3, P4, P5, M6, M7};

    /*コンストラクタ
    トニックまたはルート音を受け取り、そのメジャー又はマイナースケールを初期値として設定する*/
    public Scale(Note tonica, ScaleType scaleType){

        this.tonica = tonica;
        this.scaleType = scaleType;
        signaturesArray = SignatureUtil.getScaleSignatures(tonica, scaleType);
    }

    @Override
    public Note getTonica() {
        return tonica;
    }

    @Override
    public ScaleType getScaleType() {
        return scaleType;
    }

    @Override
    public int[] getDegrees() {
        return scaleNoteDegreesArray;
    }

    @Override
    public int[] getSignatures() {
        return signaturesArray;
    }
}
