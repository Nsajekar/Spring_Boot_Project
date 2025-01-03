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
public class ErrorResponseBean {

	String responseCode;

	String responseMessage;
	
	@Override
	public String toString() {
		try {
			return new Gson().toJson(this);
		}catch(Exception e) {
			return "";
		}
	}
}
