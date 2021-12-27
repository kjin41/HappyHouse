package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.QnaDto;
import com.ssafy.happyhouse.model.QnaParameterDto;
import com.ssafy.util.PageNavigation;

public interface QnaService {
	public boolean writeArticle(QnaDto qnaDto) throws Exception;
	public List<QnaDto> listArticle(String id) throws Exception;
	public PageNavigation makePageNavigation(QnaParameterDto qnaParameterDto) throws Exception;
	
	public QnaDto getArticle(int no) throws Exception;
//	public void updateHit(int no) throws Exception;
	public boolean modifyArticle(QnaDto qnaDto) throws Exception;
	public boolean deleteArticle(int no) throws Exception;

}
