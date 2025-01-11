package com.spring.main.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.spring.main.annotation.IgnoreAop;
import com.spring.main.exception.RateLimitReachedException;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;
import com.spring.main.model.ErrorResponseBean;
import com.spring.main.utils.CommonUtils;

import io.swagger.v3.oas.annotations.Hidden;

@IgnoreAop
@RestControllerAdvice
@Hidden
public class SpringRestAdviceController implements RequestBodyAdvice{
	
	protected CommonRequestBean<?> reqBody = null;
	
	private static final Logger logger = LogManager.getLogger(SpringRestAdviceController.class);
	
	final CommonUtils commonUtils;
	
	public SpringRestAdviceController(CommonUtils commonUtils) {
		super();
		this.commonUtils = commonUtils;
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleThrows(Throwable ex) {
		logger.error(ex.getLocalizedMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		logger.error(ex.getLocalizedMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
	}
	
	@ExceptionHandler(RateLimitReachedException.class)
    public ResponseEntity<String> rateLimitReachedException(RateLimitReachedException ex) {
    	logger.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getLocalizedMessage());
    }
	
	/**
	 * Method Returns Error Response When Field validation Failed 
	 * if request body is null the ResponseEntity is type of CommonResponseBean and return it.
	 * else it will return ErrorResponseBean
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ResponseEntity<?> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getFieldErrors());
    	BindingResult errors = ex.getBindingResult(); 
		if (errors.hasErrors()) {
			logger.info(()->MessageFormat.format("Errors Found in Request Binding : {0}", errors));
			if (reqBody != null) {
				//TODO - SORT ERROR MESSAGES ALPHABETICALLY
				CommonResponseBean<Object> responseBean = new CommonResponseBean<>();
				for (ObjectError error : errors.getAllErrors()) {
					String fieldName = ((FieldError) error).getField();
					responseBean.setResponseCode(error.getDefaultMessage());
					responseBean.setResponseMessage(commonUtils.getErrorMessages(error.getDefaultMessage(),fieldName));
				}
				responseBean.setResponseData(new HashMap<>());
				responseBean = commonUtils.processResponseBean(reqBody, responseBean);
				responseEntity = ResponseEntity.ok(responseBean);
			} else {
				//TODO - SORT ERROR MESSAGES ALPHABETICALLY
				ErrorResponseBean errorResponseBean = new ErrorResponseBean();
				for (ObjectError error : errors.getAllErrors()) {
					String fieldName = ((FieldError) error).getField();
					errorResponseBean.setResponseCode(error.getDefaultMessage());
					errorResponseBean.setResponseMessage(commonUtils.getErrorMessages(error.getDefaultMessage(),fieldName));
				}
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseBean);
			}
		}
		return responseEntity;
    }

	@Override
	public boolean supports(MethodParameter param, Type type,
			Class<? extends HttpMessageConverter<?>> arg2) {
		String typeName = "";
		for(Type t : param.getMethod().getGenericParameterTypes())
			typeName = t.getTypeName();
		return type.getTypeName().equals(typeName);
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		return inputMessage;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		if(body instanceof CommonRequestBean) {
			reqBody = (CommonRequestBean<?>) body;
		}
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}
	

}
