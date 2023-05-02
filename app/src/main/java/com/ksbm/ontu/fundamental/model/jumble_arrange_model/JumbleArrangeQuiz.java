package com.ksbm.ontu.fundamental.model.jumble_arrange_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JumbleArrangeQuiz implements Parcelable {
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
    @SerializedName("quiz_words")
    @Expose
    private List<JumbleArrangeQuizWord> quizWords = null;

    boolean quizAttend;
    boolean quizRight;

    protected JumbleArrangeQuiz(Parcel in) {
        quizQuestion = in.readString();
        rightAnswer = in.readString();
        reward = in.readString();
    }

    public static final Creator<JumbleArrangeQuiz> CREATOR = new Creator<JumbleArrangeQuiz>() {
        @Override
        public JumbleArrangeQuiz createFromParcel(Parcel in) {
            return new JumbleArrangeQuiz(in);
        }

        @Override
        public JumbleArrangeQuiz[] newArray(int size) {
            return new JumbleArrangeQuiz[size];
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

    public void setReward(String reward) {
        this.reward = reward;
    }

    public List<JumbleArrangeQuizWord> getQuizWords() {
        return quizWords;
    }

    public boolean isQuizAttend() {
        return quizAttend;
    }

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public boolean isQuizRight() {
        return quizRight;
    }

    public void setQuizRight(boolean quizRight) {
        this.quizRight = quizRight;
    }

    public void setQuizAttend(boolean quizAttend) {
        this.quizAttend = quizAttend;
    }

    public void setQuizWords(List<JumbleArrangeQuizWord> quizWords) {
        this.quizWords = quizWords;
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
