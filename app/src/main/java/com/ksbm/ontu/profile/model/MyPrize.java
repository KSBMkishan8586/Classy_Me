package com.ksbm.ontu.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyPrize {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<MyPrizeResponse> response = null;

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

    public List<MyPrizeResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MyPrizeResponse> response) {
        this.response = response;
    }

    public class MyPrizeResponse {
        @SerializedName("id")
        @Expose
        private String Id;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("rank")
        @Expose
        private String rank;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }


}
