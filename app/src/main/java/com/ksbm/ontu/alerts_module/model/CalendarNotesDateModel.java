package com.ksbm.ontu.alerts_module.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarNotesDateModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<CalendarNotesDateResponse> response = null;

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

    public List<CalendarNotesDateResponse> getResponse() {
        return response;
    }

    public void setResponse(List<CalendarNotesDateResponse> response) {
        this.response = response;
    }

    public class CalendarNotesDateResponse {
        @SerializedName("c_date")
        @Expose
        private String cdate;

        public String getcDate() {
            return cdate;
        }

        public void setcDate(String cDate) {
            this.cdate = cDate;
        }


    }
}
