package com.example.shinoharanaoki.chordy_proto.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shinoharanaoki.chordy_proto.data.Chord;
import com.example.shinoharanaoki.chordy_proto.R;
import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;
import com.example.shinoharanaoki.chordy_proto.factory.ScaleFactory;
import com.example.shinoharanaoki.chordy_proto.view.ChordView;
import com.example.shinoharanaoki.chordy_proto.view.KeyboardView;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Spinner key_select_spinner;
    private Spinner root_select_spinner;
    private Spinner chord_structure_select_spinner;
    private EditText length_imput;

    private KeyboardView keyboardView;
    private ChordView chordView;
    //private boolean isThreadRunning = false;

    //public ArrayList<ChordTerm> chordTerms_arraylist; //スレッド再生用
    private int key_tonic;     //キーボードのキー表示変更用
    public String key_string;  //key選択スピナーで取得したキー名を保持
    public String root_absolute_string; //キーを指定していない場合のルート音
    public String root_relative_string; //キーを指定している場合のスケール上のルート音程
    public String chord_symbol;
    public boolean is_key_selected = false;

    private Thread thread;
    boolean isThreadRunning = false;

    private ScaleFactory scaleFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //各Viewを取り込む
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        chordView = (ChordView)findViewById(R.id.chord_view);

        //スレッドに必要なコードのシーケンス
        //chordTerms_arraylist = new ArrayList<>();
        //chord_edit_view.setChordTermsList(chordTerms_arraylist);

        scaleFactory = new ScaleFactory();
        keyboardView.resetFullKeyboardState(scaleFactory.getScale(Note.C, ScaleType.MAJOR));

        // Spinnerの設定
        //setupSpinners();
        // ボタンの設定
        //setupButtons();

    }

    /*
    public void startThread(ChordTerm[] chordterms){
        if (!isThreadRunning) {
            chord_sequence = chordterms;
            thread = new Thread(this);
            isThreadRunning = true;
            thread.start();
            Log.d(TAG, "startThread: ");
        }
    }

    public void endThread(){
        if (isThreadRunning) {
            isThreadRunning = false;
            Log.d(TAG, "endThread: ");
        }
    }*/

    /*private void setupSpinners(){
            *//* 1. キーを選択するスピナー*//*
        final ArrayAdapter<CharSequence> key_adapter =
                ArrayAdapter.createFromResource(this, R.array.key_array,
                        android.R.layout.simple_spinner_item);

        *//**
         * ルートを指定するスピナーに設定するアダプタを２通り用意
         * *//*
        *//*C,D,E,,,で表示するアダプタ*//*
        final ArrayAdapter<CharSequence> root_absolute_adapter =
                ArrayAdapter.createFromResource(this, R.array.root_absolute_array,
                        android.R.layout.simple_spinner_item);
        root_absolute_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        *//*I,II,III,,,で表示するアダプタ*//*
        final ArrayAdapter<CharSequence> root_relative_adapter =
                ArrayAdapter.createFromResource(this, R.array.root_relative_array,
                        android.R.layout.simple_spinner_item);
        root_relative_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        key_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        key_select_spinner = (Spinner) findViewById(R.id.spinner);
        root_select_spinner = (Spinner) findViewById(R.id.spinner2);
        key_select_spinner.setAdapter(key_adapter);
        key_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                *//**
                 * キーを選択するスピナーで、キーを指定無しにした場合のみ、隣のルートを選択するスピナーにおいて、root=C,D,E,,,のように絶対音で
                 * 選択するように表示を切り替える。
                 *//*
                if(spinner.getSelectedItemPosition()==0){
                    *//**(キー指定なし)*//*
                    is_key_selected = false;
                    *//*2(A). ルートをC,D,E,,,で選択するスピナー*//*
                    root_select_spinner.setAdapter(root_absolute_adapter);
                    root_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Spinner spinner = (Spinner) parent;
                            root_absolute_string = spinner.getSelectedItem().toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Spinner spinner = (Spinner) parent;
                            root_absolute_string = spinner.getItemAtPosition(0).toString();
                        }
                    });
                }else{
                    *//**(キー選択)*//*
                    is_key_selected = true;
                    key_string = spinner.getSelectedItem().toString();//FIXME
                    keyboard_view.nowKeyString = key_string;
                    key_tonic = Chord.keyStringToTonicPositionInt(key_string);
                    keyboard_view.nowKey = key_tonic;
                    keyboard_view.setupKeyBoardScaleOfNowKey();

                     *//*2(B). ルートをI,II,III,,,で選択するスピナー*//*
                    root_select_spinner.setAdapter(root_relative_adapter);
                    root_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Spinner spinner = (Spinner) parent;
                            root_relative_string = spinner.getSelectedItem().toString();
                            Log.d(TAG, "onItemSelected: "+ root_relative_string);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Spinner spinner = (Spinner) parent;
                            root_relative_string = spinner.getItemAtPosition(0).toString();
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                *//**(キー指定なし)*//*
                *//*2(A). ルートをC,D,E,,,で選択するスピナー*//*
                is_key_selected = false;
                root_absolute_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                root_select_spinner.setAdapter(root_absolute_adapter);
                root_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Spinner spinner = (Spinner) parent;
                        root_absolute_string = spinner.getSelectedItem().toString();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Spinner spinner = (Spinner) parent;
                        root_absolute_string = spinner.getItemAtPosition(0).toString();
                    }
                });
            }
        });

        *//* 3. コードシンボル(構成)を選択するスピナー*//*
        ArrayAdapter<CharSequence> chord_structure_adapter =
                ArrayAdapter.createFromResource(this, R.array.chord_structure_array,
                        android.R.layout.simple_spinner_item);

        chord_structure_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chord_structure_select_spinner = (Spinner) findViewById(R.id.spinner3);
        chord_structure_select_spinner.setAdapter(chord_structure_adapter);
        chord_structure_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                chord_symbol = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        length_imput = (EditText)findViewById(R.id.term_length_imput);

    }


    private void setupButtons(){

        Button okButton;
        okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chord new_chord = new Chord();
                if (!is_key_selected) {
                    new_chord.setRoot(Chord.tonicRootStringToInt(root_absolute_string), root_absolute_string);
                } else {
                    new_chord.setKey(Chord.keyStringToTonicPositionInt(key_string));
                    new_chord.setKey(key_tonic);
                    new_chord.setRoot(Chord.degreeNameStringToInt(root_relative_string), root_relative_string);
                }

                new_chord.setChordIntervalsBySymbol(Chord.chordSymbolStringToInt(chord_symbol), chord_symbol);
                ChordTerm new_chordterm = new ChordTerm(new_chord, 2000);
                new_chordterm.setKey_string(key_string);
                chordTerms_arraylist.add(new_chordterm);

                chord_edit_view.setChordTermsList(chordTerms_arraylist);

            }
        });

        // ボタンの設定
        Button threadButton;
        threadButton = (Button)findViewById(R.id.thread_button);
        threadButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isThreadRunning) {
                    int l = chordTerms_arraylist.size();
                    ChordTerm[] array = new ChordTerm[l];
                    Iterator<ChordTerm> iter = chordTerms_arraylist.iterator();
                    for (int i=0;i<l;i++) array[i] = iter.next();
                    isThreadRunning = true;
                    keyboard_view.startThread(array);
                } else{
                    isThreadRunning = false;
                    keyboard_view.endThread();
                }
            }
        });
    }
*/
}
