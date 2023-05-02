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

public class WebinarModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<WebinarModelResponse> response = null;

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

    public List<WebinarModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<WebinarModelResponse> response) {
        this.response = response;
    }

    public static class WebinarModelResponse implements Serializable {
        @SerializedName("webinar_id")
        @Expose
        private String webinarId;
        @SerializedName("webinar_link")
        @Expose
        private String webinarLink;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        @SerializedName("webinar_date")
        @Expose
        private String webinarDate;
        @SerializedName("webinar_time")
        @Expose
        private String webinarTime;
        @SerializedName("join_amount")
        @Expose
        private String joinAmount;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("w_join_id")
        @Expose
        private String wJoinId;
        @SerializedName("send_date")
        @Expose
        private String sendDate;
        @SerializedName("is_join")
        @Expose
        private Boolean isJoin;
        @SerializedName("join_date")
        @Expose
        private String joinDate;

        public String getWebinarId() {
            return webinarId;
        }

        public void setWebinarId(String webinarId) {
            this.webinarId = webinarId;
        }

        public String getWebinarLink() {
            return webinarLink;
        }

        public void setWebinarLink(String webinarLink) {
            this.webinarLink = webinarLink;
        }

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        public String getWebinarDate() {
            return webinarDate;
        }

        public void setWebinarDate(String webinarDate) {
            this.webinarDate = webinarDate;
        }

        public String getWebinarTime() {
            return webinarTime;
        }

        public void setWebinarTime(String webinarTime) {
            this.webinarTime = webinarTime;
        }

        public String getJoinAmount() {
            return joinAmount;
        }

        public void setJoinAmount(String joinAmount) {
            this.joinAmount = joinAmount;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getwJoinId() {
            return wJoinId;
        }

        public void setwJoinId(String wJoinId) {
            this.wJoinId = wJoinId;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public Boolean getIsJoin() {
            return isJoin;
        }

        public void setIsJoin(Boolean isJoin) {
            this.isJoin = isJoin;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }


        @BindingAdapter("postImage")
        public static void loadImage(ImageView view, String imageUrl) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_square)
                    .apply(new RequestOptions())
                    .into(view);
        }

    }
}
