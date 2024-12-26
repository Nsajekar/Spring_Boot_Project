package com.spring.main.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.google.gson.Gson;
import com.spring.main.constants.MasterConstants;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.service.CommonRestService;
import com.spring.main.utils.CommonUtils;
import com.spring.main.utils.LoggerUtils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestCommonAspect {

	@Autowired
	CommonRestService commonRestService;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	LoggerUtils loggerUtils;

	@Qualifier("gson")
	@Autowired
	Gson gson; 
	
	//TODO - ADD ENCRYPTION/DICRYPTION LOGIC FOR REQUEST RESPONSE		
	@Around("com.spring.main.aspect.PointcutExpressionsUtil.forControllerLog()")
	public Object processRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable{
		boolean isWrapperReuest = false;
		String requestType = "";
		CommonRequestBean<?> reqBean = new CommonRequestBean<>();
		CommonResponseBean<?> respBean = new CommonResponseBean<>();
		StringBuilder requestData = new StringBuilder();
		
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		Method method = methodSign.getMethod();
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
		
		Object[] args = joinPoint.getArgs();
		if(args.length > 0){
			for(Object o : args){
				if(o instanceof CommonRequestBean<?>){
					reqBean = (CommonRequestBean<?>) o;
					isWrapperReuest = true;
				}else {
					requestData.append(o.toString()).append(" | ");
				}
			}
		}
		
		//TODO - CHECK DUPLICATE REQUEST REFERENCE NUMBER & STORE IN DB TABLE
		if(isWrapperReuest) {
			commonRestService.logRequest(reqBean,requestType);
		}else {
			commonRestService.logRequest(requestData,requestType);
		}
		//LOG REQUEST
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(),"Entered With Parameters : " + loggerUtils.logRequest(args));
		
		Object result = joinPoint.proceed();
		
		//LOG RESPONSE
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(),"Exited with Response : " + loggerUtils.logResponse(result));

		//TODO - STORE IN DB TABLE
		if(result instanceof CommonResponseBean) {
			respBean = (CommonResponseBean<?>) result;
			respBean = commonUtils.processResponseBean(reqBean, respBean);
			commonRestService.logResponse(respBean,requestType);
			return respBean;
		}else {
			commonRestService.logResponse(result,requestType);
			return result;
		}
		
	}
}
