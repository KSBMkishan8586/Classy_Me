package com.ksbm.ontu.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompetitionListModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<CompetitionListData> response = null;

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

    public List<CompetitionListData> getResponse() {
        return response;
    }

    public void setResponse(List<CompetitionListData> response) {
        this.response = response;
    }

    public class CompetitionListData {
        @SerializedName("competition_id")
        @Expose
        private String competitionId;
        @SerializedName("competition_title")
        @Expose
        private String competitionTitle;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("join_amount")
        @Expose
        private String joinAmount;
        @SerializedName("state_rewards")
        @Expose
        private State_country_Rewards stateRewards;
        @SerializedName("country_rewards")
        @Expose
        private State_country_Rewards countryRewards;

        public String getCompetitionId() {
            return competitionId;
        }

        public void setCompetitionId(String competitionId) {
            this.competitionId = competitionId;
        }

        public String getCompetitionTitle() {
            return competitionTitle;
        }

        public void setCompetitionTitle(String competitionTitle) {
            this.competitionTitle = competitionTitle;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getJoinAmount() {
            return joinAmount;
        }

        public void setJoinAmount(String joinAmount) {
            this.joinAmount = joinAmount;
        }

        public State_country_Rewards getStateRewards() {
            return stateRewards;
        }

        public void setStateRewards(State_country_Rewards stateRewards) {
            this.stateRewards = stateRewards;
        }

        public State_country_Rewards getCountryRewards() {
            return countryRewards;
        }

        public void setCountryRewards(State_country_Rewards countryRewards) {
            this.countryRewards = countryRewards;
        }

    }

    public class State_country_Rewards {
        @SerializedName("1")
        @Expose
        private String _1;
        @SerializedName("2")
        @Expose
        private String _2;
        @SerializedName("3")
        @Expose
        private String _3;
        @SerializedName("4")
        @Expose
        private String _4;
        @SerializedName("5")
        @Expose
        private String _5;

        public String get1() {
            return _1;
        }

        public void set1(String _1) {
            this._1 = _1;
        }

        public String get2() {
            return _2;
        }

        public void set2(String _2) {
            this._2 = _2;
        }

        public String get3() {
            return _3;
        }

        public void set3(String _3) {
            this._3 = _3;
        }

        public String get4() {
            return _4;
        }

        public void set4(String _4) {
            this._4 = _4;
        }

        public String get5() {
            return _5;
        }

        public void set5(String _5) {
            this._5 = _5;
        }
    }
}
