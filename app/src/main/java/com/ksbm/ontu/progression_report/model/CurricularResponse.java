package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

public class CurricularResponse {

	@SerializedName("personality_development_total_obtain")
	private String personalityDevelopmentTotalObtain;

	@SerializedName("score_calculator")
	private ScoreCurricularCalculator scoreCalculator;

	@SerializedName("practice_offline_total")
	private String practiceOfflineTotal;

	@SerializedName("personality_development_total")
	private String personalityDevelopmentTotal;

	@SerializedName("practice_offline_total_obtain")
	private String practiceOfflineTotalObtain;

	public String getPersonalityDevelopmentTotalObtain(){
		return personalityDevelopmentTotalObtain;
	}

	public ScoreCurricularCalculator getScoreCalculator(){
		return scoreCalculator;
	}

	public String getPracticeOfflineTotal(){
		return practiceOfflineTotal;
	}

	public String getPersonalityDevelopmentTotal(){
		return personalityDevelopmentTotalObtain;
	}

	public String getPracticeOfflineTotalObtain(){
		return practiceOfflineTotalObtain;
	}
}