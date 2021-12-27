package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.QnaDto;
import com.ssafy.happyhouse.model.QnaParameterDto;

@Mapper
public interface QnaMapper {

	public int writeArticle(QnaDto qnaDto) throws SQLException;
	public List<QnaDto> listArticle(String userId) throws SQLException;
	public int getTotalCount(QnaParameterDto qnaParameterDto) throws SQLException;
	public QnaDto getArticle(int articleno) throws SQLException;
	public void updateHit(int articleno) throws SQLException;
	public int modifyArticle(QnaDto qnaDto) throws SQLException;
	public void deleteComment(int articleno) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
}
