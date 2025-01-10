package com.spring.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.annotation.RateLimit;
import com.spring.main.model.entity.DataBean;
import com.spring.main.service.CommonRestService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RateLimit
@Tag(name = "Spring JDBC Controller", description = "Just For Testing Perpose")
@RestController
@RequestMapping(value = "/spring/jdbc")
public class SpringJDBCController {
	
	@Autowired
	CommonRestService commonRestService;

	@GetMapping(value = "/getdata", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DataBean> myfirstApi() {
		return commonRestService.getDataList();
	}
	
	@GetMapping(value = "/getDataWithResponsEntity", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DataBean>> getdatawithresponseentity() {
		List<DataBean> list = commonRestService.getDataList();
		return ResponseEntity.ok(list);
	}

}
