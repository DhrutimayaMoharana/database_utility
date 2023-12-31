package com.watsoo.database_utility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.database_utility.service.ConfigurationService;

@RestController
@RequestMapping("api")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;
	
	@GetMapping("/fetch/all/information")
	public ResponseEntity<?> findAll(){
		configurationService.fetchForFirstTimeFromDB();
		return new ResponseEntity<>("Successfully Fetched", HttpStatus.OK);
	}
}
