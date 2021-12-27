package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.*;

public interface HouseService {
	 
	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseDealDto> getHouseDealInApt(int aptCode) throws Exception;
	List<JjimDto> listJjim(String userId);
	List<AptInfoDto> getHouseList(Map<String, String> map) throws Exception;
    JSONArray getHouseDeal(Map<String, String> map) throws Exception;
    List<JjimDto> insertJjim(Map<String, String> map) throws Exception;
	List<JjimDto> deleteJjim(JjimDto jjimDto) throws Exception;
	List<HouseDealInfoDto> getDealList(Map<String, Double> map) throws Exception;
	List<TrainDto> getTrainList(Map<String, String> map) throws Exception;
	List<ClinicDto> getClinicList(Map<String, String> map) throws Exception;
	List<BusDto> getBusList(Map<String, String> map) throws Exception;
	List<StarbucksDto> getStarbucksList(Map<String, String> map) throws Exception;
	List<BikeDto> getBikeList(Map<String, String> map) throws Exception;
}
