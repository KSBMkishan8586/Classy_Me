package com.ksbm.ontu.session;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ksbm.ontu.Splash_Activity;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Workbook_Data;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModelResponse;
import com.ksbm.ontu.main_screen.model.SettingsModel;

import java.lang.reflect.Type;
import java.util.List;

import static com.ksbm.ontu.utils.Constant.Device_Id;
import static com.ksbm.ontu.utils.Constant.IS_FIRST_TIME;
import static com.ksbm.ontu.utils.Constant.IS_LOGGEDIN;
import static com.ksbm.ontu.utils.Constant.LANGUAGE;
import static com.ksbm.ontu.utils.Constant.LANGUAGEID;
import static com.ksbm.ontu.utils.Constant.OneTimeBanner;
import static com.ksbm.ontu.utils.Constant.PREF_NAME;
import static com.ksbm.ontu.utils.Constant.SetingsData;
import static com.ksbm.ontu.utils.Constant.Token_Id;
import static com.ksbm.ontu.utils.Constant.WorkbookData;


public class SessionManager {
    private static final String TAG = SessionManager.class.getSimpleName();

    private Context _context;
    public static SharedPreferences mypref;
    public static SharedPreferences.Editor editor;
    public static String AGE = "age";
    public static String AGE_CRITERIA = "age_criteria";
    public static String ENGLISH_LEVEL = "english_proficiency";
    public static String FIRST_USER = "first_user";
    public static String BASIC_USER = "basic_user";
    public static String ALPHABET = "alphabets";
    public static String LEARN_BASIC = "learns";

    public SessionManager(Context context) {
        this._context = context;
        mypref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mypref.edit();
        editor.apply();
    }

    public static String getMyPref(String key) {
        return mypref.getString(key, "");

    }

    public static void setMypref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
        editor.commit();

    }

    public void createSession(LoginModelResponse userInfoData) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfoData);
        editor.putString("user", json);
        editor.putBoolean(IS_LOGGEDIN, true);
        editor.apply();
    }

    public void setUser(LoginModelResponse userInfoData) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfoData);
        editor.putString("user", json);
        editor.apply();
    }

    public LoginModelResponse getUser() {
        Gson gson = new Gson();
        String str = mypref.getString("user", "");
        if (str.isEmpty())
            return null;
        return gson.fromJson(str, LoginModelResponse.class);
    }

    public void save_workbook_data(Fundamental_Workbook_Data userInfoData) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfoData);
        editor.putString(WorkbookData, json);
        editor.apply();
    }

    public Fundamental_Workbook_Data getWorkbook() {
        Gson gson = new Gson();
        String str = mypref.getString(WorkbookData, "");
        if (str.isEmpty())
            return null;
        return gson.fromJson(str, Fundamental_Workbook_Data.class);
    }

    public void save_settings_data(List<SettingsModel.SettingsModelResponse> response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        editor.putString(SetingsData, json);
        editor.apply();
    }

    public List<SettingsModel.SettingsModelResponse> getSettingsData() {
        Gson gson = new Gson();
        String str = mypref.getString(SetingsData, "");
        if (str.isEmpty())
            return null;
        Type type = new TypeToken<List<SettingsModel.SettingsModelResponse>>() {
        }.getType();
        List<SettingsModel.SettingsModelResponse> arrayList = gson.fromJson(str, type);
        return arrayList;
    }

    public void setCurrentLanguage(String value, Context context) {
        editor.putString(LANGUAGE, value);
        editor.apply();
        editor.commit();

    }

    public static String getCurrentLanguage(Context context) {
        return mypref.getString(LANGUAGE, "en");
    }

    public void setIsFirstTime(boolean value, Context context) {
        editor.putBoolean(IS_FIRST_TIME, value);
        editor.apply();
        editor.commit();

    }

    public boolean getIs_first_time(Context context) {
        return mypref.getBoolean(IS_FIRST_TIME, true);
    }

    public void setLanguageid(String gender) {
        editor.putString(LANGUAGEID, gender);
        editor.apply();
        editor.commit();
    }

    public String getLanguageid() {
        return mypref.getString(LANGUAGEID, "");

    }

    public void saveToken(String token) {
        editor.putString(Token_Id, token);
        editor.apply();
        editor.commit();
    }

    public void saveDeviceId(String token) {
        editor.putString(Device_Id, token);
        editor.apply();
        editor.commit();
    }

    public String getTokenId() {
        return mypref.getString(Token_Id, "");
    }

    public String getDeviceId() {
        return mypref.getString(Device_Id, "");
    }

    public void logout() {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(_context, Splash_Activity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }

    public boolean isLoggedIn() {
        return mypref.getBoolean(IS_LOGGEDIN, false);
    }

    public boolean isOneTimeBanner() {
        return mypref.getBoolean(OneTimeBanner, false);
    }

    public void setOneTimeBanner(boolean status) {
        editor.putBoolean(OneTimeBanner, status);
        editor.apply();
        editor.commit();
    }

    public void setAge(String age) {
        editor.putString(AGE, age);
        editor.apply();
        editor.commit();
    }

    public String getAGE() {
        return mypref.getString(AGE, "");
    }

    public void setAgeCriteria(String age_c) {
        editor.putString(AGE_CRITERIA, age_c);
        editor.apply();
        editor.commit();
    }

    public String getAgeCriteria() {
        return mypref.getString(AGE_CRITERIA, "Below 13");
    }


    public void setFirstUser(String f_user) {
        editor.putString(FIRST_USER, f_user);
        editor.apply();
        editor.commit();
    }

    public String getFirstUser() {
        return mypref.getString(FIRST_USER, "0");
    }

    public void setBasicUser(String f_user) {
        editor.putString(BASIC_USER, f_user);
        editor.apply();
        editor.commit();
    }

    public String getBasicUser() {
        return mypref.getString(BASIC_USER, "0");
    }

    public void setLearnBasic(String f_user) {
        editor.putString(LEARN_BASIC, f_user);
        editor.apply();
        editor.commit();
    }

    public String getLearnBasic() {
        return mypref.getString(LEARN_BASIC, "0");
    }

    public void setEnglishLevel(String engs) {
        editor.putString(ENGLISH_LEVEL, engs);
        editor.apply();
        editor.commit();
    }

    public String getALPHABET() {
        return mypref.getString(ALPHABET, "0");
    }

    public void setALPHABET(String engs) {
        editor.putString(ALPHABET, engs);
        editor.apply();
        editor.commit();
    }

    public String getEnglishLevel() {
        return mypref.getString(ENGLISH_LEVEL, "");
    }
}