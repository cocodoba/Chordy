package com.example.shinoharanaoki.chordy_proto.thread;

import android.graphics.Color;
import android.util.Log;

import com.example.shinoharanaoki.chordy_proto.data.Chord;
import com.example.shinoharanaoki.chordy_proto.kenban.Kenban;

import static android.content.ContentValues.TAG;

/**
 * Created by naoki on 2017/02/15.
 */

public class ChordPlayThread extends Thread{
    /*@Override
    public void run() {
        Log.d(TAG, "run: \"Thread Start!!\"");
        int now_progress = 0;
        while (isThreadRunning) {
                    *//*いったんすべてのボールの色を元に戻す*//*
            for (Kenban kenban : this.kenbans) {
                kenban.color = Color.BLUE;
            }
            postInvalidate();

            *//** コード表示1. シーケンスから次に表示する「コード+時間」を取り出す*//*
            ChordTerm chord_term = chordSequence[now_progress];
            *//** コード表示2. 「コード+時間」からコードを取り出す*//*
            Chord chord = chord_term.getChord();
            Log.d(TAG, "run: chord_root =" +chord.getRoot());

            nowKey = chord.getKey();
            nowKeyString = chord_term.getKeyString();
            setScaleLabelOnCurrentKey(); //コードのキーに合わせてキーボード表示を変更

            for (Kenban kenban : this.kenbans) {
                *//**コード表示3. 現在のコードルートに応じて、鍵盤にルートからのポジション番号と、文字表示用のインジケータを割り振る*//*
                kenban.position_from_root = chord.getPositionFromRoot(kenban.absolute_note_name);
                Log.d(TAG, "run: positionFromRoot = " + kenban.position_from_root);
                kenban.degree_name_on_chord  = Chord.getKeyDegreeFromTonic(kenban.position_from_root);
                *//**コード表示4. コードにディミニッシュかオーギュメンテッドのフラグがあれば、該当する鍵盤のインジケータを変更する*//*
                //kenbans[i].setflags(chord);
            }

            *//** コード表示5-(A). コードからコードトーンを度数の形で出力する(キーボード全体に表示するため)*//*
            //int[] chord_tones = chord.getChordTriad(); //FIXME
            int root = Chord._I;
            int third_or_fourth = chord.getThirdOrFourth();
            int fifth = chord.getFifth();
            Log.d(TAG, "run: root = " + root + ", third or fourth = " + third_or_fourth + ", fifth = " +fifth);

            *//** コード表示6. ルートからのポジション番号と、コードトーンの度数が一致するものを照合する(キーボード全体)*//*

            for (Kenban kenban : this.kenbans) {
                if (kenban.position_from_root == root) {
                    kenban.color = Color.RED;
                }else if(kenban.position_from_root == third_or_fourth) {
                    kenban.color = Color.parseColor("aqua");
                }else if(kenban.position_from_root == fifth) {
                    kenban.color = Color.parseColor("aqua");
                }
            }

            *//*for (int chord_tone : chord_tones) {
                for (Kenban kenbans : this.kenbans) {
                    if (kenbans.position_from_root == chord_tone) {
                        kenbans.color = Color.parseColor("aqua");
                    }
                }
            }*//*


            *//** コード表示7-(B). キーボードのポジション番号と、6thノートの数字が一致するものを照合して音を鳴らす*//*
            if (chord.getSixth()!=OMITTED) {
                for (Kenban kenban : this.kenbans) {
                    if (kenban.position == chord.getSixth()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            *//** コード表示7-(C). キーボードのポジション番号と、セブンスノートの数字が一致するものを照合して音を鳴らす*//*
            if (chord.getSeventh()!=OMITTED) {
                for (Kenban kenban : this.kenbans) {
                    if (kenban.position == chord.getSeventh()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            *//** コード表示7-(C). キーボードのポジション番号と、ナインスノートの数字が一致するものを照合して音を鳴らす*//*
            if (chord.getNinth()!=OMITTED) {
                for (Kenban kenban : this.kenbans) {
                    if (kenban.position == chord.getNinth()) {
                        kenban.color = Color.GREEN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            *//** コード表示7-(C). キーボードのポジション番号と、イレブンスノートの数字が一致するものを照合して音を鳴らす*//*
            if (chord.getEleventh()!=OMITTED) {
                for (Kenban kenban : this.kenbans) {
                    if (kenban.position == chord.getEleventh()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            *//** コード表示7-(C). キーボードのポジション番号と、サーティーンスノートの数字が一致するものを照合して音を鳴らす*//*
            if (chord.getThirteenth()!=OMITTED) {
                for (Kenban kenban : this.kenbans) {
                    if (kenban.position == chord.getThirteenth()) {
                        kenban.color = Color.CYAN;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            *//** コード表示5-(B). コードからコードトーンを、高さ、転回等指定したうえで出力する(実際に鳴らすため)*//*
            int[] chord_sounds = chord.generateChordSounds(); //TODO テンションも含めて全て取得できるようにする
            *//** コード表示7-(A). キーボードのポジション番号と、コードサウンド配列の数字が一致するものを照合して音を鳴らす*//*
            for (int chord_sound : chord_sounds) {
                for (Kenban kenban : this.kenbans) {
                    if (kenban.position == chord_sound) {
                        kenban.color = Color.YELLOW;
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
                        soundPool.play(sounds[position], 1.0f, 1.0f, 0, 0, 1);
                    }
                }
            }

            *//**画面を更新する(onDraw()へ)
             *
             * UIスレッドではないほかのスレッドから画面を更新させたい場合は、
             * invalidate()ではなく、postInvalidate()を利用する
             * *//*
            postInvalidate();

                    *//*次の更新までの間隔を設ける*//*
            try {
                Thread.sleep(chord_term.getTerm());
            }catch (InterruptedException e){
                Log.e(TAG, "run:InterruptedException", e );
            }

            now_progress++;
            if(now_progress == chordSequence.length){
                now_progress =0;
            }
            Log.d(TAG, "run: \"Thread Running!!\"");
        }
    }*/
}
