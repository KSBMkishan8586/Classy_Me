package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoundationCourseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<FoundationCourseResponse> response = null;

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

    public List<FoundationCourseResponse> getResponse() {
        return response;
    }

    public void setResponse(List<FoundationCourseResponse> response) {
        this.response = response;
    }

    public class FoundationCourseResponse {
        @SerializedName("foundation_id")
        @Expose
        private String foundationId;
        @SerializedName("foundation_name")
        @Expose
        private String foundationName;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("earning")
        @Expose
        private String earning;
        @SerializedName("icon_image")
        @Expose
        private String iconImage;

        public String getFoundationId() {
            return foundationId;
        }

        public void setFoundationId(String foundationId) {
            this.foundationId = foundationId;
        }

        public String getFoundationName() {
            return foundationName;
        }

        public void setFoundationName(String foundationName) {
            this.foundationName = foundationName;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getEarning() {
            return earning;
        }

        public void setEarning(String earning) {
            this.earning = earning;
        }

        public String getIconImage() {
            return iconImage;
        }

        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

    }
}
