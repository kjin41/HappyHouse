package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.happyhouse.model.UserDto;

public interface UserService {

	int idCheck(String id) throws Exception;
	void registerUser(UserDto userDto) throws Exception;	
	UserDto getUser(String id) throws Exception;
	void updateUser(UserDto userDto) throws Exception;
	void deleteUser(String id) throws Exception;
	UserDto login(UserDto userDto) throws Exception;
	UserDto userInfo(String id) throws Exception;
//	String kakaoLogin(String code) throws Exception;
//	String getKakaoUser(String token) throws Exception;
}
