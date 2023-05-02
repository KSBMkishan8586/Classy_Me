package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

public class InternalResponse {

	@SerializedName("score_calculator")
	private ScoreInternalCalculator scoreCalculator;

	@SerializedName("have_fun_total")
	private String haveFunTotal;

	@SerializedName("kids_learning_total_obtain")
	private String kidsLearningTotalObtain;

	@SerializedName("have_fun_total_obtain")
	private String haveFunTotalObtain;

	@SerializedName("kids_learning_total")
	private String kidsLearningTotal;

	public ScoreInternalCalculator getScoreCalculator(){
		return scoreCalculator;
	}

	public String getHaveFunTotal(){
		return haveFunTotal;
	}

	public String getKidsLearningTotalObtain(){
		return kidsLearningTotalObtain;
	}

	public String getHaveFunTotalObtain(){
		return haveFunTotalObtain;
	}

	public String getKidsLearningTotal(){
		return kidsLearningTotal;
	}
}