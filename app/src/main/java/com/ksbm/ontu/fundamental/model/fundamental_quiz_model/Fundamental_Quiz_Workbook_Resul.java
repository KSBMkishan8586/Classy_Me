package com.ksbm.ontu.fundamental.model.fundamental_quiz_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fundamental_Quiz_Workbook_Resul {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Workbook_Result_Data result;

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

    public Workbook_Result_Data getResult() {
        return result;
    }

    public void setResult(Workbook_Result_Data result) {
        this.result = result;
    }


}
