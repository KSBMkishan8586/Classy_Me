package com.ksbm.ontu.fundamental.model.fundamental_quiz_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Word_Quiz_List implements Parcelable {
    @SerializedName("quiz_words")
    @Expose
    private String quizWords;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("right_answer")
    @Expose
    private String rightAnswer;
    @SerializedName("quiz_word_id")
    @Expose
    private String quiz_word_id;

    private boolean all_checked;
    private boolean right_ans_by_position= false;

    protected Word_Quiz_List(Parcel in) {
        quizWords = in.readString();
        reward = in.readString();
        rightAnswer = in.readString();
        quiz_word_id = in.readString();
        all_checked = in.readByte() != 0;
        right_ans_by_position = in.readByte() != 0;
    }

    public static final Creator<Word_Quiz_List> CREATOR = new Creator<Word_Quiz_List>() {
        @Override
        public Word_Quiz_List createFromParcel(Parcel in) {
            return new Word_Quiz_List(in);
        }

        @Override
        public Word_Quiz_List[] newArray(int size) {
            return new Word_Quiz_List[size];
        }
    };

    public String getQuizWords() {
        return quizWords;
    }

    public void setQuizWords(String quizWords) {
        this.quizWords = quizWords;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public boolean getAll_checked() {
        return all_checked;
    }

    public void setAll_checked(boolean all_checked) {
        this.all_checked = all_checked;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public boolean isRight_ans_by_position() {
        return right_ans_by_position;
    }

    public void setRight_ans_by_position(boolean right_ans_by_position) {
        this.right_ans_by_position = right_ans_by_position;
    }

    public String getQuiz_word_id() {
        return quiz_word_id;
    }

    public void setQuiz_word_id(String quiz_word_id) {
        this.quiz_word_id = quiz_word_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quizWords);
        parcel.writeString(reward);
        parcel.writeString(rightAnswer);
        parcel.writeString(quiz_word_id);
        parcel.writeByte((byte) (all_checked ? 1 : 0));
        parcel.writeByte((byte) (right_ans_by_position ? 1 : 0));
    }
}
