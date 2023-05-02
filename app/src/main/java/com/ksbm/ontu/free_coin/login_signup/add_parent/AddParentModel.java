package com.ksbm.ontu.free_coin.login_signup.add_parent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddParentModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
//    @SerializedName("response")
//    @Expose
//    private Response response;

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
//
//    public Response getResponse() {
//        return response;
//    }
//
//    public void setResponse(Response response) {
//        this.response = response;
//    }

}
