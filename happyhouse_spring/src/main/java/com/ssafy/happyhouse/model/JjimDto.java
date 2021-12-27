package com.ssafy.happyhouse.model;

public class JjimDto {

	private String userId;
	private int dongNo;
	private String sidoName;
	private String gugunName;
	private String dongName;
	private String dongCode;
	
	public String getDongName() {
		return dongName;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getDongNo() {
		return dongNo;
	}

	public void setDongNo(int dongNo) {
		this.dongNo = dongNo;
	}
}
