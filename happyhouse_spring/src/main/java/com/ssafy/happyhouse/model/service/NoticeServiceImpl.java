package com.ssafy.happyhouse.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.FileInfoDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.NoticeParameterDto;
import com.ssafy.happyhouse.model.mapper.NoticeMapper;
import com.ssafy.util.PageNavigation;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	@Transactional
	public void registerNotice(NoticeDto noticeDto) throws Exception {
		sqlSession.getMapper(NoticeMapper.class).registerNotice(noticeDto);
		List<FileInfoDto> fileInfoDtos = noticeDto.getFileInfoDtos();
		if (fileInfoDtos!=null && !fileInfoDtos.isEmpty()) {
			sqlSession.getMapper(NoticeMapper.class).registerFile(noticeDto);
		}
		
	}

	@Override
	public NoticeDto getNotice(int no) throws Exception {
		NoticeDto noticeDto = sqlSession.getMapper(NoticeMapper.class).getNotice(no);
		noticeDto.setFileInfoDtos(sqlSession.getMapper(NoticeMapper.class).fileInfoList(no));
		return noticeDto; 
	}

	@Override
	public void updateNotice(NoticeDto noticeDto) throws Exception {
		sqlSession.getMapper(NoticeMapper.class).updateNotice(noticeDto);
	}

	@Override
	public void deleteNotice(int no, String path) throws Exception {
		NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
		List<FileInfoDto> fileList = noticeMapper.fileInfoList(no);
		noticeMapper.deleteFile(no);
		noticeMapper.deleteNotice(no);
		for(FileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}

	@Override
	public List<NoticeDto> listNotice() throws Exception {
//		int start = noticeParameterDto.getPg() == 0 ? 0 : (noticeParameterDto.getPg() - 1) * noticeParameterDto.getSpp();
//		noticeParameterDto.setStart(start);
		return sqlSession.getMapper(NoticeMapper.class).listNotice();
	}

	@Override
	public PageNavigation makePageNavigation(NoticeParameterDto noticeParameterDto) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		int naviSize = 10;
		pageNavigation.setCurrentPage(noticeParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(NoticeMapper.class).getTotalCount(noticeParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / noticeParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = noticeParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < noticeParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public void updateHit(int no) throws Exception {
		sqlSession.getMapper(NoticeMapper.class).updateHit(no);
	}
}
