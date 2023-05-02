package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyShopCoin {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<MyShopResponse> response = null;

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

    public List<MyShopResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MyShopResponse> response) {
        this.response = response;
    }

    public class MyShopResponse {
        @SerializedName("id")
        @Expose
        private String Id;
        @SerializedName("coins")
        @Expose
        private String coins;
        @SerializedName("extra_coins")
        @Expose
        private String extra_coins;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("extra_percent")
        @Expose
        private String extra_percent;

        public String getExtra_percent() {
            return extra_percent;
        }

        public void setExtra_percent(String extra_percent) {
            this.extra_percent = extra_percent;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        public String getExtra_coins() {
            return extra_coins;
        }

        public void setExtra_coins(String extra_coins) {
            this.extra_coins = extra_coins;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
