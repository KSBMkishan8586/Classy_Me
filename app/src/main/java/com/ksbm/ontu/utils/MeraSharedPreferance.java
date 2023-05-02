package com.ksbm.ontu.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class MeraSharedPreferance {
    private static MeraSharedPreferance meraSharedPreferance;
    private SharedPreferences sharedPreferences;
    private String isOpenMarket = "isOpenMarket";
    private String PackageUpgraded = "PackageUpgraded";
    private String profession = "profession";
    private String age = "age";
    private String level = "level";
    private String plan_upgrade_amount = "plan_upgrade_amount";


    public static MeraSharedPreferance getInstance(Context context) {
        if (meraSharedPreferance == null) {
            meraSharedPreferance = new MeraSharedPreferance(context);
        }
        return meraSharedPreferance;
    }

    private MeraSharedPreferance(Context context) {
        sharedPreferences = context.getSharedPreferences("MohammadGauriSharedPreferance",Context.MODE_PRIVATE);
    }
    public void clearPreferance() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .clear();
        prefsEditor.commit();

//        Intent intent = new Intent(activity, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(intent);
//        activity.finish();
    }


    //Is Data Inserted
    public void isOpenMarketSave(boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putBoolean(isOpenMarket, value);
        prefsEditor.commit();
    }
    public boolean isOpenMarketGet() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean(isOpenMarket, false);
        }
        return false;
    }

    //Is Data Inserted
    public void isPackageUpgradedSave(boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putBoolean(PackageUpgraded, value);
        prefsEditor.commit();
    }
    public boolean isPackageUpgraded() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean(PackageUpgraded, false);
        }
        return false;
    }



    //Is Data Inserted
    public void professionSave(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(profession, value);
        prefsEditor.commit();
    }
    public String professionGet() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(profession, "");
        }
        return "";
    }

    //Is Data Inserted
    public void ageSave(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(age, value);
        prefsEditor.commit();
    }
    public String ageGet() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(age, "");
        }
        return "";
    }

    //Is Data Inserted
    public void levelSave(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(level, value);
        prefsEditor.commit();
    }
    public String levelGet() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(level, "");
        }
        return "";
    }

    //Is Data Inserted
    public void plan_upgrade_amountSave(String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(plan_upgrade_amount, value);
        prefsEditor.commit();
    }
    public String plan_upgrade_amountGet() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(plan_upgrade_amount, "");
        }
        return "";
    }


    public void condition(){
        if (meraSharedPreferance.isOpenMarketGet()){
            if (meraSharedPreferance.professionGet().equalsIgnoreCase("School Students")){

                if (meraSharedPreferance.ageGet().equalsIgnoreCase("Above 13")){

                    if (meraSharedPreferance.levelGet().equalsIgnoreCase("Easy")){


                    }else if (meraSharedPreferance.levelGet().equalsIgnoreCase("Medium")){


                    }else if (meraSharedPreferance.levelGet().equalsIgnoreCase("Advance")){


                    }

                }else if (meraSharedPreferance.ageGet().equalsIgnoreCase("Below 13")){

                    if (meraSharedPreferance.levelGet().equalsIgnoreCase("Easy")){


                    }else if (meraSharedPreferance.levelGet().equalsIgnoreCase("Medium")){


                    }else if (meraSharedPreferance.levelGet().equalsIgnoreCase("Advance")){


                    }
                }

            }else {
                if (meraSharedPreferance.levelGet().equalsIgnoreCase("Easy")){


                }else if (meraSharedPreferance.levelGet().equalsIgnoreCase("Medium")){


                }else if (meraSharedPreferance.levelGet().equalsIgnoreCase("Advance")){


                }

            }
        }
    }



}
