package com.example.shinoharanaoki.chordy_proto.data;

import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;

/**
 * Created by naoki on 2017/02/12.
 */

public interface Scalable {
    Note getTonica();
    ScaleType getScaleType();
    int[] getDegrees();
    int[] getSignatures();
}


