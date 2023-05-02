package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoundationQuizModel implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<FoundationQuizResponse> response = null;

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

    public List<FoundationQuizResponse> getResponse() {
        return response;
    }

    public void setResponse(List<FoundationQuizResponse> response) {
        this.response = response;
    }

    public class FoundationQuizResponse implements Serializable {
        @SerializedName("question_id")
        @Expose
        private String questionId;
        @SerializedName("quiz_type_id")
        @Expose
        private String quizTypeId;
        @SerializedName("quiz_type")
        @Expose
        private String quizType;
        @SerializedName("sub_pattern_type")
        @Expose
        private String subPatternType;
        @SerializedName("foundation_id")
        @Expose
        private String foundationId;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("icon_image")
        @Expose
        private String iconImage;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("question_text")
        @Expose
        private String questionText;
        @SerializedName("question_image")
        @Expose
        private String questionImage;
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
        @SerializedName("right_answer")
        @Expose
        private String rightAnswer;
        @SerializedName("heading_1")
        @Expose
        private String heading1;
        @SerializedName("heading_3")
        @Expose
        private String heading3;
        @SerializedName("heading_2")
        @Expose
        private String heading2;
        @SerializedName("total_words")
        @Expose
        private Integer totalWords;
        @SerializedName("words")
        @Expose
        private List<FoundationWord> words = null;

        String checked_radio= null;
        boolean checked_radio_button;
        private boolean isAttemptQuiz;
        private String userTotalReward = "0" ;

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

        public String getUserTotalReward() {
            return userTotalReward;
        }

        public void setUserTotalReward(String userTotalReward) {
            this.userTotalReward = userTotalReward;
        }

        public boolean isAttemptQuiz() {
            return isAttemptQuiz;
        }

        public void setAttemptQuiz(boolean attemptQuiz) {
            isAttemptQuiz = attemptQuiz;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
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

        public String getSubPatternType() {
            return subPatternType;
        }

        public void setSubPatternType(String subPatternType) {
            this.subPatternType = subPatternType;
        }

        public String getFoundationId() {
            return foundationId;
        }

        public void setFoundationId(String foundationId) {
            this.foundationId = foundationId;
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

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getQuestionImage() {
            return questionImage;
        }

        public void setQuestionImage(String questionImage) {
            this.questionImage = questionImage;
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

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getHeading1() {
            return heading1;
        }

        public void setHeading1(String heading1) {
            this.heading1 = heading1;
        }

        public String getHeading3() {
            return heading3;
        }

        public void setHeading3(String heading3) {
            this.heading3 = heading3;
        }

        public String getHeading2() {
            return heading2;
        }

        public void setHeading2(String heading2) {
            this.heading2 = heading2;
        }

        public Integer getTotalWords() {
            return totalWords;
        }

        public void setTotalWords(Integer totalWords) {
            this.totalWords = totalWords;
        }

        public List<FoundationWord> getWords() {
            return words;
        }

        public void setWords(List<FoundationWord> words) {
            this.words = words;
        }
    }

    public class FoundationWord implements Serializable {
        @SerializedName("word")
        @Expose
        private String word;
        @SerializedName("right_answer")
        @Expose
        private String rightAnswer;
        @SerializedName("question_text")
        @Expose
        private String questionText;
        @SerializedName("question_image")
        @Expose
        private String questionImage;
        @SerializedName("answer")
        @Expose
        private String answer;
        private boolean clickAnswer;
        private boolean ans_right = false;

        private boolean all_checked;
        private boolean right_ans_by_position= false;

        public boolean isClickAnswer() {
            return clickAnswer;
        }
        public void setClickAnswer(boolean clickAnswer) {
            this.clickAnswer = clickAnswer;
        }
        public boolean isAns_right() {
            return ans_right;
        }

        public void setAns_right(boolean ans_right) {
            this.ans_right = ans_right;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getQuestionImage() {
            return questionImage;
        }

        public void setQuestionImage(String questionImage) {
            this.questionImage = questionImage;
        }

        public boolean isAll_checked() {
            return all_checked;
        }

        public void setAll_checked(boolean all_checked) {
            this.all_checked = all_checked;
        }

        public boolean isRight_ans_by_position() {
            return right_ans_by_position;
        }

        public void setRight_ans_by_position(boolean right_ans_by_position) {
            this.right_ans_by_position = right_ans_by_position;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
