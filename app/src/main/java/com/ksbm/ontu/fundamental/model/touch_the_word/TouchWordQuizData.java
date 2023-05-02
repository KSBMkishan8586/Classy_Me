package com.ksbm.ontu.fundamental.model.touch_the_word;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TouchWordQuizData implements Parcelable {
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

    boolean attendAnswer;
    boolean isRightAnswer;

    protected TouchWordQuizData(Parcel in) {
        quizQuestion = in.readString();
        quiz_question_id = in.readString();
        rightAnswer = in.readString();
        reward = in.readString();
    }

    public static final Creator<TouchWordQuizData> CREATOR = new Creator<TouchWordQuizData>() {
        @Override
        public TouchWordQuizData createFromParcel(Parcel in) {
            return new TouchWordQuizData(in);
        }

        @Override
        public TouchWordQuizData[] newArray(int size) {
            return new TouchWordQuizData[size];
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

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public boolean isAttendAnswer() {
        return attendAnswer;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    public void setAttendAnswer(boolean attendAnswer) {
        this.attendAnswer = attendAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quizQuestion);
        parcel.writeString(quiz_question_id);
        parcel.writeString(rightAnswer);
        parcel.writeString(reward);
    }
}
