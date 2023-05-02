package com.ksbm.ontu.situation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SituationSentenceModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sentence")
    @Expose
    private List<Sentence> sentence = null;

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

    public List<Sentence> getSentence() {
        return sentence;
    }

    public void setSentence(List<Sentence> sentence) {
        this.sentence = sentence;
    }

    public class Sentence {
        @SerializedName("sentence_id")
        @Expose
        private String sentenceId;
        @SerializedName("situation_id")
        @Expose
        private String situationId;
        @SerializedName("sentence_en")
        @Expose
        private String sentenceEn;
        @SerializedName("sentence_hi")
        @Expose
        private String sentenceHi;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("i_text_en")
        @Expose
        private String iTextEn;
        @SerializedName("i_text_hi")
        @Expose
        private String iTextHi;
        @SerializedName("total_words")
        @Expose
        private Integer totalWords;
        @SerializedName("words")
        @Expose
        private List<Word> words = null;

        public String getSentenceId() {
            return sentenceId;
        }

        public void setSentenceId(String sentenceId) {
            this.sentenceId = sentenceId;
        }

        public String getSituationId() {
            return situationId;
        }

        public void setSituationId(String situationId) {
            this.situationId = situationId;
        }

        public String getSentenceEn() {
            return sentenceEn;
        }

        public void setSentenceEn(String sentenceEn) {
            this.sentenceEn = sentenceEn;
        }

        public String getSentenceHi() {
            return sentenceHi;
        }

        public void setSentenceHi(String sentenceHi) {
            this.sentenceHi = sentenceHi;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getiTextEn() {
            return iTextEn;
        }

        public void setiTextEn(String iTextEn) {
            this.iTextEn = iTextEn;
        }

        public String getiTextHi() {
            return iTextHi;
        }

        public void setiTextHi(String iTextHi) {
            this.iTextHi = iTextHi;
        }

        public Integer getTotalWords() {
            return totalWords;
        }

        public void setTotalWords(Integer totalWords) {
            this.totalWords = totalWords;
        }

        public List<Word> getWords() {
            return words;
        }

        public void setWords(List<Word> words) {
            this.words = words;
        }


    }

    public class Word {
        @SerializedName("situation_word_en")
        @Expose
        private String situationWordEn;
        @SerializedName("situation_word_hi")
        @Expose
        private String situationWordHi;
        @SerializedName("i_text_en")
        @Expose
        private String iTextEn;
        @SerializedName("i_text_hi")
        @Expose
        private String iTextHi;

        public String getSituationWordEn() {
            return situationWordEn;
        }

        public void setSituationWordEn(String situationWordEn) {
            this.situationWordEn = situationWordEn;
        }

        public String getSituationWordHi() {
            return situationWordHi;
        }

        public void setSituationWordHi(String situationWordHi) {
            this.situationWordHi = situationWordHi;
        }

        public String getiTextEn() {
            return iTextEn;
        }

        public void setiTextEn(String iTextEn) {
            this.iTextEn = iTextEn;
        }

        public String getiTextHi() {
            return iTextHi;
        }

        public void setiTextHi(String iTextHi) {
            this.iTextHi = iTextHi;
        }

    }
}
