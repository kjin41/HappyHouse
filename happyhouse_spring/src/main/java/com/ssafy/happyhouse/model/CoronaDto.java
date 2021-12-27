package com.ssafy.happyhouse.model;

public class CoronaDto {
	private String region;

	private int tdTotal;
	private int tdIn;
	private int tdOut;
	private int total;
	private int cure;
	private int dead;
	private int rate;
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}

	public int getTdTotal() {
		return tdTotal;
	}

	public void setTdTotal(int tdTotal) {
		this.tdTotal = tdTotal;
	}

	public int getTdIn() {
		return tdIn;
	}

	public void setTdIn(int tdIn) {
		this.tdIn = tdIn;
	}

	public int getTdOut() {
		return tdOut;
	}

	public void setTdOut(int tdOut) {
		this.tdOut = tdOut;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCure() {
		return cure;
	}

	public void setCure(int cure) {
		this.cure = cure;
	}

	public int getDead() {
		return dead;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
