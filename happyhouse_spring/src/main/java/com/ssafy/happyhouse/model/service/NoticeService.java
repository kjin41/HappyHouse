package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.NoticeParameterDto;
import com.ssafy.util.PageNavigation;

public interface NoticeService {
	void registerNotice(NoticeDto noticeDto) throws Exception;
	List<NoticeDto> listNotice() throws Exception;
	NoticeDto getNotice(int no) throws Exception;
	void updateNotice(NoticeDto noticeDto) throws Exception;
	void deleteNotice(int no, String path) throws Exception;
	PageNavigation makePageNavigation(NoticeParameterDto noticeParameterDto) throws Exception;
	void updateHit(int no) throws Exception;

}
