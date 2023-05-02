package com.ksbm.ontu.alerts_module.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ksbm.ontu.R;

import java.io.Serializable;
import java.util.List;

public class NoticeBoardModel implements Serializable{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<NoticeBoardModelResponse> response = null;

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

    public List<NoticeBoardModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<NoticeBoardModelResponse> response) {
        this.response = response;
    }

    public static class NoticeBoardModelResponse implements Serializable {
        @SerializedName("noticeid")
        @Expose
        private String noticeid;
        @SerializedName("noticetype")
        @Expose
        private String noticetype;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sort_heading")
        @Expose
        private String sortHeading;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("mimetype")
        @Expose
        private String mimetype;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("added_date")
        @Expose
        private String addedDate;

        public String getNoticeid() {
            return noticeid;
        }

        public void setNoticeid(String noticeid) {
            this.noticeid = noticeid;
        }

        public String getNoticetype() {
            return noticetype;
        }

        public void setNoticetype(String noticetype) {
            this.noticetype = noticetype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSortHeading() {
            return sortHeading;
        }

        public void setSortHeading(String sortHeading) {
            this.sortHeading = sortHeading;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getMimetype() {
            return mimetype;
        }

        public void setMimetype(String mimetype) {
            this.mimetype = mimetype;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAddedDate() {
            return addedDate;
        }

        public void setAddedDate(String addedDate) {
            this.addedDate = addedDate;
        }


        @BindingAdapter("postImage")
        public static void loadImage(ImageView view, String imageUrl) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.home_bg)
                    .apply(new RequestOptions())
                    .into(view);
        }


    }
}
