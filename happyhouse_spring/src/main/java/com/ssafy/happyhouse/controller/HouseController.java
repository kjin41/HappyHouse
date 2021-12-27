package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.AptInfoDto;
import com.ssafy.happyhouse.model.BikeDto;
import com.ssafy.happyhouse.model.BusDto;
import com.ssafy.happyhouse.model.ClinicDto;
import com.ssafy.happyhouse.model.HouseDealInfoDto;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.JjimDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.StarbucksDto;
import com.ssafy.happyhouse.model.TrainDto;
import com.ssafy.happyhouse.model.service.HouseService;

@RestController
@RequestMapping("/map")
public class HouseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private HouseService houseService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception{
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception{
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception{
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception{
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@GetMapping("/jjim/{userId}")
	public ResponseEntity<List<JjimDto>> listJjim(@PathVariable("userId") String userId) throws Exception{
		System.out.println(userId);
		List<JjimDto> list = houseService.listJjim(userId);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<JjimDto>>(list, HttpStatus.OK);	
		}
		else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/jjim")
	public ResponseEntity<List<JjimDto>> insertJjim(@RequestParam Map<String, String> map) throws Exception{
		
		List<JjimDto> list = houseService.insertJjim(map);
		if(list != null) {			
			return new ResponseEntity<List<JjimDto>>(list, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<JjimDto>>(list, HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("/jjim")
	public ResponseEntity<List<JjimDto>> deleteJjim(@RequestBody Map<String, String> map) throws Exception{
		System.out.println("jjim : " + map.get("userId"));
		System.out.println("jjim : " +  map.get("dongCode"));
		JjimDto jjimDto = new JjimDto();
		jjimDto.setUserId(map.get("userId"));
		jjimDto.setDongCode(map.get("dongCode"));
		List<JjimDto> list = houseService.deleteJjim(jjimDto);
				
		return new ResponseEntity<List<JjimDto>>(list, HttpStatus.OK);

	}
	
	   @GetMapping("/price")
	   public ResponseEntity<List<HouseDealInfoDto>> getDealList(@RequestParam Map<String, String> map) throws Exception{
		  Map<String, Double> parammap = new HashMap<String, Double>();
		  parammap.put("lat1", Double.parseDouble(map.get("lat1")));
		  parammap.put("lng1", Double.parseDouble(map.get("lng1")));
		  parammap.put("lat2", Double.parseDouble(map.get("lat2")));
		  parammap.put("lng2", Double.parseDouble(map.get("lng2")));
		  parammap.put("stPrice", Double.parseDouble(map.get("stPrice")));
		  parammap.put("endPrice", Double.parseDouble(map.get("endPrice")));
		  List<HouseDealInfoDto> list = houseService.getDealList(parammap);
		  System.out.println(list.size());
		  System.out.println(parammap.get("lat1"));
		  System.out.println(parammap.get("lng1"));
		  System.out.println(parammap.get("lat2"));
		  System.out.println(parammap.get("lng2"));
	      return new ResponseEntity<List<HouseDealInfoDto>>(list, HttpStatus.OK);
//	      System.out.println("map : " + map.get("LAWD_CD"));
//	      List<AptInfoDto> jArray = houseService.getHouseList(map);
//	      if(jArray == null || jArray.size() == 0) {
////	         return new ResponseEntity<String>("아파트가 없습니다!!", HttpStatus.BAD_REQUEST);
//	         return new ResponseEntity<List<AptInfoDto>>(jArray, HttpStatus.OK);
//	      }
//	      return new ResponseEntity<List<AptInfoDto>>(jArray, HttpStatus.OK);

	   }
	   @GetMapping("/train")
	   public ResponseEntity<List<TrainDto>> getTrainList(@RequestParam Map<String, String> map) throws Exception{
		   List<TrainDto> tList = houseService.getTrainList(map);
		   return new ResponseEntity<List<TrainDto>>(tList, HttpStatus.OK);		   
	   }
	
	   @GetMapping("/clinic")
	   public ResponseEntity<List<ClinicDto>> getClinicList(@RequestParam Map<String, String> map) throws Exception{
		   List<ClinicDto> cList = houseService.getClinicList(map);
		   return new ResponseEntity<List<ClinicDto>>(cList, HttpStatus.OK);		   
	   }
	   
	   @GetMapping("/bus")
	   public ResponseEntity<List<BusDto>> getBusList(@RequestParam Map<String, String> map) throws Exception{
		   List<BusDto> bList = houseService.getBusList(map);
		   return new ResponseEntity<List<BusDto>>(bList, HttpStatus.OK);		   
	   }
	   @GetMapping("/starbucks")
	   public ResponseEntity<List<StarbucksDto>> getStarbucksList(@RequestParam Map<String, String> map) throws Exception{
		   List<StarbucksDto> sList = houseService.getStarbucksList(map);
		   return new ResponseEntity<List<StarbucksDto>>(sList, HttpStatus.OK);		   
	   }
	   @GetMapping("/bike")
	   public ResponseEntity<List<BikeDto>> getBikeList(@RequestParam Map<String, String> map) throws Exception{
		   List<BikeDto> bList = houseService.getBikeList(map);
		   return new ResponseEntity<List<BikeDto>>(bList, HttpStatus.OK);		   
	   }
	   
	
	// 아파트목록
	   @GetMapping("/housePd")
	   public ResponseEntity<List<AptInfoDto>> getHouseList(@RequestParam Map<String, String> map) throws Exception{
	      return new ResponseEntity<List<AptInfoDto>>(houseService.getHouseList(map), HttpStatus.OK);
//	      System.out.println("map : " + map.get("LAWD_CD"));
//	      List<AptInfoDto> jArray = houseService.getHouseList(map);
//	      if(jArray == null || jArray.size() == 0) {
////	         return new ResponseEntity<String>("아파트가 없습니다!!", HttpStatus.BAD_REQUEST);
//	         return new ResponseEntity<List<AptInfoDto>>(jArray, HttpStatus.OK);
//	      }
//	      return new ResponseEntity<List<AptInfoDto>>(jArray, HttpStatus.OK);

	   }
	   
	   
	   // 아파트 거래내역
	   @PostMapping("/housePd")
	   public ResponseEntity<String> getHouseDeal(@RequestBody Map<String, String> map) throws Exception{
	      JSONArray jArray = houseService.getHouseDeal(map);
	      if(jArray == null || jArray.length() == 0) {
//	         return new ResponseEntity<String>("거래 내역이 없습니다!!", HttpStatus.BAD_REQUEST);
	         return new ResponseEntity<String>(jArray.toString(), HttpStatus.OK);
	      }
	      return new ResponseEntity<String>(jArray.toString(), HttpStatus.OK);

	   }
	
	

}