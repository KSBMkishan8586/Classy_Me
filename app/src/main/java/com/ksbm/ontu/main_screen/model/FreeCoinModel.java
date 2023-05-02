package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FreeCoinModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("responses")
    @Expose
    private List<FreeCoinModelResponses> responses;

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

    public List<FreeCoinModelResponses> getResponses() {
        return responses;
    }

    public void setResponses(List<FreeCoinModelResponses> responses) {
        this.responses = responses;
    }


    public class FreeCoinModelResponses {
        @SerializedName("video_id")
        @Expose
        private String videoId;
        @SerializedName("video_file")
        @Expose
        private String videoFile;
        @SerializedName("video_link")
        @Expose
        private String videoLink;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("is_used")
        @Expose
        private String isUsed;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getVideoFile() {
            return videoFile;
        }

        public void setVideoFile(String videoFile) {
            this.videoFile = videoFile;
        }

        public String getVideoLink() {
            return videoLink;
        }

        public void setVideoLink(String videoLink) {
            this.videoLink = videoLink;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(String isUsed) {
            this.isUsed = isUsed;
        }

    }
}
