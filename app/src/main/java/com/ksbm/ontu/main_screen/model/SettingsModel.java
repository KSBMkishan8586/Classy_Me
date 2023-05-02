package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<SettingsModelResponse> response = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SettingsModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<SettingsModelResponse> response) {
        this.response = response;
    }

    public class SettingsModelResponse {
        @SerializedName("setting_id")
        @Expose
        private String settingId;
        @SerializedName("setting_key")
        @Expose
        private String settingKey;
        @SerializedName("setting_value")
        @Expose
        private String settingValue;

        public String getSettingId() {
            return settingId;
        }

        public void setSettingId(String settingId) {
            this.settingId = settingId;
        }

        public String getSettingKey() {
            return settingKey;
        }

        public void setSettingKey(String settingKey) {
            this.settingKey = settingKey;
        }

        public String getSettingValue() {
            return settingValue;
        }

        public void setSettingValue(String settingValue) {
            this.settingValue = settingValue;
        }

    }
}
