package com.spring.main.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.main.constants.MasterConstants;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.service.CommonRestService;
import com.spring.main.service.JWEService;
import com.spring.main.utils.CommonUtils;
import com.spring.main.utils.LoggerUtils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestCommonAspect {
	
	@Value("${request.encryption.decryption.flag}")
	boolean encrDecrFlag;

	final CommonRestService commonRestService;
	final CommonUtils commonUtils;
	final LoggerUtils loggerUtils;
	final JWEService jweService; 

	public RestCommonAspect(CommonRestService commonRestService, CommonUtils commonUtils,
			LoggerUtils loggerUtils, JWEService jweService) {
		this.commonRestService = commonRestService;
		this.commonUtils = commonUtils;
		this.loggerUtils = loggerUtils;
		this.jweService = jweService;
	}

	@Around("!@within(com.spring.main.annotation.IgnoreAop) && com.spring.main.aspect.PointcutExpressionsUtil.forControllerLog()")
	public Object processRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable{
		boolean isWrapperReuest = false;
		StringBuilder requestData = new StringBuilder();
		CommonRequestBean<?> reqBody = new CommonRequestBean<>();
		Object result = null;
		
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		Method method = methodSign.getMethod();
		String requestType = getRequestType(method);
		
		Object[] args = joinPoint.getArgs();
		if(args.length > 0){
			for(Object o : args){
				if(o instanceof CommonRequestBean<?>){
					reqBody = (CommonRequestBean<?>) o;
					isWrapperReuest = true;
				}else {
					requestData.append(o.toString()).append(" | ");
				}
			}
		}
		//LOG REQUEST
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(),"Entered With Parameters : " + loggerUtils.logRequest(args));
		
		if (isWrapperReuest) {
			//TODO - CHECK DUPLICATE REQUEST REFERENCE NUMBER 
			//TODO - STORE IN DB TABLE
			commonRestService.logRequest(reqBody, requestType);
			if (encrDecrFlag) {
				String decryptedRequestString = jweService.jweVerifyAndDecrypt(reqBody.getRequestData().toString());
				reqBody = new CommonRequestBean<>(decryptedRequestString, reqBody);
				result = joinPoint.proceed(new Object[] { reqBody });
			} else {
				result = joinPoint.proceed();
			}
		} else {
			//TODO - STORE IN DB TABLE
			commonRestService.logRequest(requestData,requestType);
			result = joinPoint.proceed();
		}
		
		//LOG RESPONSE
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(),"Exited with Response : " + loggerUtils.logResponse(result));
		return processResponse(reqBody, result, requestType);
	}

	private Object processResponse(CommonRequestBean<?> reqBody, Object result, String requestType) throws JsonProcessingException {
		CommonResponseBean<?> respBody;
		ResponseEntity<?> respBean;
		if(result instanceof ResponseEntity) {
			respBean = (ResponseEntity<?>) result;
			//TODO - STORE IN DB TABLE
			commonRestService.logResponse(respBean,requestType);
			//TODO - ADD ENCRYPTION LOGIC FOR REQUEST 
			Object responseBody = respBean.getBody();
			if (encrDecrFlag && responseBody instanceof CommonResponseBean) {
				ObjectMapper mapper = new ObjectMapper();
				respBody = (CommonResponseBean<?>) responseBody;
				String toEncryptString = mapper.writeValueAsString(respBody.getResponseData());
				String response = respBody.getResponseData() != null ? jweService.jweEncryptAndSign(toEncryptString): "";
				respBody = new CommonResponseBean<>(response);
				respBean = ResponseEntity.ok(respBody);
			}
			respBean = commonUtils.processResponseBean(reqBody, respBean);
			return respBean;
		}else {
			//TODO - STORE IN DB TABLE
			commonRestService.logResponse(result,requestType);
			return result;
		}
	}

	private String getRequestType(Method method) {
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
}
