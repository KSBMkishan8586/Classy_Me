package com.ksbm.ontu.fundamental.model.fundamental_quiz_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Workbook_Result_Data implements Serializable {

    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("got_reward")
    @Expose
    private String gotReward;
    @SerializedName("submit_date")
    @Expose
    private String submitDate;
    @SerializedName("total_words")
    @Expose
    private String totalWords;
    @SerializedName("is_right_answer")
    @Expose
    private String totalRightAnswer;
    @SerializedName("overall_result")
    @Expose
    private Integer overallResult;
    @SerializedName("modified_on")
    @Expose
    private String modifiedOn;
    @SerializedName("progress")
    @Expose
    private String progress;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGotReward() {
        return gotReward;
    }

    public void setGotReward(String gotReward) {
        this.gotReward = gotReward;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(String totalWords) {
        this.totalWords = totalWords;
    }

    public String getTotalRightAnswer() {
        return totalRightAnswer;
    }

    public void setTotalRightAnswer(String totalRightAnswer) {
        this.totalRightAnswer = totalRightAnswer;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getOverallResult() {
        return overallResult;
    }

    public void setOverallResult(Integer overallResult) {
        this.overallResult = overallResult;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

}
