package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.StoreDto;

public interface StoreMapper {
	List<StoreDto> getStores(String address) throws Exception;
}
