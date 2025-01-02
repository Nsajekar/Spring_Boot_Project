package com.spring.main.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.constants.MasterConstants;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.DataBean;
import com.spring.main.service.CommonRestService;
import com.spring.main.utils.CommonUtils;
import com.spring.main.utils.LoggerUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@Tag(name = "Spring Rest Controller", description = "Spring Boot Rest API")
@RequestMapping(value = "/spring/rest")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SpringRestController {
	
	String className = this.getClass().getSimpleName();
	
	CommonRestService commonRestService;
	CommonUtils commonUtils;
	LoggerUtils loggerUtils;
	
	public SpringRestController(CommonRestService commonRestService, CommonUtils commonUtils, LoggerUtils loggerUtils) {
		this.commonRestService = commonRestService;
		this.commonUtils = commonUtils;
		this.loggerUtils = loggerUtils;
	}

	/**
	 * Returns Data From Database
	 * @return
	 */
	@GetMapping(value = "/getDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonResponseBean<List<DataBean>>> getData() {
		return ResponseEntity.ok(new CommonResponseBean<>("000","SUCCESS",commonRestService.getDataList()));
	}
	
	/**
	 * Returns Data From Database
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/commonRequestReponsePostMethod", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonResponseBean<?>> commonRequestReponsePostMethod(@RequestBody @Valid CommonRequestBean<Object> requestData) throws Exception {
		String methodName = "commonRequestReponsePostMethod";
		CommonRequestBean<DataBean> responseData = commonUtils.getBeanFromObject(requestData, DataBean.class);
		loggerUtils.doLog(MasterConstants.LTI, className, methodName, responseData.toString());
		return ResponseEntity.ok(new CommonResponseBean<>("000","SUCCESS",commonRestService.getDataList()));
	}
	
	/**
	 * Returns Data From Database
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/commonRequestReponsePostMethodWithoutResponseEntity", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponseBean<?> commonRequestReponsePostMethodWithoutResponseEntity(@RequestBody @Valid CommonRequestBean<Object> requestData) throws Exception {
		String methodName = "commonRequestReponsePostMethodWithoutResponseEntity";
		CommonRequestBean<DataBean> responseData = commonUtils.getBeanFromObject(requestData, DataBean.class);
		loggerUtils.doLog(MasterConstants.LTI, className, methodName, responseData.toString());
		return new CommonResponseBean<>("000","SUCCESS",commonRestService.getDataList());
	}

}
