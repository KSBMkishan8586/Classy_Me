package com.ksbm.ontu.fundamental.model.quiz_touch_fill;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Touch_Fill_Response implements Serializable {
    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("quiz_name")
    @Expose
    private String quizName;
    @SerializedName("quiz_reward")
    @Expose
    private String quizReward;
    @SerializedName("words")
    @Expose
    private List<Touch_FillWord> words = null;
    @SerializedName("quiz_data")
    @Expose
    private List<Touch_FillQuizData> quizData = null;


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

    public List<Touch_FillWord> getWords() {
        return words;
    }

    public void setWords(List<Touch_FillWord> words) {
        this.words = words;
    }

    public List<Touch_FillQuizData> getQuizData() {
        return quizData;
    }

    public void setQuizData(List<Touch_FillQuizData> quizData) {
        this.quizData = quizData;
    }

}
