package com.ksbm.ontu.main_screen.model;

import com.google.gson.annotations.SerializedName;
import com.ksbm.ontu.progression_report.model.Re;

import java.util.List;

public class ResponseDetailedReport{

	@SerializedName("res")
	private List<Re> res;

	@SerializedName("status")
	private boolean status;

	public List<Re> getRes(){
		return res;
	}

	public boolean isStatus(){
		return status;
	}
}