package com.ssafy.happyhouse.model;

import java.util.List;

public class NoticeDto {
	private int no;
	private String userId;
	private String date;
	private String title;
	private String content;
	private int hit;
	private List<FileInfoDto> fileInfoDtos;

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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public List<FileInfoDto> getFileInfoDtos() {
		return fileInfoDtos;
	}

	public void setFileInfoDtos(List<FileInfoDto> fileInfoDtos) {
		this.fileInfoDtos = fileInfoDtos;
	}

	@Override
	public String toString() {
		return "NoticeDto [no=" + no + ", userId=" + userId + ", date=" + date + ", title=" + title + ", content="
				+ content + ", hit=" + hit + ", fileInfoDtos=" + fileInfoDtos + "]";
	}

}
