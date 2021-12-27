package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.EnvDto;
import com.ssafy.happyhouse.model.service.EnvService;

@RestController
@RequestMapping("/env")
public class EnvController {

	@Autowired
	private EnvService envService;
	
	@GetMapping("/list")
	public ResponseEntity<List<EnvDto>> getEnvs(@RequestParam("addr") String address) throws Exception{
		return new ResponseEntity<List<EnvDto>>(envService.getEnvs(address), HttpStatus.OK);
	}
	
}
