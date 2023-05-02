package com.ksbm.ontu.main_screen.model.state_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateModelResponse {
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city_id")
    @Expose
    private String city_id;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
