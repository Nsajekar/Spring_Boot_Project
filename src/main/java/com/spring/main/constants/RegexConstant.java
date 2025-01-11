package com.spring.main.constants;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum RegexConstant {

	ALPHANUMERIC("^[a-zA-Z0-9]*$"),
	ALPHABETS("^[a-zA-Z]*$"),
	NUMERIC("^[0-9]+$"),
	EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"),
	PHONE_NUMBER("^\\d{10}$"),
	DEFAULT("");

	final String pattern;

	RegexConstant(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
