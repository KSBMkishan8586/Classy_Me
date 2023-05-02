package com.ksbm.ontu.foundation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LearningActivityModel implements Parcelable {
    @SerializedName("foundation_id")
    @Expose
    private String foundationId;
    @SerializedName("learning_id")
    @Expose
    private String learning_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("audio_file")
    @Expose
    private String audioFile;
    @SerializedName("learning_text")
    @Expose
    private String learningText;
    @SerializedName("alphabet_word")
    @Expose
    private String alphabet_word;

    protected LearningActivityModel(Parcel in) {
        foundationId = in.readString();
        learning_id = in.readString();
        name = in.readString();
        image = in.readString();
        audioFile = in.readString();
        learningText = in.readString();
        alphabet_word = in.readString();
    }

    public static final Creator<LearningActivityModel> CREATOR = new Creator<LearningActivityModel>() {
        @Override
        public LearningActivityModel createFromParcel(Parcel in) {
            return new LearningActivityModel(in);
        }

        @Override
        public LearningActivityModel[] newArray(int size) {
            return new LearningActivityModel[size];
        }
    };

    public String getLearning_id() {
        return learning_id;
    }

    public void setLearning_id(String learning_id) {
        this.learning_id = learning_id;
    }

    public String getFoundationId() {
        return foundationId;
    }

    public void setFoundationId(String foundationId) {
        this.foundationId = foundationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getAlphabet_word() {
        return alphabet_word;
    }

    public void setAlphabet_word(String alphabet_word) {
        this.alphabet_word = alphabet_word;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getLearningText() {
        return learningText;
    }

    public void setLearningText(String learningText) {
        this.learningText = learningText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(foundationId);
        parcel.writeString(learning_id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(audioFile);
        parcel.writeString(learningText);
        parcel.writeString(alphabet_word);
    }
}


