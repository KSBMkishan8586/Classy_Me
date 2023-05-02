package com.ksbm.ontu.fundamental.model.quiz_touch_fill;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Touch_FillWord implements Parcelable, Serializable {

    @SerializedName("right_answer")
    @Expose
    private String rightAnswer;

    private boolean clickAnswer = false;

    protected Touch_FillWord(Parcel in) {
        rightAnswer = in.readString();
    }

    public static final Creator<Touch_FillWord> CREATOR = new Creator<Touch_FillWord>() {
        @Override
        public Touch_FillWord createFromParcel(Parcel in) {
            return new Touch_FillWord(in);
        }

        @Override
        public Touch_FillWord[] newArray(int size) {
            return new Touch_FillWord[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(rightAnswer);
    }
}
