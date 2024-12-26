package com.spring.main.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointcutExpressionsUtil {

	@Pointcut("execution (* com.spring.main.controller.*.*(..))")
	public void forControllerLog(){}
	
	@Pointcut("execution (* com.spring.main.service.*.*(..))")
	public void forServiceLog(){}
	
	@Pointcut("execution (* com.spring.main.repository.*.*(..))")
	public void forDaoLog(){}
	
	@Pointcut("forServiceLog() || forControllerLog() ||  forDaoLog()")
	public void forlog(){}
	
	@Pointcut("execution (* com.spring.main.repository.*.*(..))")
	public void forExcpection(){}
	
	
	
	
}
