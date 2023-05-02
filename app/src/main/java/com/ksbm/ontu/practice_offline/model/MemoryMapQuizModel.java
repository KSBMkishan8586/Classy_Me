package com.ksbm.ontu.practice_offline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemoryMapQuizModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<MemoryMapQuizResponse> response = null;

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

    public List<MemoryMapQuizResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MemoryMapQuizResponse> response) {
        this.response = response;
    }

    public class MemoryMapQuizResponse {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("question_image")
        @Expose
        private String question_image;

        @SerializedName("quiz_question")
        @Expose
        private String quizQuestion;
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

        String checked_radio= null;
        boolean checked_radio_button;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
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


        public String getQuestion_image() {
            return question_image;
        }

        public void setQuestion_image(String question_image) {
            this.question_image = question_image;
        }
    }
}
