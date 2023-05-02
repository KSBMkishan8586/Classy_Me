package com.ksbm.ontu.personality_dev.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryWiseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<CategoryWiseModelResponse> response = null;

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

    public List<CategoryWiseModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<CategoryWiseModelResponse> response) {
        this.response = response;
    }

    public class CategoryWiseModelResponse {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("video_url_path")
        @Expose
        private String videoUrlPath;
        @SerializedName("video_type")
        @Expose
        private String videoType;
        @SerializedName("player_type")
        @Expose
        private String player_type;
        @SerializedName("coin_bonus")
        @Expose
        private String coinBonus;
        @SerializedName("coin_charge")
        @Expose
        private String coin_charge;
        @SerializedName("drive_link")
        @Expose
        private String driverLink;
        @SerializedName("first_open")
        @Expose
        private String firstopen;
        @SerializedName("duration")
        @Expose
        private String Duration;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getVideoUrlPath() {
            return videoUrlPath;
        }

        public void setVideoUrlPath(String videoUrlPath) {
            this.videoUrlPath = videoUrlPath;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getCoinBonus() {
            return coinBonus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoin_charge() {
            return coin_charge;
        }

        public String getPlayer_type() {
            return player_type;
        }

        public void setPlayer_type(String player_type) {
            this.player_type = player_type;
        }

        public void setCoin_charge(String coin_charge) {
            this.coin_charge = coin_charge;
        }

        public void setCoinBonus(String coinBonus) {
            this.coinBonus = coinBonus;
        }

        public String getDriverLink() {
            return driverLink;
        }

        public void setDriverLink(String driverLink) {
            this.driverLink = driverLink;
        }


        public String getfirstopen() {
            return firstopen;
        }

        public void setfirstopen(String firstopen) {
            this.firstopen = firstopen;
        }


        public String getDuration() {
            return Duration;
        }

        public void setDuration(String Duration) {
            this.Duration = Duration;
        }



    }
}
