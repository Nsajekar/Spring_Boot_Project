package com.spring.main.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.spring.main.constants.MasterConstants;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.model.CommonResponseBean;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoggerUtils {
	
	static final Logger logger = LogManager.getLogger(LoggerUtils.class);
	
	@Qualifier("gson")
	@Autowired
	Gson gson; 

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

	/**
	 * Returns parameters of method as string
	 * 
	 * @param jpObj
	 * @return
	 * @throws Exception
	 */
	public String logRequest(Object[] args) throws Exception{
		StringBuilder reqParamMsg = new StringBuilder(MasterConstants.EMPTY_STRING);
		CommonRequestBean<?> reqBean = new CommonRequestBean<>();
		if(args.length > 0){
			for(Object o : args){
				if(o instanceof CommonRequestBean<?>){
					reqBean = (CommonRequestBean<?>) o;
					reqParamMsg.append(gson.toJson(reqBean));
				}else {
					reqParamMsg.append(o.toString()).append(" | ");
				}
			}
		}
		return StringUtils.isNotBlank(reqParamMsg.toString()) ? reqParamMsg.toString() : "None";
	}

	/**
	 * Returns Result of Method as string
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public String logResponse(Object result) throws Exception{
		String respMassage = "";
		CommonResponseBean<?> respBean = new CommonResponseBean<>();
		if(result instanceof CommonResponseBean<?>){
			respBean = (CommonResponseBean<?>) result;
			respMassage = String.format("%s%s", respMassage, gson.toJson(respBean));
		}else{
			respMassage += result == null ? MasterConstants.EMPTY_STRING : result.toString();
		}
		return respMassage;
	}
}
