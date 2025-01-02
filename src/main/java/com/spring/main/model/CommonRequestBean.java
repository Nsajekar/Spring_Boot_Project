package com.spring.main.model;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;

import com.google.gson.Gson;
import com.spring.main.constants.MasterConstants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Scope(scopeName = "request")
public class CommonRequestBean <T>{

	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	String requestRefNo;

	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	String requestTimeStamp;
	
	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	String apiID;
	
	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	String apiPassword;
	
//	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
//	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@Valid
	T requestData;
	
	public CommonRequestBean(T requestData, CommonRequestBean<?> reqBean) {
		BeanUtils.copyProperties(reqBean, this);
		this.requestData = requestData;
	}

	@Override
	public String toString() {
		try {
			return new Gson().toJson(this);
		}catch(Exception e) {
			return "";
		}
	}
	
	
	
	
}
