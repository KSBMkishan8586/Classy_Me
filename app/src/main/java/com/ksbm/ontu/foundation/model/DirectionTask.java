package com.ksbm.ontu.foundation.model;

public class DirectionTask {
    String direction_ques; String direction_ans; String direction_type;

    public DirectionTask(String direction_ques, String direction_ans, String direction_type) {
        this.direction_ques = direction_ques;
        this.direction_ans = direction_ans;
        this.direction_type = direction_type;
    }

    public String getDirection_ques() {
        return direction_ques;
    }

    public void setDirection_ques(String direction_ques) {
        this.direction_ques = direction_ques;
    }

    public String getDirection_ans() {
        return direction_ans;
    }

    public void setDirection_ans(String direction_ans) {
        this.direction_ans = direction_ans;
    }

    public String getDirection_type() {
        return direction_type;
    }

    public void setDirection_type(String direction_type) {
        this.direction_type = direction_type;
    }
}
