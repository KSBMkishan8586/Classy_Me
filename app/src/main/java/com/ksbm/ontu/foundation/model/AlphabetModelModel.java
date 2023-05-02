package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlphabetModelModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<AlphabetModelResponse> response = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AlphabetModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<AlphabetModelResponse> response) {
        this.response = response;
    }

    public class AlphabetModelResponse {

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
        @SerializedName("foundation_type_id")
        @Expose
        private String foundationTypeId;
        @SerializedName("audio_file")
        @Expose
        private String audioFile;
        @SerializedName("learning_text")
        @Expose
        private String learningText;
        @SerializedName("capital_alphabet_letter")
        @Expose
        private String capital_alphabet_letter;
        @SerializedName("small_alphabet_letter")
        @Expose
        private String small_alphabet_letter;
        @SerializedName("alphabet_word")
        @Expose
        private String alphabet_word;

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

        public void setImage(String image) {
            this.image = image;
        }

        public String getFoundationTypeId() {
            return foundationTypeId;
        }

        public void setFoundationTypeId(String foundationTypeId) {
            this.foundationTypeId = foundationTypeId;
        }

        public String getAlphabet_word() {
            return alphabet_word;
        }

        public void setAlphabet_word(String alphabet_word) {
            this.alphabet_word = alphabet_word;
        }

        public String getCapital_alphabet_letter() {
            return capital_alphabet_letter;
        }

        public void setCapital_alphabet_letter(String capital_alphabet_letter) {
            this.capital_alphabet_letter = capital_alphabet_letter;
        }

        public String getSmall_alphabet_letter() {
            return small_alphabet_letter;
        }

        public void setSmall_alphabet_letter(String small_alphabet_letter) {
            this.small_alphabet_letter = small_alphabet_letter;
        }

        public String getLearning_id() {
            return learning_id;
        }

        public void setLearning_id(String learning_id) {
            this.learning_id = learning_id;
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


    }
}
