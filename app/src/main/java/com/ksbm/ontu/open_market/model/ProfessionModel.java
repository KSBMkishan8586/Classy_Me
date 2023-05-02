package com.ksbm.ontu.open_market.model;

public class ProfessionModel {
    String proffession, color;
    int image;

    public ProfessionModel(String proffession, int image, String color) {
        this.proffession = proffession;
        this.image = image;
        this.color = color;
    }

    public String getProffession() {
        return proffession;
    }

    public void setProffession(String proffession) {
        this.proffession = proffession;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
