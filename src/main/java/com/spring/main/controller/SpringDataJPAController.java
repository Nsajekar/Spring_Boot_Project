package com.spring.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@Tag(name = "Spring Rest Data JPA Controller", description = "Spring Boot Rest API Data JPA")
@RequestMapping(value = "/spring/datajpa")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SpringDataJPAController {
	
	

	
}
