package com.ksbm.ontu.personality_dev.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalityMCQTest {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<PersonalityMCQResponse> response;

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

    public List<PersonalityMCQResponse>  getResponse() {
        return response;
    }

    public void setResponse(List<PersonalityMCQResponse>  response) {
        this.response = response;
    }

    public static class PersonalityMCQResponse implements Parcelable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("quiz_question")
        @Expose
        private String quizQuestion;

        @SerializedName("question_image")
        @Expose
        private String question_image;

        @SerializedName("option_1")
        @Expose
        private String option1;
        @SerializedName("option_2")
        @Expose
        private String option2;
        @SerializedName("option_3")
        @Expose
        private String option3;
        @SerializedName("option_4")
        @Expose
        private String option4;
        @SerializedName("option_5")
        @Expose
        private String option5;
        @SerializedName("right_answer")
        @Expose
        private String rightAnswer;

        protected PersonalityMCQResponse(Parcel in) {
            id = in.readString();
            quizQuestion = in.readString();
            question_image =in.readString();
            option1 = in.readString();
            option2 = in.readString();
            option3 = in.readString();
            option4 = in.readString();
            option5 = in.readString();
            rightAnswer = in.readString();
        }

        public static final Creator<PersonalityMCQResponse> CREATOR = new Creator<PersonalityMCQResponse>() {
            @Override
            public PersonalityMCQResponse createFromParcel(Parcel in) {
                return new PersonalityMCQResponse(in);
            }

            @Override
            public PersonalityMCQResponse[] newArray(int size) {
                return new PersonalityMCQResponse[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuizQuestion() {
            return quizQuestion;
        }

        public void setQuizQuestion(String quizQuestion) {
            this.quizQuestion = quizQuestion;
        }

        public String getQuestion_image() {
            return question_image;
        }

        public void setQuestion_image(String question_image) {
            this.question_image = question_image;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }

        public String getOption5() {
            return option5;
        }

        public void setOption5(String option5) {
            this.option5 = option5;
        }

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(quizQuestion);
            parcel.writeString(question_image);
            parcel.writeString(option1);
            parcel.writeString(option2);
            parcel.writeString(option3);
            parcel.writeString(option4);
            parcel.writeString(option5);
            parcel.writeString(rightAnswer);
        }
    }

}
