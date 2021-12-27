package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.FileInfoDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.NoticeParameterDto;

public interface NoticeMapper {
	
	void registerNotice(NoticeDto noticeDto) throws Exception;
	List<NoticeDto> listNotice() throws Exception;
	NoticeDto getNotice(int no) throws Exception;
	void updateNotice(NoticeDto noticeDto) throws Exception;
	void deleteNotice(int no) throws Exception;
	void registerFile(NoticeDto noticeDto) throws Exception;
	int getTotalCount(NoticeParameterDto noticeParameterDto) throws Exception;
	List<FileInfoDto> fileInfoList(int no) throws Exception;
	void deleteFile(int no) throws Exception;
	void updateHit(int no) throws Exception;

}
