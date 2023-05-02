
package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;


public class InternalExam {

    @SerializedName("have_fun")
    private String mHaveFun;
    @SerializedName("kids_learning")
    private String mKidsLearning;
    @SerializedName("score_calculater")
    private ScoreCalculater mScoreCalculater;
    @SerializedName("status")
    private Boolean mStatus;

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

    public ScoreCalculater getScoreCalculater() {
        return mScoreCalculater;
    }

    public void setScoreCalculater(ScoreCalculater scoreCalculater) {
        mScoreCalculater = scoreCalculater;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
