package com.ksbm.ontu.progression_report.model;

import com.google.gson.annotations.SerializedName;

public class ReportResponseModel {

	@SerializedName("Nationalrank")
	private String nationalrank;

	@SerializedName("coin_balance")
	private String coinBalance;

	@SerializedName("Schoolrank")
	private String schoolrank;

	@SerializedName("co_currilier")
	private int coCurrilier;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("internal_exam")
	private int internalExam;

	@SerializedName("userid")
	private String userid;

	public String getNationalrank(){
		return nationalrank;
	}

	public String getCoinBalance(){
		return coinBalance;
	}

	public String getSchoolrank(){
		return schoolrank;
	}

	public int getCoCurrilier(){
		return coCurrilier;
	}

	public String getFullname(){
		return fullname;
	}

	public int getInternalExam(){
		return internalExam;
	}

	public String getUserid(){
		return userid;
	}
}