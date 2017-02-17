package com.example.shinoharanaoki.chordy_proto.util;

import com.example.shinoharanaoki.chordy_proto.enums.Note;
import com.example.shinoharanaoki.chordy_proto.enums.ScaleType;

import static com.example.shinoharanaoki.chordy_proto.enums.ScaleType.MINOR;

/**
 * Created by naoki on 2017/02/05.
 */

public class SignatureUtil {

    public static final int PLAIN = 1;
    public static final int NATURAL = 2;
    public static final int FLAT = 3;
    public static final int WFLAT = 4;
    public static final int SHARP = 5;
    public static final int WSHARP = 6;

    public static String getSymbol(int signature){
        switch (signature){
            case PLAIN:
                return "";

            case NATURAL:
                return "♮";

            case FLAT:
                return "♭";

            case WFLAT:
                return "♭♭";

            case SHARP:
                return "♯";

            case WSHARP:
                return "♯♯"; //ダブル♯記号(X)が環境に依って出せない

            default:
                return "";

        }
    }

    /*トニックまたはルート音を受け取り、そのメイジャースケールを初期値として設定する*/
    public static int[] getScaleSignatures(Note tonica, ScaleType scaleType){
        int [] signaturesArray = new int[12];
        switch(tonica){
            case C:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        SHARP,
                        PLAIN,//Major2nd
                        SHARP,
                        PLAIN,//Major3rd
                        PLAIN,//Perfect4th
                        SHARP,
                        PLAIN,//Perfect5th
                        SHARP,
                        PLAIN,//Major6th
                        SHARP,
                        PLAIN,//Major7th
                };
                break;
            case D:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        SHARP,
                        PLAIN,//Major2nd
                        NATURAL,
                        SHARP,//Major3rd
                        PLAIN,//Perfect4th
                        SHARP,
                        PLAIN,//Perfect5th
                        SHARP,
                        PLAIN,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case E:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        NATURAL,
                        SHARP,//Major2nd
                        NATURAL,
                        SHARP,//Major3rd
                        PLAIN,//Perfect4th
                        SHARP,
                        PLAIN,//Perfect5th
                        NATURAL,
                        SHARP,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case F:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        FLAT,
                        PLAIN,//Major2nd
                        FLAT,
                        PLAIN,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        PLAIN,//Perfect5th
                        FLAT,
                        PLAIN,//Major6th
                        FLAT,
                        PLAIN,//Major7th
                };
                break;
            case G:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        SHARP,
                        PLAIN,//Major2nd
                        SHARP,
                        PLAIN,//Major3rd
                        PLAIN,//Perfect4th
                        SHARP,
                        PLAIN,//Perfect5th
                        SHARP,
                        PLAIN,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case A:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        SHARP,
                        PLAIN,//Major2nd
                        NATURAL,
                        SHARP,//Major3rd
                        PLAIN,//Perfect4th
                        SHARP,
                        PLAIN,//Perfect5th
                        NATURAL,
                        SHARP,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case B:
                signaturesArray = new int[]{
                        PLAIN,//Perfect1st
                        NATURAL,
                        SHARP,//Major2nd
                        NATURAL,
                        SHARP,//Major3rd
                        PLAIN,//Perfect4th
                        NATURAL,
                        SHARP,//Perfect5th
                        NATURAL,
                        SHARP,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case C_FLAT:
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        FLAT,//Major2nd
                        NATURAL,
                        FLAT,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        FLAT,//Perfect5th
                        NATURAL,
                        FLAT,//Major6th
                        NATURAL,
                        FLAT,//Major7th
                };
                break;
            case C_SHARP:
                signaturesArray = new int[]{
                        SHARP,//Perfect1st
                        NATURAL,
                        SHARP,//Major2nd
                        NATURAL,
                        SHARP,//Major3rd
                        SHARP,//Perfect4th
                        NATURAL,
                        SHARP,//Perfect5th
                        NATURAL,
                        SHARP,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case D_FLAT:
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        FLAT,//Major2nd
                        NATURAL,
                        PLAIN,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        FLAT,//Perfect5th
                        NATURAL,
                        FLAT,//Major6th
                        NATURAL,
                        PLAIN,//Major7th
                };
                break;
            case D_SHARP:
                if(scaleType != MINOR) {
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            NATURAL,
                            SHARP,//Major2nd
                            SHARP,
                            WSHARP,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            NATURAL,
                            SHARP,//Major6th
                            SHARP,
                            WSHARP,//Major7th
                    };
                }else{
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            NATURAL,
                            SHARP,//Major2nd
                            SHARP,
                            PLAIN,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            NATURAL,
                            SHARP,//Major6th
                            SHARP,
                            PLAIN,//Major7th
                    };

                }
                break;
            case E_FLAT:
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        PLAIN,//Major2nd
                        FLAT,
                        PLAIN,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        FLAT,//Perfect5th
                        NATURAL,
                        PLAIN,//Major6th
                        FLAT,
                        NATURAL,//Major7th
                };
                break;
            case E_SHARP: // = C
                if(scaleType != MINOR) {
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            SHARP,
                            WSHARP,//Major2nd
                            SHARP,
                            WSHARP,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            SHARP,
                            WSHARP,//Major6th
                            SHARP,
                            WSHARP,//Major7th
                    };
                }else{
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            SHARP,
                            PLAIN,//Major2nd
                            SHARP,
                            PLAIN,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            SHARP,
                            PLAIN,//Major6th
                            SHARP,
                            PLAIN,//Major7th
                    };

                }
                break;
            case F_FLAT: // = E
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        FLAT,//Major2nd
                        NATURAL,
                        FLAT,//Major3rd
                        WFLAT,//Perfect4th
                        FLAT,
                        FLAT,//Perfect5th
                        NATURAL,
                        FLAT,//Major6th
                        NATURAL,
                        FLAT,//Major7th
                };
                break;
            case F_SHARP:
                signaturesArray = new int[]{
                        SHARP,//Perfect1st
                        NATURAL,
                        SHARP,//Major2nd
                        NATURAL,
                        SHARP,//Major3rd
                        PLAIN,//Perfect4th
                        NATURAL,
                        SHARP,//Perfect5th
                        NATURAL,
                        SHARP,//Major6th
                        NATURAL,
                        SHARP,//Major7th
                };
                break;
            case G_FLAT:
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        FLAT,//Major2nd
                        NATURAL,
                        FLAT,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        FLAT,//Perfect5th
                        NATURAL,
                        FLAT,//Major6th
                        NATURAL,
                        PLAIN,//Major7th
                };
                break;
            case G_SHARP:
                if(scaleType != MINOR) {
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            NATURAL,
                            SHARP,//Major2nd
                            NATURAL,
                            SHARP,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            NATURAL,
                            SHARP,//Major6th
                            SHARP,
                            WSHARP,//Major7th

                    };
                }else{
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            NATURAL,
                            SHARP,//Major2nd
                            NATURAL,
                            SHARP,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            NATURAL,
                            SHARP,//Major6th
                            SHARP,
                            PLAIN,//Major7th
                    };

                }
                break;
            case A_FLAT:
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        FLAT,//Major2nd
                        NATURAL,
                        PLAIN,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        FLAT,//Perfect5th
                        NATURAL,
                        PLAIN,//Major6th
                        FLAT,
                        PLAIN,//Major7th
                };
                break;
            case A_SHARP:
                if(scaleType != MINOR) {
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            NATURAL,
                            SHARP,//Major2nd
                            SHARP,
                            WSHARP,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            SHARP,
                            WSHARP,//Major6th
                            SHARP,
                            WSHARP,//Major7th
                    };
                }else{
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            NATURAL,
                            SHARP,//Major2nd
                            SHARP,
                            PLAIN,//Major3rd
                            SHARP,//Perfect4th
                            NATURAL,
                            SHARP,//Perfect5th
                            SHARP,
                            PLAIN,//Major6th
                            SHARP,
                            PLAIN,//Major7th
                    };
                }

                break;
            case B_FLAT:
                signaturesArray = new int[]{
                        FLAT,//Perfect1st
                        NATURAL,
                        PLAIN,//Major2nd
                        FLAT,
                        PLAIN,//Major3rd
                        FLAT,//Perfect4th
                        NATURAL,
                        PLAIN,//Perfect5th
                        FLAT,
                        PLAIN,//Major6th
                        FLAT,
                        PLAIN,//Major7th
                };
                break;
            case B_SHARP: // =C
                if(scaleType != MINOR) {
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            SHARP,
                            WSHARP,//Major2nd
                            SHARP,
                            WSHARP,//Major3rd
                            SHARP,//Perfect4th
                            SHARP,
                            WSHARP,//Perfect5th
                            SHARP,
                            WSHARP,//Major6th
                            SHARP,
                            WSHARP,//Major7th
                    };
                }else{
                    signaturesArray = new int[]{
                            SHARP,//Perfect1st
                            SHARP,
                            PLAIN,//Major2nd
                            SHARP,
                            PLAIN,//Major3rd
                            SHARP,//Perfect4th
                            SHARP,
                            PLAIN,//Perfect5th
                            SHARP,
                            PLAIN,//Major6th
                            SHARP,
                            PLAIN,//Major7th

                    };
                }
                break;

        }
        return signaturesArray;
    }

}
