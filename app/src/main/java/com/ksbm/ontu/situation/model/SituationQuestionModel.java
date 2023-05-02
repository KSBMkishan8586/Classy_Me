package com.ksbm.ontu.situation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SituationQuestionModel implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sentence")
    @Expose
    private List<QuestionList> questions = null;

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

    public List<QuestionList> getSentence() {
        return questions;
    }

    public void setSentence(List<QuestionList> sentence) {
        this.questions = sentence;
    }

    public class QuestionList implements Serializable {
        @SerializedName("situation_question_id")
        @Expose
        private String situationQuestionId;
        @SerializedName("quiz_type_id")
        @Expose
        private String quizTypeId;
        @SerializedName("quiz_type")
        @Expose
        private String quizType;
        @SerializedName("sentence_id")
        @Expose
        private String sentenceId;
        @SerializedName("situation_id")
        @Expose
        private String situationId;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("icon_image")
        @Expose
        private String iconImage;
        @SerializedName("situation_question")
        @Expose
        private String situationQuestion;
        @SerializedName("situation_question_hi")
        @Expose
        private String situation_question_hi;
        @SerializedName("right_answer")
        @Expose
        private String rightAnswer;
        @SerializedName("situation_heading")
        @Expose
        private String situation_heading;
        @SerializedName("total_words")
        @Expose
        private String totalWords;
        @SerializedName("words")
        @Expose
        private List<Word_Question> questions_words = null;

        protected QuestionList(Parcel in) {
            situationQuestionId = in.readString();
            quizTypeId = in.readString();
            quizType = in.readString();
            sentenceId = in.readString();
            situationId = in.readString();
            reward = in.readString();
            iconImage = in.readString();
            situationQuestion = in.readString();
            situation_question_hi = in.readString();
            rightAnswer = in.readString();
            situation_heading = in.readString();
            totalWords = in.readString();
        }



        public String getSituationQuestionId() {
            return situationQuestionId;
        }

        public void setSituationQuestionId(String situationQuestionId) {
            this.situationQuestionId = situationQuestionId;
        }

        public String getSituation_question_hi() {
            return situation_question_hi;
        }

        public void setSituation_question_hi(String situation_question_hi) {
            this.situation_question_hi = situation_question_hi;
        }

        public String getQuizTypeId() {
            return quizTypeId;
        }

        public void setQuizTypeId(String quizTypeId) {
            this.quizTypeId = quizTypeId;
        }

        public String getQuizType() {
            return quizType;
        }

        public void setQuizType(String quizType) {
            this.quizType = quizType;
        }

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

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getIconImage() {
            return iconImage;
        }

        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

        public String getSituationQuestion() {
            return situationQuestion;
        }

        public void setSituationQuestion(String situationQuestion) {
            this.situationQuestion = situationQuestion;
        }

        public String getSituation_heading() {
            return situation_heading;
        }

        public void setSituation_heading(String situation_heading) {
            this.situation_heading = situation_heading;
        }

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getTotalWords() {
            return totalWords;
        }

        public void setTotalWords(String totalWords) {
            this.totalWords = totalWords;
        }

        public List<Word_Question> getWords() {
            return questions_words;
        }

        public void setWords(List<Word_Question> words) {
            this.questions_words = words;
        }

    }

    public class Word_Question implements Serializable{
        @SerializedName("situation_word")
        @Expose
        private String situationWord;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("answer")
        @Expose
        private String answer;

        boolean isSelected;
        boolean isSelectedAns;

        protected Word_Question(Parcel in) {
            situationWord = in.readString();
            question = in.readString();
            answer = in.readString();
        }



        public String getSituationWord() {
            return situationWord;
        }

        public void setSituationWord(String situationWord) {
            this.situationWord = situationWord;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public boolean isSelectedAns() {
            return isSelectedAns;
        }

        public void setSelectedAns(boolean selectedAns) {
            isSelectedAns = selectedAns;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
