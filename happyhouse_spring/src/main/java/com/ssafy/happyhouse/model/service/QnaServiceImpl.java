package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.QnaDto;
import com.ssafy.happyhouse.model.QnaParameterDto;
import com.ssafy.happyhouse.model.mapper.QnaMapper;
import com.ssafy.util.PageNavigation;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean writeArticle(QnaDto qnaDto) throws Exception {
		if(qnaDto.getTitle() == null || qnaDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(QnaMapper.class).writeArticle(qnaDto) == 1;
	}

	@Override
	public List<QnaDto> listArticle(String userId) throws Exception {
//		int start = qnaParameterDto.getPg() == 0 ? 0 : (qnaParameterDto.getPg() - 1) * qnaParameterDto.getSpp();
//		qnaParameterDto.setStart(start);
//		System.out.println(start+" "+qnaParameterDto);
		return sqlSession.getMapper(QnaMapper.class).listArticle(userId);
	}

	@Override
	public PageNavigation makePageNavigation(QnaParameterDto qnaParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(qnaParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(QnaMapper.class).getTotalCount(qnaParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / qnaParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = qnaParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < qnaParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public QnaDto getArticle(int no) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).getArticle(no);
	}

	@Override
	@Transactional
	public boolean modifyArticle(QnaDto qnaDto) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).modifyArticle(qnaDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int no) throws Exception {
		sqlSession.getMapper(QnaMapper.class).deleteComment(no);
		return sqlSession.getMapper(QnaMapper.class).deleteArticle(no) == 1;
	}

	
//	@Override
//	public void updateHit(int no) throws Exception {
//		sqlSession.getMapper(QnaMapper.class).updateHit(no);
//	}
}
