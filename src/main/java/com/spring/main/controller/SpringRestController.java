package com.spring.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.DataBean;
import com.spring.main.service.CommonRestService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Spring Rest Controller", description = "Spring Boot Rest API")
@RequestMapping(value = "/spring/rest")
public class SpringRestController {
	
	@Autowired
	CommonRestService commonRestService;

	/**
	 * Returns Data From Database
	 * @return
	 */
	@GetMapping(value = "/getDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonResponseBean<List<DataBean>>> getData() {
		return ResponseEntity.ok(new CommonResponseBean<>(commonRestService.getDataList()));
	}

}
