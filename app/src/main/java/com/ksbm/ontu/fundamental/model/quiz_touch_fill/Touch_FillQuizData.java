package com.ksbm.ontu.fundamental.model.quiz_touch_fill;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Touch_FillQuizData implements Serializable, Parcelable {
    @SerializedName("quiz_question")
    @Expose
    private String quizQuestion;
    @SerializedName("right_answer")
    @Expose
    private String rightAnswer;
    @SerializedName("complete_right_answer")
    @Expose
    private String complete_right_answer;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("quiz_question_id")
    @Expose
    private String quiz_question_id;

    private boolean clickAnswer = false;
    private boolean ans_right = false;

    protected Touch_FillQuizData(Parcel in) {
        quizQuestion = in.readString();
        rightAnswer = in.readString();
        reward = in.readString();
    }

    public static final Creator<Touch_FillQuizData> CREATOR = new Creator<Touch_FillQuizData>() {
        @Override
        public Touch_FillQuizData createFromParcel(Parcel in) {
            return new Touch_FillQuizData(in);
        }

        @Override
        public Touch_FillQuizData[] newArray(int size) {
            return new Touch_FillQuizData[size];
        }
    };

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public boolean isClickAnswer() {
        return clickAnswer;
    }

    public void setClickAnswer(boolean clickAnswer) {
        this.clickAnswer = clickAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getReward() {
        return reward;
    }

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public String getComplete_right_answer() {
        return complete_right_answer;
    }

    public void setComplete_right_answer(String complete_right_answer) {
        this.complete_right_answer = complete_right_answer;
    }

    public boolean isAns_right() {
        return ans_right;
    }

    public void setAns_right(boolean ans_right) {
        this.ans_right = ans_right;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quizQuestion);
        parcel.writeString(rightAnswer);
        parcel.writeString(reward);
    }
}
