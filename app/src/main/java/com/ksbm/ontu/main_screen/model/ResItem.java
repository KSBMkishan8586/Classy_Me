package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.SerializedName;

public class ResItem{

	@SerializedName("score")
	private String score;

	@SerializedName("total_coins")
	private String totalCoins;

	@SerializedName("earned_coins")
	private String earnedCoins;

	@SerializedName("particular")
	private String particular;

	public String getScore(){
		return score;
	}

	public String getTotalCoins(){
		return totalCoins;
	}

	public String getEarnedCoins(){
		return earnedCoins;
	}

	public String getParticular(){
		return particular;
	}
}