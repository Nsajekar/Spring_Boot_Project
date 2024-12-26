package com.spring.main.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.spring.main.constants.MasterConstants;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoggerUtils {
	
	static final Logger logger = LogManager.getLogger(LoggerUtils.class);

	public void doLog(int type, String className, String methodName, String description) {
		StringBuilder strMessage = new StringBuilder();

		strMessage.append("SPRING BOOT LOG : ");
		strMessage.append("Class :");
		strMessage.append(className).append(" || ");
		strMessage.append("Method :");
		strMessage.append(methodName).append(" || ");
		strMessage.append(description);

		String logString = strMessage.toString();

		if (type == MasterConstants.LTI) {
			logger.info(logString);
		} else if (type == MasterConstants.LTW) {
			logger.warn(logString);
		} else if (type == MasterConstants.LTE) {
			logger.error(logString);
		} else if (type == MasterConstants.LTD) {
			logger.debug(logString);
		} else {
			logger.warn(logString);
		}
	}
	
	public void doLog(String className, String methodName, String description) {
		doLog(MasterConstants.LTE, className, methodName, description);
	}
}
