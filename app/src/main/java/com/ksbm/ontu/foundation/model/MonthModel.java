package com.ksbm.ontu.foundation.model;

public class MonthModel {
    String month_name; String month_details;

    public MonthModel(String month_name, String month_details) {
        this.month_name = month_name;
        this.month_details = month_details;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getMonth_details() {
        return month_details;
    }

    public void setMonth_details(String month_details) {
        this.month_details = month_details;
    }
}
