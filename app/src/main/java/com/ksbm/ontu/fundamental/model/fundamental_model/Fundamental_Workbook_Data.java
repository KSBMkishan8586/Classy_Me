package com.ksbm.ontu.fundamental.model.fundamental_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fundamental_Workbook_Data {
    @SerializedName("fundamental_name")
    @Expose
    private String fundamental_name;
    @SerializedName("fundamental_id")
    @Expose
    private String fundamental_id;
    @SerializedName("workbook_id")
    @Expose
    private String workbookId;
    @SerializedName("workbook_name")
    @Expose
    private String workbookName;
    @SerializedName("workbook_title")
    @Expose
    private String workbook_title;
    @SerializedName("toddle_id")
    @Expose
    private String toddle_id;
    @SerializedName("toddle_name")
    @Expose
    private String toddle_name;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("earning")
    @Expose
    private String earning;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("banner_file")
    @Expose
    private String banner_file;
    @SerializedName("other_link")
    @Expose
    private String other_link;
    @SerializedName("quiz_type_id")
    @Expose
    private String quiz_type_id;
    @SerializedName("quiz_type")
    @Expose
    private String quiz_type;
    @SerializedName("play_status")
    @Expose
    private String play_status;

    public String getWorkbookId() {
        return workbookId;
    }

    public void setWorkbookId(String workbookId) {
        this.workbookId = workbookId;
    }

    public String getWorkbookName() {
        return workbookName;
    }

    public void setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
    }

    public String getFundamental_name() {
        return fundamental_name;
    }

    public void setFundamental_name(String fundamental_name) {
        this.fundamental_name = fundamental_name;
    }

    public String getWorkbook_title() {
        return workbook_title;
    }

    public void setWorkbook_title(String workbook_title) {
        this.workbook_title = workbook_title;
    }

    public String getFundamental_id() {
        return fundamental_id;
    }

    public void setFundamental_id(String fundamental_id) {
        this.fundamental_id = fundamental_id;
    }

    public String getReward() {
        return reward;
    }

    public String getToddle_id() {
        return toddle_id;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setToddle_id(String toddle_id) {
        this.toddle_id = toddle_id;
    }

    public String getToddle_name() {
        return toddle_name;
    }

    public void setToddle_name(String toddle_name) {
        this.toddle_name = toddle_name;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getEarning() {
        return earning;
    }

    public String getBanner_file() {
        return banner_file;
    }

    public String getQuiz_type_id() {
        return quiz_type_id;
    }

    public String getPlay_status() {
        return play_status;
    }

    public void setPlay_status(String play_status) {
        this.play_status = play_status;
    }

    public void setQuiz_type_id(String quiz_type_id) {
        this.quiz_type_id = quiz_type_id;
    }

    public String getQuiz_type() {
        return quiz_type;
    }

    public void setQuiz_type(String quiz_type) {
        this.quiz_type = quiz_type;
    }

    public void setBanner_file(String banner_file) {
        this.banner_file = banner_file;
    }

    public String getOther_link() {
        return other_link;
    }

    public void setOther_link(String other_link) {
        this.other_link = other_link;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

}
