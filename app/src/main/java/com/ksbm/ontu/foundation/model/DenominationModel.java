package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DenominationModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<DenominationModelResponse> response = null;

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

    public List<DenominationModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<DenominationModelResponse> response) {
        this.response = response;
    }

    public class DenominationModelResponse {
        @SerializedName("learning_id")
        @Expose
        private String learningId;
        @SerializedName("currency_image")
        @Expose
        private String currencyImage;
        @SerializedName("currency_in_text")
        @Expose
        private String currency_in_text;
        @SerializedName("currency_in_digit")
        @Expose
        private String currency_in_digit;
        @SerializedName("currency_name")
        @Expose
        private String currencyName;
        @SerializedName("currency_code")
        @Expose
        private String currencyCode;
        @SerializedName("currency_symbol")
        @Expose
        private String currencySymbol;

        public String getLearningId() {
            return learningId;
        }

        public void setLearningId(String learningId) {
            this.learningId = learningId;
        }

        public String getCurrencyImage() {
            return currencyImage;
        }

        public void setCurrencyImage(String currencyImage) {
            this.currencyImage = currencyImage;
        }

        public String getCurrency_in_text() {
            return currency_in_text;
        }

        public void setCurrency_in_text(String currency_in_text) {
            this.currency_in_text = currency_in_text;
        }

        public String getCurrency_in_digit() {
            return currency_in_digit;
        }

        public void setCurrency_in_digit(String currency_in_digit) {
            this.currency_in_digit = currency_in_digit;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }


    }
}
