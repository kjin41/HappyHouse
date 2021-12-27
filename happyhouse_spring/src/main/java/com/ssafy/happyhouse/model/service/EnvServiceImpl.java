package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.EnvDto;
import com.ssafy.happyhouse.model.mapper.EnvMapper;

@Service
public class EnvServiceImpl implements EnvService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<EnvDto> getEnvs(String address) throws Exception {
		return sqlSession.getMapper(EnvMapper.class).getEnvs(address);
	}

}
