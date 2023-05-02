package com.ksbm.ontu.free_coin.login_signup.login_signup_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private LoginModelResponse response;
    @SerializedName("open_market")
    @Expose
    private OpenMarketModel open_market;

    public OpenMarketModel getOpen_market() {
        return open_market;
    }

    public void setOpen_market(OpenMarketModel open_market) {
        this.open_market = open_market;
    }

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

    public LoginModelResponse getResponse() {
        return response;
    }

    public void setResponse(LoginModelResponse response) {
        this.response = response;
    }
}
