package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.*;

public interface HouseMapper {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseDealDto> getHouseDealInApt(int aptCode) throws Exception;
	List<JjimDto> listJjim(String userId);
	int insertJjim(Map<String, String> map) throws Exception;
	int deleteJjim(JjimDto jjimDto) throws Exception;
	List<HouseDealInfoDto> getDealList(Map<String, Double> map) throws Exception;
	List<TrainDto> getTrainList(Map<String, String> map) throws Exception;
	List<ClinicDto> getClinicList(Map<String, String> map) throws Exception;
	List<BusDto> getBusList(Map<String, String> map) throws Exception;
	List<StarbucksDto> getStarbucksList(Map<String, String> map) throws Exception;
	List<BikeDto> getBikeList(Map<String, String> map) throws Exception;
}
