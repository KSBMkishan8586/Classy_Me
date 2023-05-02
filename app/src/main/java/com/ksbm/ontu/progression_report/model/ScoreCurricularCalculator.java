package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

public class ScoreCurricularCalculator {

	@SerializedName("practice_offline_obtain")
	private int practiceOfflineTotal;

	@SerializedName("total_obtain")
	private int totalObtain;

	@SerializedName("practice_offline_weightage")
	private int practiceOfflineWeightage;

	@SerializedName("personality_development_weightage")
	private int personalityDevelopmentWeightage;

	@SerializedName("personality_development_obtain")
	private int personalityDevelopmentObtain;

	public int getPracticeOfflineObtain(){
		return practiceOfflineTotal;
	}

	public int getTotalObtain(){
		return totalObtain;
	}

	public int getPracticeOfflineWeightage(){
		return practiceOfflineWeightage;
	}

	public int getPersonalityDevelopmentWeightage(){
		return personalityDevelopmentWeightage;
	}

	public int getPersonalityDevelopmentObtain(){
		return personalityDevelopmentObtain;
	}
}