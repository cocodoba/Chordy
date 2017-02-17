package com.example.shinoharanaoki.chordy_proto.data;

import com.example.shinoharanaoki.chordy_proto.enums.ChordType;
import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class Chord extends Scale implements Scalable{
    private static final String TAG = Chord.class.getSimpleName();
    private final Chord self = this;

    //Data 5
    public ChordType chordType;


    public Chord(Note tonica, ScaleType scaleType) {
        super(tonica, scaleType);
    }

    @Override
    public Note getTonica() {
        return super.tonica;
    }

    @Override
    public ScaleType getScaleType() {
        return super.scaleType;
    }

    @Override
    public int[] getDegrees() {
        return super.scaleNoteDegreesArray;
    }

    @Override
    public int[] getSignatures() {
        return super.signaturesArray;
    }
}
