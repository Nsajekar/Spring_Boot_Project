package com.spring.main.model;

import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonRequestBean <T>{

	//TODO - ADD VALIDATION USING @VALID
	
	String partitionId;
	
	String requestRefNo;

	String requestTimeStamp;
	
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
