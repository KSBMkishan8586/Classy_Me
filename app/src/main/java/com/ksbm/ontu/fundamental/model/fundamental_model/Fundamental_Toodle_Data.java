package com.ksbm.ontu.fundamental.model.fundamental_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fundamental_Toodle_Data implements Serializable {
    @SerializedName("fundamental_id")
    @Expose
    private String fundamentalId;
    @SerializedName("fundamental_name")
    @Expose
    private String fundamentalName;
    @SerializedName("toddle_id")
    @Expose
    private String courseId;
    @SerializedName("toddle_name")
    @Expose
    private String courseName;
    @SerializedName("banner_file")
    @Expose
    private String bannerFile;
    @SerializedName("other_link")
    @Expose
    private String other_link;
    @SerializedName("mimetype")
    @Expose
    private String mimetype;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("earning")
    @Expose
    private String earning;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("complete")
    @Expose
    private String complete;

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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBannerFile() {
        return bannerFile;
    }

    public void setBannerFile(String bannerFile) {
        this.bannerFile = bannerFile;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getOther_link() {
        return other_link;
    }

    public void setOther_link(String other_link) {
        this.other_link = other_link;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

}
