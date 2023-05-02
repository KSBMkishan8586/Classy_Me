package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
	private boolean status;
	@SerializedName("personality_development")
	private String personalityDevelopment;
	@SerializedName("practise_speaking")
	private String practiseSpeaking;
	@SerializedName("score_calculater")
	private ScoreCalculaterDTO scoreCalculater;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setPersonalityDevelopment(String personalityDevelopment){
		this.personalityDevelopment = personalityDevelopment;
	}

	public String getPersonalityDevelopment(){
		return personalityDevelopment;
	}

	public void setPractiseSpeaking(String practiseSpeaking){
		this.practiseSpeaking = practiseSpeaking;
	}

	public String getPractiseSpeaking(){
		return practiseSpeaking;
	}

	public void setScoreCalculater(ScoreCalculaterDTO scoreCalculater){
		this.scoreCalculater = scoreCalculater;
	}

	public ScoreCalculaterDTO getScoreCalculater(){
		return scoreCalculater;
	}

}