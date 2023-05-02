package com.ksbm.ontu.foundation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Drawing_Model {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<DrawingResponse> response = null;

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

    public List<DrawingResponse> getResponse() {
        return response;
    }

    public void setResponse(List<DrawingResponse> response) {
        this.response = response;
    }

    public class DrawingResponse {
        @SerializedName("drawing_id")
        @Expose
        private String drawingId;
        @SerializedName("drawing_images")
        @Expose
        private String drawingImages;

        public String getDrawingId() {
            return drawingId;
        }

        public void setDrawingId(String drawingId) {
            this.drawingId = drawingId;
        }

        public String getDrawingImages() {
            return drawingImages;
        }

        public void setDrawingImages(String drawingImages) {
            this.drawingImages = drawingImages;
        }



    }
}
