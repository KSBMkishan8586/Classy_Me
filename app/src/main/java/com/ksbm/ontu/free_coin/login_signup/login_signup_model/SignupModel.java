package com.ksbm.ontu.free_coin.login_signup.login_signup_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("username")
    @Expose
    private String username;

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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
