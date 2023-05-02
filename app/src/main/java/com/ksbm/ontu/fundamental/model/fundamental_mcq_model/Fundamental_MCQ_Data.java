package com.ksbm.ontu.fundamental.model.fundamental_mcq_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fundamental_MCQ_Data implements Serializable, Parcelable {
    @SerializedName("quiz_question")
    @Expose
    private String quizQuestion;
    @SerializedName("quiz_question_id")
    @Expose
    private String quiz_question_id;
    @SerializedName("option_1")
    @Expose
    private String option1;
    @SerializedName("option_2")
    @Expose
    private String option2;
    @SerializedName("option_3")
    @Expose
    private String option3;
    @SerializedName("option_4")
    @Expose
    private String option4;
    @SerializedName("option_5")
    @Expose
    private String option5;
    @SerializedName("right_answer")
    @Expose
    private String rightAnswer;
    @SerializedName("reward")
    @Expose
    private String reward;

    @SerializedName("devil_right_answer")
    @Expose
    private String devil_right_answer;

    String checked_radio= null;
    boolean checked_radio_button;

    protected Fundamental_MCQ_Data(Parcel in) {
        quizQuestion = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        option5 = in.readString();
        rightAnswer = in.readString();
        reward = in.readString();
        quiz_question_id = in.readString();
        devil_right_answer = in.readString();
    }

    public static final Creator<Fundamental_MCQ_Data> CREATOR = new Creator<Fundamental_MCQ_Data>() {
        @Override
        public Fundamental_MCQ_Data createFromParcel(Parcel in) {
            return new Fundamental_MCQ_Data(in);
        }

        @Override
        public Fundamental_MCQ_Data[] newArray(int size) {
            return new Fundamental_MCQ_Data[size];
        }
    };

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getDeviRightAnswer(){
        return devil_right_answer;
    }
    public void setDevilRightAnswer (String devil_right_answer){
        this.devil_right_answer = devil_right_answer;
    }


    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public String isChecked_radio() {
        return checked_radio;
    }

    public void setChecked_radio(String checked_radio) {
        this.checked_radio = checked_radio;
    }

    public boolean isChecked_radio_button() {
        return checked_radio_button;
    }

    public void setChecked_radio_button(boolean checked_radio_button) {
        this.checked_radio_button = checked_radio_button;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quizQuestion);
        parcel.writeString(option1);
        parcel.writeString(option2);
        parcel.writeString(option3);
        parcel.writeString(option4);
        parcel.writeString(option5);
        parcel.writeString(rightAnswer);
        parcel.writeString(reward);
        parcel.writeString(quiz_question_id);
        parcel.writeString(devil_right_answer);

    }
}
