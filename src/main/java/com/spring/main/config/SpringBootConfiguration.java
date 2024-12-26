package com.spring.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class SpringBootConfiguration {

	@Bean("gson")
	Gson gson() {
		return new Gson();
	}
}
