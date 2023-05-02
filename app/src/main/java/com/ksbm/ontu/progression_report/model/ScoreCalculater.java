
package com.ksbm.ontu.progression_report.model;


import com.google.gson.annotations.SerializedName;



public class ScoreCalculater {

    @SerializedName("have_fun")
    private String mHaveFun;
    @SerializedName("kids_learning")
    private String mKidsLearning;

    public String getHaveFun() {
        return mHaveFun;
    }

    public void setHaveFun(String haveFun) {
        mHaveFun = haveFun;
    }

    public String getKidsLearning() {
        return mKidsLearning;
    }

    public void setKidsLearning(String kidsLearning) {
        mKidsLearning = kidsLearning;
    }

}
