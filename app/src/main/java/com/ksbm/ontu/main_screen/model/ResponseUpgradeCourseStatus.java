package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.SerializedName;

public class ResponseUpgradeCourseStatus{

	@SerializedName("plan")
	private boolean plan;

	@SerializedName("status")
	private boolean status;

	@SerializedName("plan_upgrade_amount")
	private String planUpgradeAmount;

	public boolean isPlan(){
		return plan;
	}

	public boolean isStatus(){
		return status;
	}

	public String getPlanUpgradeAmount(){
		return planUpgradeAmount;
	}
}