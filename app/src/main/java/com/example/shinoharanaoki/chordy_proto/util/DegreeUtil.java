package com.example.shinoharanaoki.chordy_proto.util;

/**
 * Created by shinoharanaoki on 2017/01/14.
 * 度数を扱う定数クラス
 */
public class DegreeUtil {

    public static final int P1 = 0;
    public static final int m2 = 1;
    public static final int M2 = 2;
    public static final int m3 = 3;
    public static final int M3 = 4;
    public static final int P4 = 5;
    public static final int aug4 = 6;
    public static final int P5 = 7;
    public static final int m6 = 8;
    public static final int M6 = 9;
    public static final int m7 = 10;
    public static final int M7 = 11;
    public static final int P8 = 12; // = OCTAVE
    public static final int m9 = 13;
    //続き...M13くらいまで


    public int subtraction(int subtractFrom, int subtractBy){

        int result = subtractFrom - subtractBy;
        if(result < 0){
            result += 12;
        }
        return  result;
    }

    public int addition(int addTo, int addWith){

        int result = addTo + addWith;

        return  result;
    }

}
