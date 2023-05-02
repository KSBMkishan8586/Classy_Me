package com.ksbm.ontu.foundation.model;

import android.graphics.Point;

public class PointModel {
    Point mPointFrom; Point mPointTo;

    public PointModel(Point mPointFrom, Point mPointTo) {
        this.mPointFrom = mPointFrom;
        this.mPointTo = mPointTo;
    }

    public Point getmPointFrom() {
        return mPointFrom;
    }

    public void setmPointFrom(Point mPointFrom) {
        this.mPointFrom = mPointFrom;
    }

    public Point getmPointTo() {
        return mPointTo;
    }

    public void setmPointTo(Point mPointTo) {
        this.mPointTo = mPointTo;
    }
}
