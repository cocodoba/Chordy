package com.example.shinoharanaoki.chordy_proto.kenban;

import com.example.shinoharanaoki.chordy_proto.enums.Note;

import java.util.ArrayList;

/**
 * Created by naoki on 2017/02/06.
 */

public class KenbanNotesInitializer {

    /*1個目から12個目までの各Kenbanが表示しうる音名(最大6種)のリスト*/
    private ArrayList<Note>
            kenban1Notes,
            kenban2Notes,
            kenban3Notes,
            kenban4Notes,
            kenban5Notes,
            kenban6Notes,
            kenban7Notes,
            kenban8Notes,
            kenban9Notes,
            kenban10Notes,
            kenban11Notes,
            kenban12Notes;

    public KenbanNotesInitializer(int offset) {
        //TODO offset引数を使って並べ始める音名を変えられるようにする

        //順に、{PLAIN(1), NATURAL(2), FLAT(3), WFLAT(4), SHARP(5), WSHARP(6)}
        kenban1Notes = new ArrayList<Note>(6) {{
            add(Note.C);
            add(Note.C_NATURAL);
            add(Note.NULL);
            add(Note.DwFLAT);
            add(Note.B_SHARP);
            add(Note.NULL);
        }};

        kenban2Notes = new ArrayList<Note>(6) {{
            add(Note.NULL);
            add(Note.NULL);
            add(Note.D_FLAT);
            add(Note.NULL);
            add(Note.C_SHARP);
            add(Note.BwSHARP);
        }};

        kenban3Notes = new ArrayList<Note>(6) {{
            add(Note.D);
            add(Note.D_NATURAL);
            add(Note.NULL);
            add(Note.EwFLAT);
            add(Note.NULL);
            add(Note.CwSHARP);
        }};

        kenban4Notes = new ArrayList<Note>(6) {{
            add(Note.NULL);
            add(Note.NULL);
            add(Note.E_FLAT);
            add(Note.NULL);
            add(Note.D_SHARP);
            add(Note.NULL);
        }};

        kenban5Notes = new ArrayList<Note>(6) {{
            add(Note.E);
            add(Note.E_NATURAL);
            add(Note.F_FLAT);
            add(Note.NULL);
            add(Note.NULL);
            add(Note.DwSHARP);
        }};

        kenban6Notes = new ArrayList<Note>(6) {{
            add(Note.F);
            add(Note.F_NATURAL);
            add(Note.NULL);
            add(Note.GwFLAT);
            add(Note.E_SHARP);
            add(Note.NULL);
        }};

        kenban7Notes = new ArrayList<Note>(6) {{
            add(Note.NULL);
            add(Note.NULL);
            add(Note.G_FLAT);
            add(Note.NULL);
            add(Note.F_SHARP);
            add(Note.EwSHARP);
        }};

        kenban8Notes = new ArrayList<Note>(6) {{
            add(Note.G);
            add(Note.G_NATURAL);
            add(Note.NULL);
            add(Note.AwFLAT);
            add(Note.NULL);
            add(Note.FwSHARP);
        }};

        kenban9Notes = new ArrayList<Note>(6) {{
            add(Note.NULL);
            add(Note.NULL);
            add(Note.A_FLAT);
            add(Note.NULL);
            add(Note.G_SHARP);
            add(Note.NULL);
        }};

        kenban10Notes = new ArrayList<Note>(6) {{
            add(Note.A);
            add(Note.A_NATURAL);
            add(Note.NULL);
            add(Note.BwFLAT);
            add(Note.NULL);
            add(Note.GwSHARP);
        }};

        kenban11Notes = new ArrayList<Note>(6) {{
            add(Note.NULL);
            add(Note.NULL);
            add(Note.B_FLAT);
            add(Note.NULL);
            add(Note.A_SHARP);
            add(Note.NULL);
        }};

        kenban12Notes = new ArrayList<Note>(6) {{
            add(Note.B);
            add(Note.B_NATURAL);
            add(Note.C_FLAT);
            add(Note.NULL);
            add(Note.NULL);
            add(Note.AwSHARP);
        }};
    }

    public ArrayList<Note>[] getNotes(){

        ArrayList<Note>[] arrayLists = new ArrayList[]{
                kenban1Notes,
                kenban2Notes,
                kenban3Notes,
                kenban4Notes,
                kenban5Notes,
                kenban6Notes,
                kenban7Notes,
                kenban8Notes,
                kenban9Notes,
                kenban10Notes,
                kenban11Notes,
                kenban12Notes};
        return arrayLists;
    }

    public void setNotesOnKenbans(Kenban[] kenbans, ArrayList<Note>[] noteLists){

        int i = 0;
        for( Kenban kenban : kenbans){
            kenban.noteNameList = noteLists[i++];
            if(i > 11){
                i = 0;
            }
        }
    }

}

