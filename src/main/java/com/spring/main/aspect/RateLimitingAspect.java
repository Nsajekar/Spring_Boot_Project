package com.spring.main.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.main.config.RateLimiterConfiguration;
import com.spring.main.exception.RateLimitReachedException;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@Aspect
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateLimitingAspect {

	static final Logger logger = LoggerFactory.getLogger(RateLimitingAspect.class);

	final RateLimiterConfiguration rateLimiterConfig;
	
	public RateLimitingAspect(RateLimiterConfiguration rateLimiterConfig) {
		this.rateLimiterConfig = rateLimiterConfig;
	}
	
	@Around(value = "@within(com.ren.apis.annotation.RateLimit)")
	public Object applyRateLimiting(ProceedingJoinPoint joinPoint) throws Throwable {
		jakarta.servlet.http.HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
																	.getRequestAttributes()).getRequest();
		String apiName = httpServletRequest.getRequestURI();
		logger.info("Entered RateLimitingAspssect.applyRateLimiting() With Api Name : " + apiName);
		RateLimiter ratelimiter = rateLimiterConfig.getOrCreateBucket(apiName);
		boolean isPermissionGranted = ratelimiter.acquirePermission();
        if(!isPermissionGranted) {
        	logger.info("Exited RateLimitingAspect.applyRateLimiting() With Api Name : " + apiName + "With Error : Too Many Reuest");
        	throw new RateLimitReachedException("Too Many Reuest For Api : " + apiName);
        }
    	logger.info("Exited RateLimitingAspect.applyRateLimiting() With Api Name : " + apiName);
		return joinPoint.proceed();
	}
	
}
