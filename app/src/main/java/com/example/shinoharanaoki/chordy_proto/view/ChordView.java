package com.example.shinoharanaoki.chordy_proto.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class ChordView extends View{
    private static final String TAG = ChordView.class.getSimpleName();
    private final ChordView self = this;

    /**レイアウトファイルの中にViewをはめ込む場合はAttributeSetが必要*/
    public ChordView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
}
