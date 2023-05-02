package com.ksbm.ontu.fundamental.model.jumble_arrange_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JumbleArrangeModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private JumbleArrangeResponse response;

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

    public JumbleArrangeResponse getResponse() {
        return response;
    }

    public void setResponse(JumbleArrangeResponse response) {
        this.response = response;
    }

}
