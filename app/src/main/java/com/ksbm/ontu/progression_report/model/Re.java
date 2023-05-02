
package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Re {

    @SerializedName("earned_coins")
    private String mEarnedCoins;
    @SerializedName("particular")
    private String mParticular;
    @SerializedName("score")
    private String mScore;
    @SerializedName("total_coins")
    private String mTotalCoins;

    public String getEarnedCoins() {
        return mEarnedCoins;
    }

    public void setEarnedCoins(String earnedCoins) {
        mEarnedCoins = earnedCoins;
    }

    public String getParticular() {
        return mParticular;
    }

    public void setParticular(String particular) {
        mParticular = particular;
    }

    public String getScore() {
        return mScore;
    }

    public void setScore(String score) {
        mScore = score;
    }

    public String getTotalCoins() {
        return mTotalCoins;
    }

    public void setTotalCoins(String totalCoins) {
        mTotalCoins = totalCoins;
    }

}
