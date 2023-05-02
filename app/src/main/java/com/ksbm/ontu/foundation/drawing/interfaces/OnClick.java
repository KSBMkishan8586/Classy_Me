package com.ksbm.ontu.foundation.drawing.interfaces;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public interface OnClick extends Serializable {

    void click(int position, String type);

}
