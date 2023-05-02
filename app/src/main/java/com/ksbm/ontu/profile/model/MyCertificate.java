package com.ksbm.ontu.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyCertificate {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<MyCertificateResponse> response = null;

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

    public List<MyCertificateResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MyCertificateResponse> response) {
        this.response = response;
    }

    public class MyCertificateResponse {
        @SerializedName("certificate_id")
        @Expose
        private String certificateId;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("url")
        @Expose
        private String url;

        public String getCertificateId() {
            return certificateId;
        }

        public void setCertificateId(String certificateId) {
            this.certificateId = certificateId;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}
