package com.spring.main.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spring.main.annotation.Log;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;

import jakarta.annotation.Resource;

@Log
@Component
public class CommonUtils {
	
	@Autowired
	@Resource(name = "customErrorMap")
	private Map<String, String> errorMap;

	/**
	 * Generic method to copy request & response bean
	 * e.g.Partition ID, Request Reference Number
	 * Also creates response times-tamp
	 * @throws Exception 
	 */
	@Deprecated
	public ResponseEntity<?> processResponseBean(CommonRequestBean<?> fromBean, ResponseEntity<?> toBean) {
		CommonResponseBean<?> responseBeanData = new CommonResponseBean<>();
		Object toBeanData = toBean.getBody();
		if(toBeanData instanceof CommonResponseBean) {
			responseBeanData = (CommonResponseBean<?>) toBeanData;
		}
		BeanUtils.copyProperties(toBeanData,responseBeanData);
		responseBeanData.setRequestRefNo(String.valueOf(fromBean.getRequestRefNo()));
		responseBeanData.setResponseTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return ResponseEntity.ok(responseBeanData);
	}

	/**
	 * Generic method to copy request & response bean
	 * e.g.Partition ID, Request Reference Number
	 * Also creates response times-tamp
	 * @throws Exception 
	 */
	public <T,K>CommonResponseBean<K> processResponseBean(CommonRequestBean<T> reqBody, CommonResponseBean<K> respBody) {
        respBody.setRequestRefNo(String.valueOf(reqBody.getRequestRefNo()));
        respBody.setResponseTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return respBody;
	}
	
	public <T> CommonRequestBean<T> getBeanFromObject(CommonRequestBean<Object> request, Class<T> clazz) throws Exception{
		ObjectMapper objMapper = new ObjectMapper();
		CommonRequestBean<T> reqBean = new CommonRequestBean<>();
		BeanUtils.copyProperties(request, reqBean);
		if(request.getRequestData() instanceof Map){
			request.setRequestData(objMapper.writeValueAsString(request.getRequestData()));
		}
		reqBean.setRequestData(new Gson().fromJson( String.valueOf(request.getRequestData()), clazz));
		return reqBean;
	}
	
	@Log(logIgnore = true)
	public String getRequestType(Method method) {
		String requestType;
		if (method.isAnnotationPresent(GetMapping.class)) {
			GetMapping annotation = method.getAnnotation(GetMapping.class);
			requestType = StringUtils.replace(annotation.value()[0], "/", "");
		} else if (method.isAnnotationPresent(PostMapping.class)) {
			PostMapping annotation = method.getAnnotation(PostMapping.class);
			requestType = StringUtils.replace(annotation.value()[0], "/", "");
		}else if (method.isAnnotationPresent(PutMapping.class)) {
			PutMapping annotation = method.getAnnotation(PutMapping.class);
			requestType = StringUtils.replace(annotation.value()[0], "/", "");
		}else if (method.isAnnotationPresent(DeleteMapping.class)) {
			DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
			requestType = StringUtils.replace(annotation.value()[0], "/", "");
		} else {
			requestType = "DEFAULT";
		}
		return requestType;
	}

	@Log(logIgnore = true)
	public String getErrorMessages(String errorCode, String fieldName) {
		String messageCode = "";
		if(StringUtils.isNotBlank(errorCode)) {
			messageCode = errorMap.get(errorCode);
			if(StringUtils.isNotBlank(messageCode)) {
				if(StringUtils.contains(messageCode, "{Key_Name}")) {
					messageCode = StringUtils.replace(messageCode, "{Key_Name}", fieldName);
				}
			}else {
				messageCode = errorCode;
			}
		} 
		return messageCode;
	}
}
