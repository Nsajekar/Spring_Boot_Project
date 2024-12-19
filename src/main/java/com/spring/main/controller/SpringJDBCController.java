package com.spring.main.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.SpringBootProjectApplication;
import com.spring.main.model.DataBean;
import com.spring.main.repository.CommonRestDao;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Spring JDBC Controller", description = "Just For Testing Perpose")
@RestController
@RequestMapping(value = "/spring/jdbc")
public class SpringJDBCController {
	
	private static final Logger logger = LogManager.getLogger(SpringBootProjectApplication.class);

	@Autowired
	CommonRestDao CommonRestDao;

	@GetMapping(value = "/getdata", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DataBean> myfirstApi() {
		logger.info("Method SpringJDBCController.myfirstApi() Started ..");
		List<DataBean> list = CommonRestDao.getDataList();
		return list;
	}

}
