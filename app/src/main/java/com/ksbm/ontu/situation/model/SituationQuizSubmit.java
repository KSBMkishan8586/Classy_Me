package com.ksbm.ontu.situation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SituationQuizSubmit {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private SituationQuizResult result;

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

    public SituationQuizResult getResult() {
        return result;
    }

    public void setResult(SituationQuizResult result) {
        this.result = result;
    }

    public class SituationQuizResult {
        @SerializedName("situation_id")
        @Expose
        private String situationId;
        @SerializedName("sentence_id")
        @Expose
        private String sentenceId;
        @SerializedName("situation_question_id")
        @Expose
        private String situationQuestionId;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("got_reward")
        @Expose
        private String gotReward;
        @SerializedName("submit_date")
        @Expose
        private String submitDate;
        @SerializedName("is_right_answer")
        @Expose
        private String isRightAnswer;
        @SerializedName("modified_on")
        @Expose
        private String modifiedOn;
        @SerializedName("result_id")
        @Expose
        private Integer resultId;
        @SerializedName("attempt_no")
        @Expose
        private Integer attemptNo;

        public String getSituationId() {
            return situationId;
        }

        public void setSituationId(String situationId) {
            this.situationId = situationId;
        }

        public String getSentenceId() {
            return sentenceId;
        }

        public void setSentenceId(String sentenceId) {
            this.sentenceId = sentenceId;
        }

        public String getSituationQuestionId() {
            return situationQuestionId;
        }

        public void setSituationQuestionId(String situationQuestionId) {
            this.situationQuestionId = situationQuestionId;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getGotReward() {
            return gotReward;
        }

        public void setGotReward(String gotReward) {
            this.gotReward = gotReward;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }

        public String getIsRightAnswer() {
            return isRightAnswer;
        }

        public void setIsRightAnswer(String isRightAnswer) {
            this.isRightAnswer = isRightAnswer;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Integer getResultId() {
            return resultId;
        }

        public void setResultId(Integer resultId) {
            this.resultId = resultId;
        }

        public Integer getAttemptNo() {
            return attemptNo;
        }

        public void setAttemptNo(Integer attemptNo) {
            this.attemptNo = attemptNo;
        }


    }
}
