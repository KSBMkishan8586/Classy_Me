package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.SerializedName;

public class ResponseUpgradeCourse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private boolean status;

	public String getMsg(){
		return msg;
	}

	public boolean isStatus(){
		return status;
	}
}