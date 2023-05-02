
package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class DetailedReport {

    @SerializedName("percentage")
    private String mPercentage;
    @SerializedName("res")
    private List<Re> mRes;
    @SerializedName("status")
    private Boolean mStatus;

    public String getPercentage() {
        return mPercentage;
    }

    public void setPercentage(String percentage) {
        mPercentage = percentage;
    }

    public List<Re> getRes() {
        return mRes;
    }

    public void setRes(List<Re> res) {
        mRes = res;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
