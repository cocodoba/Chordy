package com.example.shinoharanaoki.chordy_proto.util;

/**
 * Created by naoki on 2017/02/10.
 */

public class ArrayUtil {

    //例えばoffset=4からはじめて456123456123456...みたいな数列を作る
    public static int[] getLoopNumbersWithOffset(int min, int max, int offset, int totalSize){
        int[] array = new int[totalSize];
        int count = 0;
        for(int i=offset;i<=max;i++){
            array[count++] = i;
        }
        int i=min;
        while(count < totalSize){
            array[count++] = i++;
            if(i>max){
                i = min;
            }
        }
        return array;
    }

    //数字の配列の先頭を変える
    //引数の説明 ... int startPosition 何番目の数を配列の先頭として変更するか
    public static int[] changeStartPointOnArray(int[] array, int startPosition, int totalSize){
        int[] arrayResult = new int[totalSize];
        int count = 0;
        int i;
        for(i=startPosition; i<array.length ;i++){
            arrayResult[count++] = array[i];
        }
        i=0;
        while(count < totalSize){
            arrayResult[count++] = array[i++];
            if(i>=array.length){
                i = 0;
            }
        }
        return arrayResult;
    }
    //オブジェクトの配列の先頭を変える
    //引数の説明 ... int startPosition 何番目の要素を配列の先頭として変更するか
    public static Object[] changeStartPointOnArray(Object[] array, int startPositon, int totalSize){
        Object[] arrayResult = new Object[totalSize];
        int count = 0;
        int i;
        for(i=startPositon-1; i<array.length ;i++){
            arrayResult[count++] = array[i];
        }
        i=0;
        while(count < totalSize){
            arrayResult[count++] = array[i++];
            if(i>=array.length){
                i = 0;
            }
        }
        return arrayResult;
    }


}
