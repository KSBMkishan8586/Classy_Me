package com.ksbm.ontu.fundamental.model.fundamental_quiz_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QuizDetails implements Serializable , Parcelable {
    @SerializedName("heading_1")
    @Expose
    private String heading1;
    @SerializedName("heading_2")
    @Expose
    private String heading2;
    @SerializedName("heading_3")
    @Expose
    private String heading3;
    @SerializedName("heading_1_en")
    @Expose
    private String heading1En;
    @SerializedName("heading_2_en")
    @Expose
    private String heading2En;
    @SerializedName("heading_3_en")
    @Expose
    private String heading3En;
    @SerializedName("total_words")
    @Expose
    private Integer totalWords;
    @SerializedName("words")
    @Expose
    private List<Word_Quiz_List> words = null;

    protected QuizDetails(Parcel in) {
        heading1 = in.readString();
        heading2 = in.readString();
        heading3 = in.readString();
        heading1En = in.readString();
        heading2En = in.readString();
        heading3En = in.readString();
        if (in.readByte() == 0) {
            totalWords = null;
        } else {
            totalWords = in.readInt();
        }
        words = in.createTypedArrayList(Word_Quiz_List.CREATOR);
    }

    public static final Creator<QuizDetails> CREATOR = new Creator<QuizDetails>() {
        @Override
        public QuizDetails createFromParcel(Parcel in) {
            return new QuizDetails(in);
        }

        @Override
        public QuizDetails[] newArray(int size) {
            return new QuizDetails[size];
        }
    };

    public String getHeading1() {
        return heading1;
    }

    public void setHeading1(String heading1) {
        this.heading1 = heading1;
    }

    public String getHeading2() {
        return heading2;
    }

    public void setHeading2(String heading2) {
        this.heading2 = heading2;
    }

    public String getHeading3() {
        return heading3;
    }

    public void setHeading3(String heading3) {
        this.heading3 = heading3;
    }

    public String getHeading1En() {
        return heading1En;
    }

    public void setHeading1En(String heading1En) {
        this.heading1En = heading1En;
    }

    public String getHeading2En() {
        return heading2En;
    }

    public void setHeading2En(String heading2En) {
        this.heading2En = heading2En;
    }

    public String getHeading3En() {
        return heading3En;
    }

    public void setHeading3En(String heading3En) {
        this.heading3En = heading3En;
    }

    public Integer getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(Integer totalWords) {
        this.totalWords = totalWords;
    }

    public List<Word_Quiz_List> getWords() {
        return words;
    }

    public void setWords(List<Word_Quiz_List> words) {
        this.words = words;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(heading1);
        parcel.writeString(heading2);
        parcel.writeString(heading3);
        parcel.writeString(heading1En);
        parcel.writeString(heading2En);
        parcel.writeString(heading3En);
        if (totalWords == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalWords);
        }
        parcel.writeTypedList(words);
    }
}
