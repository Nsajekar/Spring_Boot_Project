package com.spring.main.model;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;

import com.google.gson.Gson;

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

	@NotNull(message = "Value Should Not Be NULL!")
	@NotBlank(message = "Value Should Not Be Blanck!")
	String requestRefNo;

	@NotNull(message = "Value Should Not Be NULL!")
	@NotBlank(message = "Value Should Not Be Blanck!")
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
