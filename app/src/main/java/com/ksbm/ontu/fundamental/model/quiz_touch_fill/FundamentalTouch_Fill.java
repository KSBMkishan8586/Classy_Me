package com.ksbm.ontu.fundamental.model.quiz_touch_fill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundamentalTouch_Fill {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private Touch_Fill_Response response;

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

    public Touch_Fill_Response getResponse() {
        return response;
    }

    public void setResponse(Touch_Fill_Response response) {
        this.response = response;
    }

}
