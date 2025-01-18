package com.spring.main.constants;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum RegexConstant {

	ALPHANUMERIC("^[a-zA-Z0-9]*$"),
	ALPHABETS("^[a-zA-Z]*$"),
	ALPHA_NUMERIC_SPACE("^[a-zA-Z0-9\\s]+$"),
	NUMERIC("^[0-9]+$"),
	DECIMAL("^[-+]?\\d*[.]?\\d+|^[-+]?\\d+[.]?\\d*"),
	EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"),
	DEFAULT("");

	final String pattern;

	RegexConstant(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
