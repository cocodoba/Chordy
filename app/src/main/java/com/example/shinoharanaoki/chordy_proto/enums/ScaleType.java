package com.example.shinoharanaoki.chordy_proto.enums;

/**
 * Created by naoki on 2017/02/04.
 */

public enum ScaleType {

    /**
     * 7Notes Scales
     */
    MAJOR, //(Ionian)
    MINOR, //(Aeorian)

    IONIAN, // Chord...Maj7(6th) DegreeShift...None
      // = Major Scale
    DORIAN, // Chord...IIm7 DegreeShift...b3 b7
    PHRYGIAN, //Chord...IIIm7 DegreeShift...b2 b3 b6 b7
    LYDIAN, // Chord...Maj7(6th) DegreeShift...#4
    MIXOLYDIAN, // Chord...V7 DegreeShift...b7
    AEORIAN, // Chord...VIm DegreeShift...b3 b6 b7
      // = Natural Minor Scale
    LOCRIAN, //Chord...VIIm7(b5) or IIm7(b5) DegreeShift...b2 b3 b5 b6 b7

    MELODIC_MINOR, //Im(Maj7,6) DegreeShift...b3
    MELODIC_MINOR_3, //bIII+Maj7 or IV(#5) DegreeShift...#4 #5
    HARMONIC_MINOR, //Im(Maj7) or V7 DegreeShift...b3 b6
    HARMONIC_MINOR_3, //bIII+Maj7 or I(#5) DegreeShift...#5
    ALTERED, //V7(b9/#11/b13) DegreeShift...b2 b3 b4 b5 b6 b7

    /**
     * 6Notes Scales
     */
    AUGUMENTED, //DegreeShift...b3 #5 add natural3rd & omit 4th
    WHOLETONE, //V7(#5) DegreeShift...#4 #5 b7 !Omit6th!

    /**
     * 8Notes Scales
     */
    DIMINISHED, //DegreeShift...b3 b5 b6 !Add natural6th!
    FUNCTIONAL_DIMINISHED, //#Idim7 or #IIdim7 DegreeShift...b2 b3 b4 b5 b6 !Add natural6th!
    COMBINATION_DIMINISHED, //V7(b9,13) DegreeShift...b2 b3 #4 b7 !Add natural3rd natural6th!
    BLUENOTE, // = Blues Scale //DegreeShift...b3 b5 b7

    /**
     * 5Notes Sales
     */
    PENTATONIC_1, //普通のペンタトニック
    PENTATONIC_2,
    PENTATONIC_3,
    PENTATONIC_4,
    PENTATONIC_5,
    RYUKYU, //琉球音階
}
