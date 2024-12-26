package com.spring.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Spring Demo Controller" , description = "Swagger Implemented & Tested Demo API")
@RestController
@RequestMapping(value = "/spring/demo")
public class SpringDemoController {

	@GetMapping(value = "/myfirstapi" , produces = "application/json")
	public String myfirstApi() {
		return "This Is My First API!";
	} 
}
