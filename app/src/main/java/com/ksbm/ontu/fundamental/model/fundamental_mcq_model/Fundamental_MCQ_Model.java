package com.ksbm.ontu.fundamental.model.fundamental_mcq_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fundamental_MCQ_Model {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private Fundamental_MCQ_Model_Response response;

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

    public Fundamental_MCQ_Model_Response getResponse() {
        return response;
    }

    public void setResponse(Fundamental_MCQ_Model_Response response) {
        this.response = response;
    }


}
