package com.ksbm.ontu.alerts_module.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingEventModel {



    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<UpcomingEventModel.UpcomingNotesResponse> response = null;

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

    public List<UpcomingEventModel.UpcomingNotesResponse> getResponse() {
        return response;
    }

    public void setResponse(List<UpcomingEventModel.UpcomingNotesResponse> response) {
        this.response = response;
    }

    public class UpcomingNotesResponse {
        @SerializedName("c_date")
        @Expose
        private String cdate;
        @SerializedName("title")
        @Expose
        private String title;

        public String getcDate() {
            return cdate;
        }

        public void setcDate(String cDate) {
            this.cdate = cDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    }



