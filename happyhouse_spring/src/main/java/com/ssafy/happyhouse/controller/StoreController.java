package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.StoreDto;
import com.ssafy.happyhouse.model.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@GetMapping("/list")
	private ResponseEntity<List<StoreDto>> list(@RequestParam("addr") String address) throws Exception{
		return new ResponseEntity<List<StoreDto>>(storeService.getStores(address), HttpStatus.OK);
	}
	
}
