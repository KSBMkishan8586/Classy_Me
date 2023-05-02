package com.ksbm.ontu.foundation.model;

public class RandomBgFoundation {
    int card; int drawable_bg;

    public RandomBgFoundation(int card, int drawable_bg) {
        this.card = card;
        this.drawable_bg = drawable_bg;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public int getDrawable_bg() {
        return drawable_bg;
    }

    public void setDrawable_bg(int drawable_bg) {
        this.drawable_bg = drawable_bg;
    }
}
