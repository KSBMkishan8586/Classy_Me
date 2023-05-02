package com.ksbm.ontu.alerts_module.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarNotesModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<CalendarNotesResponse> response = null;

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

    public List<CalendarNotesResponse> getResponse() {
        return response;
    }

    public void setResponse(List<CalendarNotesResponse> response) {
        this.response = response;
    }

    public class CalendarNotesResponse {
        @SerializedName("c_note_id")
        @Expose
        private String cNoteId;
        @SerializedName("c_title")
        @Expose
        private String ctitle;
        @SerializedName("c_description")
        @Expose
        private String cdescription;
        @SerializedName("c_date")
        @Expose
        private String cdate;
        @SerializedName("added_by")
        @Expose
        private String addedBy;

        public String getcNoteId() {
            return cNoteId;
        }

        public void setcNoteId(String cNoteId) {
            this.cNoteId = cNoteId;
        }

        public String getCtitle() {
            return ctitle;
        }

        public String getCdescription() {
            return cdescription;
        }

        public void setCdescription(String cdescription) {
            this.cdescription = cdescription;
        }

        public String getCdate() {
            return cdate;
        }

        public void setCdate(String cdate) {
            this.cdate = cdate;
        }

        public void setCtitle(String ctitle) {
            this.ctitle = ctitle;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }
    }
}
