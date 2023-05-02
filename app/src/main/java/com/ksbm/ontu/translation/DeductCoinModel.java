package com.ksbm.ontu.translation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeductCoinModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("coin_balance")
    @Expose
    private Integer coinBalance;

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

    public Integer getCoinBalance() {
        return coinBalance;
    }

    public void setCoinBalance(Integer coinBalance) {
        this.coinBalance = coinBalance;
    }

}
