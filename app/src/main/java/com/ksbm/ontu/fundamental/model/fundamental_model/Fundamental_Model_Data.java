package com.ksbm.ontu.fundamental.model.fundamental_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fundamental_Model_Data {
    @SerializedName("fundamental_id")
    @Expose
    private String fundamentalId;
    @SerializedName("fundamental_name")
    @Expose
    private String fundamentalName;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("earning")
    @Expose
    private String earning;

    public String getFundamentalId() {
        return fundamentalId;
    }

    public void setFundamentalId(String fundamentalId) {
        this.fundamentalId = fundamentalId;
    }

    public String getFundamentalName() {
        return fundamentalName;
    }

    public void setFundamentalName(String fundamentalName) {
        this.fundamentalName = fundamentalName;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

}
