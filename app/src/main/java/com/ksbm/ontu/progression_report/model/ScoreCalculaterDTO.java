package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScoreCalculaterDTO implements Serializable {
	@SerializedName("personality_development")
	private String personalityDevelopment;
	@SerializedName("practise_speaking")
	private String practiseSpeaking;

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

}