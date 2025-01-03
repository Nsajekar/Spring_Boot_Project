package com.spring.main.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.spring.main.constants.MasterConstants;

@Configuration
public class ErrorMapConfiguration {
	
	@Autowired
	Environment env;
	
	@Bean(name = "customErrorMap")
	Map<String, String> getErrorMessageClass(){
		Map<String, String> respMap = new HashMap<>();
		respMap.put(MasterConstants.ErrorResponseCodes.INVALID_API_PSD, env.getProperty("request.error.invalidChannelIdOrPass"));
		respMap.put(MasterConstants.ErrorResponseCodes.DUP_REF_NO, env.getProperty("request.error.duplicateRefNo"));
		respMap.put(MasterConstants.ErrorResponseCodes.REQ_DATA, env.getProperty("request.error.dataRequired"));
		respMap.put(MasterConstants.ErrorResponseCodes.INVALID_DATA_TYPE, env.getProperty("request.error.invalidDataType"));
		respMap.put(MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH, env.getProperty("request.error.invalidDataLength"));
		return respMap;
	}

}
