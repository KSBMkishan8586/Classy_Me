package com.ksbm.ontu.main_screen.model.class_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassModelResponse {
    @SerializedName("classid")
    @Expose
    private String classid;
    @SerializedName("classname")
    @Expose
    private String classname;

    public ClassModelResponse(String classname) {
        this.classname= classname;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }


}
