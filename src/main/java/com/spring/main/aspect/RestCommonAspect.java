package com.spring.main.aspect;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
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
		String requestType = commonUtils.getRequestType(method);
		
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
		commonRestService.logRequest(reqBody, requestType);
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(),"Entered With Parameters : " + loggerUtils.logRequest(args));
		
		if (isWrapperReuest) {
			//TODO - CHECK DUPLICATE REQUEST REFERENCE NUMBER 
			if (encrDecrFlag) {
				String decryptedRequestString = jweService.jweVerifyAndDecrypt(reqBody.getRequestData().toString());
				reqBody = new CommonRequestBean<>(decryptedRequestString, reqBody);
				result = joinPoint.proceed(new Object[] { reqBody });
			} else {
				result = joinPoint.proceed();
			}
		} else {
			result = joinPoint.proceed();
		}
		
		//LOG RESPONSE
		commonRestService.logResponse(result, requestType);
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(),"Exited with Response : " + loggerUtils.logResponse(result));
		return processResponse(reqBody, result, requestType);
	}

	/**
	 * TEST CASE 1 : WHEN RESPONSE TYPE IS [ANY] 
	 * TEST CASE 2 : WHEN RESPONSE TYPE IS [RESPONSEENTITY<T>] 
	 * 				 & REQUEST DATA <T> IS OF TYPE [COMMONRESPONSEBEAN<T1>] 
	 *     			 & REQUEST DATA <T1> IS ENCRYPTED/NORMAL 
	 * TEST CASE 3 : WHEN RESPONSE TYPE IS [COMMONRESPONSEBEAN<T>] 
	 *  			 & REQUEST DATA <T> IS ENCRYPTED/NORMAL
	 * @param reqBody
	 * @param result
	 * @param requestType
	 * @return
	 * @throws JsonProcessingException
	 * @throws NoSuchAlgorithmException
	 * @throws JOSEException
	 */
	private Object processResponse(CommonRequestBean<?> reqBody, Object result, String requestType)
			throws JsonProcessingException, NoSuchAlgorithmException, JOSEException {
		CommonResponseBean<?> respBody = new CommonResponseBean<>();
		
		if (result instanceof ResponseEntity) {
			ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
			Object responseBody = responseEntity.getBody();
			if (responseBody instanceof CommonResponseBean) {
				respBody = (CommonResponseBean<?>) responseBody;
				if (encrDecrFlag) {
					respBody = encryptResponse(reqBody, respBody);
				}
				respBody = commonUtils.processResponseBean(reqBody, respBody);
				result = ResponseEntity.ok(respBody);
			}
		} else if (result instanceof CommonResponseBean) {
			respBody = (CommonResponseBean<?>) result;
			if (encrDecrFlag) {
				respBody = encryptResponse(reqBody, respBody);
			}
			respBody = commonUtils.processResponseBean(reqBody, respBody);
			result = respBody;
		}
		return result;
	}

	private CommonResponseBean<?> encryptResponse(CommonRequestBean<?> reqBody, CommonResponseBean<?> respBody)
			throws JsonProcessingException, NoSuchAlgorithmException, JOSEException {
		String toEncryptString = new ObjectMapper().writeValueAsString(respBody.getResponseData());
		String response = respBody.getResponseData() != null ? jweService.jweEncryptAndSign(toEncryptString): "";
		respBody = new CommonResponseBean<>(respBody.getResponseCode(),respBody.getResponseMessage(),response);
		return respBody;
	}

}
