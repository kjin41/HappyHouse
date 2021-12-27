package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.EnvDto;

@Mapper
public interface EnvMapper {

	List<EnvDto> getEnvs(String address) throws Exception;
}
