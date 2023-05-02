package com.ksbm.ontu.fundamental.model.match_following_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match_Following_QuizData implements Parcelable {
    @SerializedName("quiz_question_id")
    @Expose
    private String quiz_question_id;
    @SerializedName("quiz_question")
    @Expose
    private String quizQuestion;
    @SerializedName("right_answer")
    @Expose
    private String rightAnswer;
    @SerializedName("reward")
    @Expose
    private String reward;
    boolean isSelected;
    boolean isSelectedAns;

    protected Match_Following_QuizData(Parcel in) {
        quizQuestion = in.readString();
        rightAnswer = in.readString();
        reward = in.readString();
        quiz_question_id = in.readString();
    }

    public static final Creator<Match_Following_QuizData> CREATOR = new Creator<Match_Following_QuizData>() {
        @Override
        public Match_Following_QuizData createFromParcel(Parcel in) {
            return new Match_Following_QuizData(in);
        }

        @Override
        public Match_Following_QuizData[] newArray(int size) {
            return new Match_Following_QuizData[size];
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

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getReward() {
        return reward;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isSelectedAns() {
        return isSelectedAns;
    }

    public void setSelectedAns(boolean selectedAns) {
        isSelectedAns = selectedAns;
    }

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
        parcel.writeString(quiz_question_id);
    }
}
