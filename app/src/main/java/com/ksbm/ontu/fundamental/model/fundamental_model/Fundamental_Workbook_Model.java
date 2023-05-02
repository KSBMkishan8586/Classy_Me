package com.ksbm.ontu.fundamental.model.fundamental_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fundamental_Workbook_Model {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<Fundamental_Workbook_Data> response = null;

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

    public List<Fundamental_Workbook_Data> getResponse() {
        return response;
    }

    public void setResponse(List<Fundamental_Workbook_Data> response) {
        this.response = response;
    }
}
