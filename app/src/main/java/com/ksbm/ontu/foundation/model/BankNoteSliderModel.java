package com.ksbm.ontu.foundation.model;

public class BankNoteSliderModel {
    int layout;
    String iButtonText;
    String counts;

    public BankNoteSliderModel(int layout, String iButtonText, String counts) {
        this.layout = layout;
        this.iButtonText = iButtonText;
        this.counts = counts;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public int getLayout() {
        return layout;
    }

    public String getiButtonText() {
        return iButtonText;
    }

    public void setiButtonText(String iButtonText) {
        this.iButtonText = iButtonText;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }
}
