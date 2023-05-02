package com.ksbm.ontu.practice_offline.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OfflineLevelList {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Stage")
    @Expose
    private List<StageData> stage = null;

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

    public List<StageData> getStage() {
        return stage;
    }

    public void setStage(List<StageData> stage) {
        this.stage = stage;
    }

    public class StageData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("classid")
        @Expose
        private String classid;
        @SerializedName("total_question")
        @Expose
        private String totalQuestion;
        @SerializedName("total_right_ans")
        @Expose
        private String totalRightAns;
        @SerializedName("levelData")
        @Expose
        private List<LevelData> levelData = null;

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

        public String getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(String totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getTotalRightAns() {
            return totalRightAns;
        }

        public void setTotalRightAns(String totalRightAns) {
            this.totalRightAns = totalRightAns;
        }

        public List<LevelData> getLevelData() {
            return levelData;
        }

        public void setLevelData(List<LevelData> levelData) {
            this.levelData = levelData;
        }

    }

    public class LevelData implements Parcelable{
        @SerializedName("level_id")
        @Expose
        private String levelId;
        @SerializedName("level_name")
        @Expose
        private String levelName;
        @SerializedName("total_right_ans")
        @Expose
        private String totalRightAns;
        @SerializedName("total_earning_coin")
        @Expose
        private String totalEarningCoin;
        @SerializedName("total_attempt_question")
        @Expose
        private String total_attempt_question;

        @SerializedName("level_play")
        @Expose
        private String level_play;


        @SerializedName("self_checked")
        @Expose
        private String self_checked;


        @SerializedName("level_quiz")
        @Expose
        private List<LevelQuiz> levelQuiz = null;

        protected LevelData(Parcel in) {
            levelId = in.readString();
            levelName = in.readString();
            totalRightAns = in.readString();
            totalEarningCoin = in.readString();
            level_play=in.readString();
            self_checked=in.readString();
            levelQuiz = in.createTypedArrayList(LevelQuiz.CREATOR);
        }

        public String getSelf_checked() {
            return self_checked;
        }

        public void setSelf_checked(String self_checked) {
            this.self_checked = self_checked;
        }

        public String getLevel_play() {
            return level_play;
        }

        public void setLevel_play(String level_play) {
            this.level_play = level_play;
        }

        public  final Creator<LevelData> CREATOR = new Creator<LevelData>() {
            @Override
            public LevelData createFromParcel(Parcel in) {
                return new LevelData(in);
            }

            @Override
            public LevelData[] newArray(int size) {
                return new LevelData[size];
            }
        };

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getTotalRightAns() {
            return totalRightAns;
        }

        public void setTotalRightAns(String totalRightAns) {
            this.totalRightAns = totalRightAns;
        }

        public String getTotal_attempt_question() {
            return total_attempt_question;
        }

        public void setTotal_attempt_question(String total_attempt_question) {
            this.total_attempt_question = total_attempt_question;
        }

        public String getTotalEarningCoin() {
            return totalEarningCoin;
        }

        public void setTotalEarningCoin(String totalEarningCoin) {
            this.totalEarningCoin = totalEarningCoin;
        }

        public List<LevelQuiz> getLevelQuiz() {
            return levelQuiz;
        }

        public void setLevelQuiz(List<LevelQuiz> levelQuiz) {
            this.levelQuiz = levelQuiz;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(levelId);
            parcel.writeString(levelName);
            parcel.writeString(totalRightAns);
            parcel.writeString(totalEarningCoin);
            parcel.writeString(level_play);
            parcel.writeString(self_checked);
            parcel.writeTypedList(levelQuiz);

        }
    }

    public static class LevelQuiz implements Parcelable {
        @SerializedName("question_id")
        @Expose
        private String questionId;
        @SerializedName("level_id")
        @Expose
        private String levelId;
        @SerializedName("stage_id")
        @Expose
        private String stageId;
        @SerializedName("question_hi")
        @Expose
        private String questionHi;
        @SerializedName("question_en")
        @Expose
        private String questionEn;
        @SerializedName("coin_per_question")
        @Expose
        private String coinPerQuestion;

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("media_url")
        @Expose
        private String media_url;

        protected LevelQuiz(Parcel in) {
            questionId = in.readString();
            levelId = in.readString();
            stageId = in.readString();
            questionHi = in.readString();
            questionEn = in.readString();
            coinPerQuestion = in.readString();
            type=in.readString();
            media_url=in.readString();
        }

        public static final Creator<LevelQuiz> CREATOR = new Creator<LevelQuiz>() {
            @Override
            public LevelQuiz createFromParcel(Parcel in) {
                return new LevelQuiz(in);
            }

            @Override
            public LevelQuiz[] newArray(int size) {
                return new LevelQuiz[size];
            }
        };

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public String getQuestionHi() {
            return questionHi;
        }

        public void setQuestionHi(String questionHi) {
            this.questionHi = questionHi;
        }

        public String getQuestionEn() {
            return questionEn;
        }

        public void setQuestionEn(String questionEn) {
            this.questionEn = questionEn;
        }

        public String getCoinPerQuestion() {
            return coinPerQuestion;
        }

        public void setCoinPerQuestion(String coinPerQuestion) {
            this.coinPerQuestion = coinPerQuestion;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMedia_url() {
            return media_url;
        }

        public void setMedia_url(String media_url) {
            this.media_url = media_url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(questionId);
            parcel.writeString(levelId);
            parcel.writeString(stageId);
            parcel.writeString(questionHi);
            parcel.writeString(questionEn);
            parcel.writeString(coinPerQuestion);
            parcel.writeString(type);
            parcel.writeString(media_url);
        }
    }
}
