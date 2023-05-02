package com.ksbm.ontu.free_coin.login_signup.login_signup_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenMarketModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("age_criteria")
    @Expose
    private String age_criteria;
    @SerializedName("english_proficiency")
    @Expose
    private String english_proficiency;
    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge_criteria() {
        return age_criteria;
    }

    public void setAge_criteria(String age_criteria) {
        this.age_criteria = age_criteria;
    }

    public String getEnglish_proficiency() {
        return english_proficiency;
    }

    public void setEnglish_proficiency(String english_proficiency) {
        this.english_proficiency = english_proficiency;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
