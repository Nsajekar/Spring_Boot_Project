package com.spring.main.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.spring.main.annotation.Log;
import com.spring.main.constants.MasterConstants;
import com.spring.main.utils.LoggerUtils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Aspect
@Component
@ConditionalOnProperty(name="annotation.logging.aspect.enable", havingValue = "true", matchIfMissing=false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoggingAnnotationAspect {
	
	@Autowired
	LoggerUtils loggerUtils;
	
	@Before(value = "@within(com.spring.main.annotation.Log)")
	public void logMethodStart(JoinPoint joinPoint) throws Exception {
		String msg = "Entered";
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		Method method = methodSign.getMethod();
		if (method.isAnnotationPresent(Log.class)) {
			Log log = method.getAnnotation(Log.class);
			if(log.logIgnore()) {
				return;
			}
			if(log.logParameter()) {
				msg += " With Parameters : " + loggerUtils.logRequest(joinPoint.getArgs());
			}
		}
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(), msg);
	}
	
	@AfterReturning(pointcut = "@within(com.spring.main.annotation.Log)", returning = "result")
	public void logMethodEnd(JoinPoint joinPoint, Object result) throws Exception {
		String msg = "Exited";
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		if (result != null && method.isAnnotationPresent(Log.class)) {
				Log log = method.getAnnotation(Log.class);
				if(log.logIgnore()) {
					return;
				}
				if(log.logReturn()) {
					msg += " with Response : " + loggerUtils.logResponse(result) + ". ";
				}
			}
		loggerUtils.doLog(MasterConstants.LTI, method.getDeclaringClass().getSimpleName(), method.getName(), msg);
	}

}
