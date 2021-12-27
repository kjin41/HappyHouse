package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "QnaDto : QnA정보", description = "QnA의 상세 정보를 나타낸다.")
public class QnaDto {
	private int no;
	private String userId;
	private String date;
	private String title;
	private String content;
	private int ansCount;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAnsCount() {
		return ansCount;
	}

	public void setAnsCount(int ansCount) {
		this.ansCount = ansCount;
	}

	@Override
	public String toString() {
		return "QnaDto [no=" + no + ", userId=" + userId + ", date=" + date + ", title=" + title + ", content="
				+ content + ", ansCount=" + ansCount + "]";
	}


}
