package com.ssafy.happyhouse.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
//import com.ssafy.happyhouse.controller.IOException;
//import com.ssafy.happyhouse.controller.JsonElement;
//import com.ssafy.happyhouse.controller.JsonObject;
//import com.ssafy.happyhouse.controller.JsonParser;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if(userDto.getUserId() == null || userDto.getPassword() == null)
			return null;
		return sqlSession.getMapper(UserMapper.class).login(userDto);
	}

	@Override
	public UserDto userInfo(String id) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(id);
	}
	
	@Override
	public int idCheck(String id) throws Exception {
		return sqlSession.getMapper(UserMapper.class).idCheck(id);
	}

	@Override
	public void registerUser(UserDto userDto) throws Exception {
		sqlSession.getMapper(UserMapper.class).registerUser(userDto);
	}

//	@Override
//	public String kakaoLogin(String authorization_code) throws Exception {
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
//        URL url = new URL(reqURL);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        
//        //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        
//        //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//        StringBuilder sb = new StringBuilder();
//        sb.append("grant_type=authorization_code");
//        sb.append("&client_id=8105a536e7d755140d8ffa78260da754");
//        sb.append("&redirect_uri=http://localhost:8080/kakaologin");
//        sb.append("&code=" + authorization_code);
//        bw.write(sb.toString());
//        bw.flush();
//        
//        //    결과 코드가 200이라면 성공
//        int responseCode = conn.getResponseCode();
//        System.out.println("responseCode : " + responseCode);
// 
//            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line = "";
//        String result = "";
//        
//        while ((line = br.readLine()) != null) {
//            result += line;
//        }
//        System.out.println("response body : " + result);
//        
//        br.close();
//        bw.close();
//        
//        return result;
//	}
//
//	@Override
//	public String getKakaoUser(String access_Token) throws Exception{
//	        
//        String reqURL = "https://kapi.kakao.com/v2/user/me";
//        URL url = new URL(reqURL);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////		요청에 필요한 Header에 포함될 내용
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//
////		결과 코드가 200이라면 성공
//        int responseCode = conn.getResponseCode();
//        System.out.println("responseCode : " + responseCode);
//        
//        
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        
//        String line = "";
//        String result = "";
//        
//        while ((line = br.readLine()) != null) {
//            result += line;
//        }
//        System.out.println("response body : " + result);
//	        	
//	    return result;
//	}
	    
	@Override
	public UserDto getUser(String id) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getUser(id);
	}

	@Override
	public void updateUser(UserDto userDto) throws Exception {
		sqlSession.getMapper(UserMapper.class).updateUser(userDto);
	}

	@Override
	public void deleteUser(String id) throws Exception {
		sqlSession.getMapper(UserMapper.class).deleteUser(id);
	}

//	@Override
//	public HashMap<String, String> getKakaoUser(String token) throws Exception {
//		return null;
//	}

//	@Override
//	public HashMap<String, String> getKakaoUser(String token) throws Exception {
//		
//		return null;
//	}

}
