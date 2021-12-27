package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.EnvDto;

public interface EnvService {
	List<EnvDto> getEnvs(String address) throws Exception;
}
