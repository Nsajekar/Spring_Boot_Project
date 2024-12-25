package com.spring.main.aspect;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.spring.main.utils.LoggerUtils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
/**
 * @author Nitesh
 * Notes : 
 * 1.WHEN WE HAVE MULIPLE ASPECT THIS ANNOTATION IS USED TO GEVE PREFERENCE IN RUNNING LOWEST THE NUMBER GETS MOST PRIORITY
 */
@Aspect
@Component
@Order(1)
@ConditionalOnProperty(name="annotation.logging.aspect.enable", havingValue = "false", matchIfMissing=false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoggingPointcutAspect {
	
	private static Logger logger = Logger.getLogger(LoggingPointcutAspect.class.getName());

	final LoggerUtils log;
	
	public LoggingPointcutAspect(LoggerUtils log) {
		this.log = log;
	}
	//For Controller & Service & Repository
	@Before("com.spring.main.aspect.PointcutExpressionsUtil.forlog()")
	public void doBeforeLog(JoinPoint joinPoint){
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		log.doLog(2,methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(), "Started.....");
	}
	//For Controller & Service & Repository
	@After("com.spring.main.aspect.PointcutExpressionsUtil.forlog()")
	public void doAfterLog(JoinPoint joinPoint){
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		log.doLog(2, methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(), "Ended.....");
	}
	//For Repository 
	@AfterReturning(pointcut = "com.spring.main.aspect.PointcutExpressionsUtil.forDaoLog()" ,returning = "result")
	public void doAfterReturningLog(JoinPoint joinPoint,Object result) throws IllegalArgumentException, IllegalAccessException{
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		if(result != null){
			for (Field field : result.getClass().getDeclaredFields()) {
				Object value = 	field.get(result);
				log.doLog(2, methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(), value.toString());
			}
		}
		log.doLog(2, methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(), "Ended.....");
	}
	
	//For Repository If Exception Occurred 
	@AfterThrowing(pointcut = "com.spring.main.aspect.PointcutExpressionsUtil.forExcpection()", throwing = "error")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error){
	   logger.info("Method Signature: "  + joinPoint.getSignature());  
	   logger.info("Exception: "+error.getStackTrace());  
	}
	
	//For Repository Before And After Combine
	@Around("com.spring.main.aspect.PointcutExpressionsUtil.forDaoLog()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		log.doLog(2,methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(), "Started.....");
		long beginTime = System.currentTimeMillis();
		Object obj = null ;
		try{
	    obj = joinPoint.proceed();
		}catch(Exception e){
			log.doLog(2,  methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(), "Exception Occured :" + e);
		}
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - beginTime;
		log.doLog(2, methodSign.getDeclaringTypeName(), methodSign.getMethod().getName(),"Time taken to Execute is :" + timeTaken);
		return obj;
	}
	
}
