package com.ssafy.happyhouse.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.net.HttpHeaders;
import com.ssafy.happyhouse.model.QnaDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@GetMapping("/idcheck/{userId}")
	public ResponseEntity<Integer> idCheck(@PathVariable("userId") String checkId) throws Exception {
		int idCount = userService.idCheck(checkId);
//		JSONObject json = new JSONObject();
//		json.put("idcount", idCount);
		return new ResponseEntity<Integer>(idCount, HttpStatus.OK);
	}
//
//	@PostMapping("/register")
//	public String register(UserDto userDto, Model model) throws Exception {
//		logger.debug("userDto info : {}", userDto);
//		userService.registerUser(userDto);
//		return "redirect:/";
//	}
	
	@PostMapping
	public ResponseEntity<String> register(@RequestBody UserDto userDto) throws Exception {
		logger.info("user Register - ??????");
		userService.registerUser(userDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modify(@RequestBody UserDto userDto) throws Exception {
		logger.info("user modify - ??????");
		userService.updateUser(userDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> delete(@PathVariable("userId") String userId) throws Exception {
		logger.info("user delete - ??????");
		System.out.println(userId);
		userService.deleteUser(userId);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}


//	@PostMapping("/login")
//	public String login(@RequestParam Map<String, String> map, Model model, HttpSession session,
//			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
//		logger.debug("map : {}", map.get("id"));
//		UserDto userDto = userService.login(map);
//		if (userDto != null) {
//			session.setAttribute("userinfo", userDto);
//
//			Cookie cookie = new Cookie("user_id", map.get("userId"));
//			cookie.setPath("/");
//			if ("saveok".equals(map.get("idsave"))) {
//				cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
//			} else {
//				cookie.setMaxAge(0);
//			}
//			response.addCookie(cookie);
//			return "redirect:/";
////			return new ResponseEntity(HttpStatus.OK);
//		} else {
////			return new ResponseEntity(HttpStatus.NO_CONTENT);
//			redirectAttributes.addFlashAttribute("msg", "????????? ?????? ???????????? ?????? ??? ?????? ??????????????????!");
//			redirectAttributes.addFlashAttribute("loginmsg", "????????? ?????? ???????????? ?????? ??? ?????? ??????????????????!");
//			return "redirect:/";
//		}
//	}
	
	@ApiOperation(value = "?????????", notes = "Access-token??? ????????? ?????? ???????????? ????????????.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "????????? ??? ????????? ????????????(?????????, ????????????).", required = true) UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto loginUser = userService.login(userDto);
			System.out.println(userDto);
			System.out.println(loginUser);
			if (loginUser != null) {
				String token = jwtService.create("userId", loginUser.getUserId(), "access-token");// key, data, subject
				logger.debug("????????? ???????????? : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("????????? ?????? : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	
	@ApiOperation(value = "????????????", notes = "?????? ????????? ?????? Token??? ????????????.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "????????? ????????? ?????????.", required = true) String userId,
			HttpServletRequest request) {
		logger.info("getInfo");
		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("?????? ????????? ??????!!!");
			try {
//				????????? ????????? ??????.
				UserDto userDto = userService.userInfo(userId);
				System.out.println("????????? ????????? ??????: "+userDto);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("???????????? ?????? : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("?????? ????????? ??????!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable("userId") String userId) throws Exception {
		logger.info("userInfo");
		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		UserDto userDto=userService.getUser(userId);
		System.out.println(userDto);
		if (userDto!=null) {
			resultMap.put("userInfo", userDto);
			resultMap.put("message", SUCCESS);
		} else {
			resultMap.put("message", FAIL);
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	//ResponseEntity<Map<String, Object>>
	@GetMapping("/kakaologin")
	public ResponseEntity<String> kakaoLogin(@RequestParam("code") String authorization_code) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String reqURL = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        //    POST ????????? ?????? ???????????? false??? setDoOutput??? true???
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        
        //    POST ????????? ????????? ???????????? ???????????? ???????????? ?????? ??????
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        String result = "";
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=8105a536e7d755140d8ffa78260da754");
        sb.append("&redirect_uri=http://localhost:8080/kakaologin");
        sb.append("&code=" + authorization_code);
        bw.write(sb.toString());
        bw.flush();
        
        //    ?????? ????????? 200????????? ??????
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
        if (responseCode==200) {
            //    ????????? ?????? ?????? JSON????????? Response ????????? ????????????
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        br.close();
        } else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        bw.close();
	    System.out.println("code : " + authorization_code);
//	    String token=userService.kakaoLogin(authorization_code);
	    System.out.println("token: "+result);
	    return new ResponseEntity<String>(result, status);
	}
	
	@GetMapping("/kakaotoken")
	public ResponseEntity<String> getKakaoUser(@RequestParam("token") String access_Token) throws Exception {
		HttpStatus status = HttpStatus.OK;
        String line = "";
        String result = "";
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		????????? ????????? Header??? ????????? ??????
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

//		?????? ????????? 200????????? ??????
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
        if (responseCode==200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
        } else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        System.out.println("response body : " + result);

	    return new ResponseEntity<String>(result, status);
	}
	
	
	
	    
//	https://kauth.kakao.com/oauth/authorize?client_id=8105a536e7d755140d8ffa78260da754&redirect_uri=http://localhost/kakaologin&response_type=code
		
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
	
//	@GetMapping("/list")
//	public String list() {
//		return "user/list";
//	}
//	
//	@GetMapping("/mypage")
//	public String mypage() throws Exception {
//		return "user/mypage";
//	}
//	
//	@GetMapping("/modify")
//	public String modify() throws Exception{
//		return "user/mypage_modify";
//	}
//	
//	@PostMapping("/modify")
//	public String modify(UserDto userDto, Model model, HttpSession session) throws Exception{
//		logger.debug("userDto info : {}", userDto);
//		userService.updateUser(userDto);
//		session.setAttribute("userinfo", userService.getUser(userDto.getUserId()));
//		return "redirect:/";
//	}
//	
//	@GetMapping("/delete")
//	public String delete(@RequestParam("userId") String userId, HttpSession session) throws Exception{
//		userService.deleteUser(userId);
//		session.invalidate();
//		return "redirect:/";
//	}
//	
//	@GetMapping("/findpass")
//	public String findPass(@RequestParam Map<String, String> map, RedirectAttributes redirectAttributes) throws Exception{
//		UserDto userDto=userService.getUser(map.get("userId"));
//		System.out.println(userDto);
//		if (userDto!=null) {
//			redirectAttributes.addFlashAttribute("msg", "?????? ???????????? ??????????????? "+userDto.getPassword()+" ?????????.");
//		} else {
//			redirectAttributes.addFlashAttribute("msg", "???????????? ?????? ??????????????????.");
//		}
//		return "redirect:/";
//	}
	
	
	
	
}
