package com.ksbm.ontu.main_screen.model.language_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageModelResponse {

    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("language_name")
    @Expose
    private String languageName;
    @SerializedName("language_code")
    @Expose
    private String languageCode;

    public LanguageModelResponse(String language_name, String language_code) {
        this.languageName= language_name;
        this.languageCode= language_code;

    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

}
