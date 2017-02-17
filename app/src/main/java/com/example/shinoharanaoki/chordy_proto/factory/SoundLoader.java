package com.example.shinoharanaoki.chordy_proto.factory;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.shinoharanaoki.chordy_proto.R;

/**
 * Created by shinoharanaoki on 2017/01/14.
 */
public class SoundLoader {
    private static final String TAG = SoundLoader.class.getSimpleName();
    private final SoundLoader self = this;

    private Context context;

    public SoundLoader(Context context) {

        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SoundPool getSoundPool() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA or USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC, CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        SoundPool soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて(同時に鳴らせる音の数)
                .setMaxStreams(8)
                .build();
        
        soundPool.load(context, R.raw.c1, 1);
        soundPool.load(context, R.raw.c_sharp1, 1);
        soundPool.load(context, R.raw.d1, 1);
        soundPool.load(context, R.raw.d_sharp1, 1);
        soundPool.load(context, R.raw.e1, 1);
        soundPool.load(context, R.raw.f1, 1);
        soundPool.load(context, R.raw.f_sharp1, 1);
        soundPool.load(context, R.raw.g1, 1);
        soundPool.load(context, R.raw.g_sharp1, 1);
        soundPool.load(context, R.raw.a1, 1);
        soundPool.load(context, R.raw.a_sharp1, 1);
        soundPool.load(context, R.raw.b1, 1);
        soundPool.load(context, R.raw.c2, 1);
        soundPool.load(context, R.raw.c_sharp2, 1);
        soundPool.load(context, R.raw.d2, 1);
        soundPool.load(context, R.raw.d_sharp2, 1);
        soundPool.load(context, R.raw.e2, 1);
        soundPool.load(context, R.raw.f2, 1);
        soundPool.load(context, R.raw.f_sharp2, 1);
        soundPool.load(context, R.raw.g2, 1);
        soundPool.load(context, R.raw.g_sharp2, 1);
        soundPool.load(context, R.raw.a2, 1);
        soundPool.load(context, R.raw.a_sharp2, 1);
        soundPool.load(context, R.raw.b2, 1);
        soundPool.load(context, R.raw.c3, 1);
        soundPool.load(context, R.raw.c_sharp3, 1);
        soundPool.load(context, R.raw.d3, 1);
        soundPool.load(context, R.raw.d_sharp3, 1);
        soundPool.load(context, R.raw.e3, 1);
        soundPool.load(context, R.raw.f3, 1);
        soundPool.load(context, R.raw.f_sharp3, 1);
        soundPool.load(context, R.raw.g3, 1);
        soundPool.load(context, R.raw.g_sharp3, 1);
        soundPool.load(context, R.raw.a3, 1);
        soundPool.load(context, R.raw.a_sharp3, 1);
        soundPool.load(context, R.raw.b3, 1);
        soundPool.load(context, R.raw.c4, 1);
        soundPool.load(context, R.raw.c_sharp4, 1);
        soundPool.load(context, R.raw.d4, 1);
        soundPool.load(context, R.raw.d_sharp4, 1);
        soundPool.load(context, R.raw.e4, 1);
        soundPool.load(context, R.raw.f4, 1);
        soundPool.load(context, R.raw.f_sharp4, 1);
        soundPool.load(context, R.raw.g4, 1);
        soundPool.load(context, R.raw.g_sharp4, 1);
        soundPool.load(context, R.raw.a4, 1);
        soundPool.load(context, R.raw.a_sharp4, 1);
        soundPool.load(context, R.raw.b4, 1);
        soundPool.load(context, R.raw.c5, 1);
        soundPool.load(context, R.raw.c_sharp5, 1);
        soundPool.load(context, R.raw.d5, 1);
        soundPool.load(context, R.raw.d_sharp5, 1);
        soundPool.load(context, R.raw.e5, 1);
        soundPool.load(context, R.raw.f5, 1);
        soundPool.load(context, R.raw.f_sharp5, 1);
        soundPool.load(context, R.raw.g5, 1);
        soundPool.load(context, R.raw.g_sharp5, 1);
        soundPool.load(context, R.raw.a5, 1);
        soundPool.load(context, R.raw.a_sharp5, 1);
        soundPool.load(context, R.raw.b5, 1);
        soundPool.load(context, R.raw.c6, 1);
        soundPool.load(context, R.raw.c_sharp6, 1);
        soundPool.load(context, R.raw.d6, 1);
        soundPool.load(context, R.raw.d_sharp6, 1);
        soundPool.load(context, R.raw.e6, 1);
        soundPool.load(context, R.raw.f6, 1);
        soundPool.load(context, R.raw.f_sharp6, 1);
        soundPool.load(context, R.raw.g6, 1);
        soundPool.load(context, R.raw.g_sharp6, 1);
        soundPool.load(context, R.raw.a6, 1);
        soundPool.load(context, R.raw.a_sharp6, 1);
        soundPool.load(context, R.raw.b6, 1);

        return soundPool;
    }

}
