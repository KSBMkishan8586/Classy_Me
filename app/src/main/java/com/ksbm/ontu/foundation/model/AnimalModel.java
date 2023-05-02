package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimalModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<AnimalModelResponse> response = null;

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

    public List<AnimalModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<AnimalModelResponse> response) {
        this.response = response;
    }

    public class AnimalModelResponse {
        @SerializedName("foundation_type_id")
        @Expose
        private String foundationTypeId;
        @SerializedName("foundation_type")
        @Expose
        private String foundationType;
        @SerializedName("learning_activity")
        @Expose
        private List<LearningActivityModel> learningActivity = null;

        public String getFoundationTypeId() {
            return foundationTypeId;
        }

        public void setFoundationTypeId(String foundationTypeId) {
            this.foundationTypeId = foundationTypeId;
        }

        public String getFoundationType() {
            return foundationType;
        }

        public void setFoundationType(String foundationType) {
            this.foundationType = foundationType;
        }

        public List<LearningActivityModel> getLearningActivity() {
            return learningActivity;
        }

        public void setLearningActivity(List<LearningActivityModel> learningActivity) {
            this.learningActivity = learningActivity;
        }

    }

}
