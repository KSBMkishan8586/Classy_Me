package com.ksbm.ontu.situation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SituationModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("bg_image")
    @Expose
    private String bg_image;
    @SerializedName("response")
    @Expose
    private List<SituationModelResponse> response = null;

    public Integer getStatus() {
        return status;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
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

    public List<SituationModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<SituationModelResponse> response) {
        this.response = response;
    }
}
