package com.ksbm.ontu.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyChildModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("childs")
    @Expose
    private List<ChildData> childs = null;

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

    public List<ChildData> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildData> childs) {
        this.childs = childs;
    }

    public class ChildData {
        @SerializedName("c_id")
        @Expose
        private String cId;
        @SerializedName("c_username")
        @Expose
        private String cUsername;
        @SerializedName("c_fullname")
        @Expose
        private String cFullname;
        @SerializedName("c_school_name")
        @Expose
        private String cSchoolName;
        @SerializedName("c_profile_pic")
        @Expose
        private String cProfilePic;

        public String getcId() {
            return cId;
        }

        public void setcId(String cId) {
            this.cId = cId;
        }

        public String getcUsername() {
            return cUsername;
        }

        public void setcUsername(String cUsername) {
            this.cUsername = cUsername;
        }

        public String getcFullname() {
            return cFullname;
        }

        public void setcFullname(String cFullname) {
            this.cFullname = cFullname;
        }

        public String getcSchoolName() {
            return cSchoolName;
        }

        public void setcSchoolName(String cSchoolName) {
            this.cSchoolName = cSchoolName;
        }

        public String getcProfilePic() {
            return cProfilePic;
        }

        public void setcProfilePic(String cProfilePic) {
            this.cProfilePic = cProfilePic;
        }
    }
}
