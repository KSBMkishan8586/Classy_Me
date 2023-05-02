package com.ksbm.ontu.profile.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ksbm.ontu.R;

import java.util.List;

public class LeaderBoardModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("current_page_no")
    @Expose
    private String currentPageNo;
    @SerializedName("next_page_no")
    @Expose
    private Integer nextPageNo;
    @SerializedName("page_data")
    @Expose
    private Integer pageData;
    @SerializedName("response")
    @Expose
    private List<LeaderBoardResponse> response = null;

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

    public String getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(String currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Integer getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(Integer nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public Integer getPageData() {
        return pageData;
    }

    public void setPageData(Integer pageData) {
        this.pageData = pageData;
    }

    public List<LeaderBoardResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LeaderBoardResponse> response) {
        this.response = response;
    }

    public static class LeaderBoardResponse {
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("coin_balance")
        @Expose
        private String totalEarning;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("user_pic")
        @Expose
        private String userPic;
        @SerializedName("school_name")
        @Expose
        private String schoolName;
        @SerializedName("school_pic")
        @Expose
        private String schoolPic;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getTotalEarning() {
            return totalEarning;
        }

        public void setTotalEarning(String totalEarning) {
            this.totalEarning = totalEarning;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getSchoolPic() {
            return schoolPic;
        }

        public void setSchoolPic(String schoolPic) {
            this.schoolPic = schoolPic;
        }


        @BindingAdapter("postImage")
        public static void loadImage(ImageView view, String imageUrl) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.school_icon)
                    .apply(new RequestOptions())
                    .into(view);
        }


    }
}
