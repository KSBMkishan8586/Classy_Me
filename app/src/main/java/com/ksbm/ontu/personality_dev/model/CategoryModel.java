package com.ksbm.ontu.personality_dev.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<CategoryModelResponse> response = null;

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

    public List<CategoryModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<CategoryModelResponse> response) {
        this.response = response;
    }

    public class CategoryModelResponse {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("classid")
        @Expose
        private String classid;
        @SerializedName("language_id")
        @Expose
        private String languageId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image")
        @Expose
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getLanguageId() {
            return languageId;
        }

        public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


    }
}
