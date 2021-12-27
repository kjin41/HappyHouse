package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CommentDto : 도서 상세정보에 작성하는 도서평에 대한 글정보")
public class CommentDto {
	@ApiModelProperty(value = "글번호")
	private int no;
	@ApiModelProperty(value = "작성자아이디")
	private String userId;
	@ApiModelProperty(value = "답변")
	private String comment;
	@ApiModelProperty(value = "작성시각")
	private String date;
	@ApiModelProperty(value = "QnA 고유번호")
	private int qnano;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getQnano() {
		return qnano;
	}
	public void setQnano(int qnano) {
		this.qnano = qnano;
	}
	
	@Override
	public String toString() {
		return "CommentDto [no=" + no + ", userId=" + userId + ", comment=" + comment + ", date=" + date + ", qnano="
				+ qnano + "]";
	}
	
	
	
	
}
