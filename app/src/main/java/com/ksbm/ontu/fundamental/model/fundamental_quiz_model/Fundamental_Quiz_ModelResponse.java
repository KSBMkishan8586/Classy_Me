package com.ksbm.ontu.fundamental.model.fundamental_quiz_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fundamental_Quiz_ModelResponse {

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
    private QuizDetails quizData;

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

    public QuizDetails getQuizData() {
        return quizData;
    }

    public void setQuizData(QuizDetails quizData) {
        this.quizData = quizData;
    }
}
