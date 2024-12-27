package com.spring.main.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.main.annotation.IgnoreAop;
import com.spring.main.exception.RateLimitReachedException;

import io.swagger.v3.oas.annotations.Hidden;

@IgnoreAop
@RestControllerAdvice
@Hidden
public class SpringRestAdviceController {
	
	private static final Logger logger = LogManager.getLogger(SpringRestAdviceController.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		logger.error(ex.getLocalizedMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleThrows(Throwable ex) {
		logger.error(ex.getLocalizedMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	}

	@ExceptionHandler(RateLimitReachedException.class)
    public ResponseEntity<?> rateLimitReachedException(Exception ex) {
    	logger.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }
	

}
