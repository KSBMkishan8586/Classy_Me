package com.ksbm.ontu.practice_offline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfflineStageModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("memory_map_question_report")
    @Expose
    private String memory_map_question_report;
    @SerializedName("response")
    @Expose
    private List<OfflineStageModelResponse> response = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getMemory_map_question_report() {
        return memory_map_question_report;
    }

    public void setMemory_map_question_report(String memory_map_question_report) {
        this.memory_map_question_report = memory_map_question_report;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OfflineStageModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<OfflineStageModelResponse> response) {
        this.response = response;
    }

    public class OfflineStageModelResponse {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("classid")
        @Expose
        private String classid;
        @SerializedName("stage")
        @Expose
        private String stage;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("total_quiz_earn_level_coin")
        @Expose
        private Integer totalQuizEarnLevelCoin;
        @SerializedName("total_quiz_level_question")
        @Expose
        private Integer totalQuizLevelQuestion;
        @SerializedName("total_quiz_level_attampt")
        @Expose
        private Integer totalQuizLevelAttampt;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("total_attempt_memory_map_question")
        @Expose
        private Integer totalAttemptMemoryMapQuestion;
        @SerializedName("total_right_stage_quiz_ans")
        @Expose
        private Integer totalRightStageQuizAns;
        @SerializedName("memory_map_quiz_play_status")
        @Expose
        private String memoryMapQuizPlayStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getTotalQuizEarnLevelCoin() {
            return totalQuizEarnLevelCoin;
        }

        public void setTotalQuizEarnLevelCoin(Integer totalQuizEarnLevelCoin) {
            this.totalQuizEarnLevelCoin = totalQuizEarnLevelCoin;
        }

        public Integer getTotalQuizLevelQuestion() {
            return totalQuizLevelQuestion;
        }

        public void setTotalQuizLevelQuestion(Integer totalQuizLevelQuestion) {
            this.totalQuizLevelQuestion = totalQuizLevelQuestion;
        }

        public Integer getTotalQuizLevelAttampt() {
            return totalQuizLevelAttampt;
        }

        public void setTotalQuizLevelAttampt(Integer totalQuizLevelAttampt) {
            this.totalQuizLevelAttampt = totalQuizLevelAttampt;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getTotalAttemptMemoryMapQuestion() {
            return totalAttemptMemoryMapQuestion;
        }

        public void setTotalAttemptMemoryMapQuestion(Integer totalAttemptMemoryMapQuestion) {
            this.totalAttemptMemoryMapQuestion = totalAttemptMemoryMapQuestion;
        }

        public Integer getTotalRightStageQuizAns() {
            return totalRightStageQuizAns;
        }

        public void setTotalRightStageQuizAns(Integer totalRightStageQuizAns) {
            this.totalRightStageQuizAns = totalRightStageQuizAns;
        }

        public String getMemoryMapQuizPlayStatus() {
            return memoryMapQuizPlayStatus;
        }

        public void setMemoryMapQuizPlayStatus(String memoryMapQuizPlayStatus) {
            this.memoryMapQuizPlayStatus = memoryMapQuizPlayStatus;
        }
    }
}
