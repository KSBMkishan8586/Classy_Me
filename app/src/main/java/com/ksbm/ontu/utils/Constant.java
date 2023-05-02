package com.ksbm.ontu.utils;

import android.os.Environment;

import com.ksbm.ontu.R;

import java.util.ArrayList;

public class Constant {

    public static String D_LANG_ID = "en";   //default language
    public static final String DEVELOPER_KEY = "AIzaSyCzZThly0SrdJebAAQkzJp_aIG1DbkQ5EE";
    public static final String CURRENCY_EXCHANGE_API_KEY = "c6654d39146366718cb31505";
    public static final String CURRENCY_EXCHANGE_API = "https://v6.exchangerate-api.com/v6/" + CURRENCY_EXCHANGE_API_KEY + "/pair/";

    public static boolean EnableLanguage = true;
    public static boolean EnableGoogleLogin = true;
    public static boolean EnableFbLogin = true;
    public static boolean EnableChat = true;
    public static boolean EnableOnlineStatusFriend = true;

    public static String PREF_NAME = "Ontu_pref";
    public static  String IS_LOGGEDIN = "IsLogin";
    public static  String LANGUAGEID = "LANGUAGEID";
    public static  String Token_Id = "token";
    public static  String Device_Id = "deviceId";
    public static  String LANGUAGE = "language";
    public static  String IS_FIRST_TIME = "is_first_time";
    public static  String SOUND_EFFECT = "SOUND_EFFECT";
    public static  String OneTimeBanner = "onetimebanner";


    public static boolean havefun=false;
    public static  int Passing_percent = 60;

    public static String WorkbookData = "WorkbookData";
    public static String FundamentalWorkbookQuizList = "FundamentalWorkbookQuizList";
    public static String JumbleQuizData = "JumbleQuizData";
    public static int Fundamental_current_item = 0;

    public static String SetingsData = "SetingsData";

    public static int DEFAULT_LANG_POS = 1;
    public static int DEFAULT_LANG_POS_TO= 0;
    public static boolean NEXT_SENTENCES= false;

    public static String TranslateCoinCharge= "0";
    public static String FoundationLearningCoinBonus= "0";
    public static String WHATSAPP_NO= "";

    //internal storage folder path
    public static final String root= Environment.getExternalStorageDirectory().toString();
    public static final String app_hided_folder = "/.ClassyMe"  +"/";
    public static final String app_showing_folder = "/ClassyMe"  +"/";
    public static final String certificate_folder = app_showing_folder + "Certificate/" ;
    public static final String drawing = app_showing_folder + "Drawing/" ;
    public static final String audio_record_folder = app_showing_folder + "Recording/" ;

    public static String WorkbookPlayStatusDone= "done";
    public static String WorkbookPlayStatusIncomplete = "incomplete";

    public static String Right = "Right";
    public static String Wrong = "Wrong";

    //offline DB
    public static String COLUMN_ID= "COLUMN_ID";
    public static String STAGE_ID= "STAGE_ID";
    public static String LEVEL_ID= "LEVEL_ID";
    public static String QUIZ_ID= "QUIZ_ID";
    public static String QUIZ_COIN= "QUIZ_COIN";
    public static String IS_PLAYED= "IS_PLAYED";

    //foundation name and id
    public static String ABCD = "1";
    public static String Animals = "2";
    public static String Body_Parts = "3";
    public static String Counting = "4";
    public static String Colors = "5";
    public static String Common_Currency = "6";
    public static String Date = "7";
    public static String Direction = "8";
    public static String Fruits_Vegetables = "9";
    public static String Months = "10";
    public static String Time = "11";
    public static String Weekdays = "12";
    public static String Body_Partsoriginal = "37";

    public static String foundation_id;
    public static String foundation_name;
    public static boolean hourHandStatus = false;
    public static boolean minuteHandStatus = false;

    //role id-
    public static String studend_role_id ="3";
    public static String parent_role_id ="4";
    public static String individual_role_id ="5";
    public static boolean previous_btn=true;

    public static String upgrade_course = "Upgrade your package please....";
    public static String upgradePackage = "upgradePackage";

    public static String PRIVACY_URL = "https://ksbminfotech.com";

}
