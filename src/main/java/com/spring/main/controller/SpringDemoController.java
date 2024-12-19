package com.spring.main.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.SpringBootProjectApplication;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Spring Demo Controller" , description = "Swagger Implemented & Tested Demo API")
@RestController
@RequestMapping(value = "/spring/demo")
public class SpringDemoController {

	private static final Logger logger = LogManager.getLogger(SpringBootProjectApplication.class);
	
	@GetMapping(value = "/myfirstapi" , produces = "application/json")
	public String myfirstApi() {
		logger.info("Method SpringDemoController.myfirstApi() Started ..");
		return "This Is My First API!";
	} 
}
