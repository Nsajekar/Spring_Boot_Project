package com.spring.main.model.entity;

import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataBean {

	String partition_id;
	
	String request_ref_no;
	
	String request_time_stamp;

	@Override
	public String toString() {
		try {
			return new Gson().toJson(this);
		}catch(Exception e) {
			return "";
		}
	}
	
	
	
}
