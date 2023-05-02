package com.ksbm.ontu.situation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SituationModelResponse {
    @SerializedName("situation_id")
    @Expose
    private String situationId;
    @SerializedName("situation_name")
    @Expose
    private String situationName;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("icon_image")
    @Expose
    private String iconImage;
    @SerializedName("i_text")
    @Expose
    private String iText;
    @SerializedName("other_link")
    @Expose
    private String otherLink;

    public String getSituationId() {
        return situationId;
    }

    public void setSituationId(String situationId) {
        this.situationId = situationId;
    }

    public String getSituationName() {
        return situationName;
    }

    public void setSituationName(String situationName) {
        this.situationName = situationName;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getiText() {
        return iText;
    }

    public void setiText(String iText) {
        this.iText = iText;
    }

    public String getOtherLink() {
        return otherLink;
    }

    public void setOtherLink(String otherLink) {
        this.otherLink = otherLink;
    }


//
//    public String getOtherLink() {
//        return otherLink;
//    }
//
//    public void setOtherLink(String otherLink) {
//        this.otherLink = otherLink;
//    }
}
