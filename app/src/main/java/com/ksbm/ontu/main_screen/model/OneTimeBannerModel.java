package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneTimeBannerModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<OneTimeBannerResponse> response = null;

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

    public List<OneTimeBannerResponse> getResponse() {
        return response;
    }

    public void setResponse(List<OneTimeBannerResponse> response) {
        this.response = response;
    }

    public class OneTimeBannerResponse {
        @SerializedName("banner_id")
        @Expose
        private String bannerId;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("mimetype")
        @Expose
        private String mimetype;
        @SerializedName("added_date")
        @Expose
        private String addedDate;

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getMimetype() {
            return mimetype;
        }

        public void setMimetype(String mimetype) {
            this.mimetype = mimetype;
        }

        public String getAddedDate() {
            return addedDate;
        }

        public void setAddedDate(String addedDate) {
            this.addedDate = addedDate;
        }
    }
}
