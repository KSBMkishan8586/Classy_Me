package com.ksbm.ontu.foundation.model;

public class CurrencyList {
    String country_name; String country_currency;

    public CurrencyList(String country_name, String country_currency) {
        this.country_name = country_name;
        this.country_currency = country_currency;
    }


    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_currency() {
        return country_currency;
    }

    public void setCountry_currency(String country_currency) {
        this.country_currency = country_currency;
    }
}
