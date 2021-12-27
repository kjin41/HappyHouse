package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.CommentDto;

public interface CommentService {

	List<CommentDto> list(int qnano) throws Exception;
	boolean create(CommentDto commentDto) throws Exception;
	boolean modify(CommentDto commentDto) throws Exception;
	boolean delete(int no) throws Exception;
}
