package com.ssafy.happyhouse.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.UserDto;

@Mapper
public interface UserMapper {
	int idCheck(String id) throws Exception;
	void registerUser(UserDto UserDto) throws Exception;
	UserDto login(UserDto userDto) throws Exception;
	
	UserDto getUser(String id) throws Exception;
	void updateUser(UserDto userDto) throws Exception;
	void deleteUser(String id) throws Exception;
	UserDto userInfo(String id) throws Exception;
	
}
