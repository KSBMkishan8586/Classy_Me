package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrencyModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<CurrencyModelResponse> response = null;

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

    public List<CurrencyModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<CurrencyModelResponse> response) {
        this.response = response;
    }


    public class CurrencyModelResponse {
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("country_flag_image")
        @Expose
        private String countryFlagImage;
        @SerializedName("currency_name")
        @Expose
        private String currencyName;
        @SerializedName("currency_code")
        @Expose
        private String currencyCode;
        @SerializedName("currency_symbol")
        @Expose
        private String currencySymbol;
        @SerializedName("dial_code")
        @Expose
        private String dialCode;

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryFlagImage() {
            return countryFlagImage;
        }

        public void setCountryFlagImage(String countryFlagImage) {
            this.countryFlagImage = countryFlagImage;
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

        public String getDialCode() {
            return dialCode;
        }

        public void setDialCode(String dialCode) {
            this.dialCode = dialCode;
        }


    }
}
