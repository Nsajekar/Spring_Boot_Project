package com.spring.main.model;

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
public class CommonResponseBean <T>{

	String requestRefNo;

	String responseTimeStamp;
	
	String responseCode;
	
	String responseMessage;
	
	T responseData;
	
	public CommonResponseBean(T data) {
		this.responseData = data;
	}

	public CommonResponseBean(String responseCode,String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public CommonResponseBean(String responseCode,String responseMessage,T data) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseData = data;
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
