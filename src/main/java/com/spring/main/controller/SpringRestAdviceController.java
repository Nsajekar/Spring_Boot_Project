package com.spring.main.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.main.SpringBootProjectApplication;
import com.spring.main.exception.RateLimitReachedException;

@RestControllerAdvice
public class SpringRestAdviceController {
	
	private static final Logger logger = LogManager.getLogger(SpringBootProjectApplication.class);
	
	@ExceptionHandler(RateLimitReachedException.class)
    public ResponseEntity<String> rateLimitReachedException(Exception ex) {
    	logger.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

}
