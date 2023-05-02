package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

public class ProgressionReportResponse{

	@SerializedName("response")
	private ReportResponseModel response;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public ReportResponseModel getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}