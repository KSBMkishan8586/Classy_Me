package com.ksbm.ontu.fundamental.model.match_following_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Match_Following_Response {
    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("quiz_name")
    @Expose
    private String quizName;
    @SerializedName("quiz_reward")
    @Expose
    private String quizReward;
    @SerializedName("quiz_data")
    @Expose
    private List<Match_Following_QuizData> quizData = null;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizReward() {
        return quizReward;
    }

    public void setQuizReward(String quizReward) {
        this.quizReward = quizReward;
    }

    public List<Match_Following_QuizData> getQuizData() {
        return quizData;
    }

    public void setQuizData(List<Match_Following_QuizData> quizData) {
        this.quizData = quizData;
    }

}
