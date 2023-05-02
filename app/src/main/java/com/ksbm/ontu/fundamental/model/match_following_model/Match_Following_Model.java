package com.ksbm.ontu.fundamental.model.match_following_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match_Following_Model {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private Match_Following_Response response;

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

    public Match_Following_Response getResponse() {
        return response;
    }

    public void setResponse(Match_Following_Response response) {
        this.response = response;
    }

}
