package com.ksbm.ontu.fundamental.model.jumble_arrange_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JumbleArrangeQuizWord implements Serializable, Parcelable {
    @SerializedName("words")
    @Expose
    private String words;

    protected JumbleArrangeQuizWord(Parcel in) {
        words = in.readString();
    }

    public static final Creator<JumbleArrangeQuizWord> CREATOR = new Creator<JumbleArrangeQuizWord>() {
        @Override
        public JumbleArrangeQuizWord createFromParcel(Parcel in) {
            return new JumbleArrangeQuizWord(in);
        }

        @Override
        public JumbleArrangeQuizWord[] newArray(int size) {
            return new JumbleArrangeQuizWord[size];
        }
    };

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(words);
    }
}
