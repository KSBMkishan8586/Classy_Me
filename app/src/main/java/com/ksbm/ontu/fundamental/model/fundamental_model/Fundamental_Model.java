package com.ksbm.ontu.fundamental.model.fundamental_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fundamental_Model {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<Fundamental_Model_Data> response = null;

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

    public List<Fundamental_Model_Data> getResponse() {
        return response;
    }

    public void setResponse(List<Fundamental_Model_Data> response) {
        this.response = response;
    }
}
