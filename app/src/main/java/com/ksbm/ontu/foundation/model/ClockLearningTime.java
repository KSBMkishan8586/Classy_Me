package com.ksbm.ontu.foundation.model;

public class ClockLearningTime {
    int hour; int mint;
    String title_text, learning_text;

    public ClockLearningTime(int hour, int mint, String title_text, String learning_text) {
        this.hour = hour;
        this.mint = mint;
        this.title_text = title_text;
        this.learning_text = learning_text;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMint() {
        return mint;
    }

    public String getTitle_text() {
        return title_text;
    }

    public String getLearning_text() {
        return learning_text;
    }

    public void setLearning_text(String learning_text) {
        this.learning_text = learning_text;
    }

    public void setTitle_text(String title_text) {
        this.title_text = title_text;
    }

    public void setMint(int mint) {
        this.mint = mint;
    }
}
