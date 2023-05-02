package com.ksbm.ontu.foundation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PalletColorModel implements Parcelable {
    String color_name; int color_code;

    public PalletColorModel(String color_name, int color_code) {
        this.color_name = color_name;
        this.color_code = color_code;
    }

    protected PalletColorModel(Parcel in) {
        color_name = in.readString();
        color_code = in.readInt();
    }

    public static final Creator<PalletColorModel> CREATOR = new Creator<PalletColorModel>() {
        @Override
        public PalletColorModel createFromParcel(Parcel in) {
            return new PalletColorModel(in);
        }

        @Override
        public PalletColorModel[] newArray(int size) {
            return new PalletColorModel[size];
        }
    };

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public int getColor_code() {
        return color_code;
    }

    public void setColor_code(int color_code) {
        this.color_code = color_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(color_name);
        parcel.writeInt(color_code);
    }
}
