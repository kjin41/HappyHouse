package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.CommentDto;

@Mapper
public interface CommentMapper {

	List<CommentDto> list(int qnano);
	int create(CommentDto commentDto);
	int modify(CommentDto commentDto);
	int delete(int no);
	int plus(int qnano);
	int minus(int qnano);
}
