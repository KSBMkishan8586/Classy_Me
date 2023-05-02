package com.ksbm.ontu.fundamental.model.touch_the_word;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TouchWordModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private TouchWordResponse response;

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

    public TouchWordResponse getResponse() {
        return response;
    }

    public void setResponse(TouchWordResponse response) {
        this.response = response;
    }
}
