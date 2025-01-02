package com.spring.main.config;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.google.gson.Gson;
import com.spring.main.utils.RSAUtils;

import jakarta.validation.Validator;

@Configuration
public class SpringBootConfiguration {
	
	@Value("${consumer.public.key}")
	Resource publicKeyPath;
	
	@Value("${client.private.key}")
	Resource privateKeyPath;
	
	@Autowired
	RSAUtils rsaUtils;

	@Bean("gson")
	Gson gson() {
		return new Gson();
	}
	
	@Bean("clientPrivateKey")
	RSAPrivateKey clientPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return rsaUtils.getRSAPrivateKey(privateKeyPath);
	}
	
	@Bean("consumerPublicKey")
	RSAPublicKey consumerPublicKey() throws NoSuchAlgorithmException, CertificateException, IOException, InvalidKeySpecException {
		return rsaUtils.getRSAPublicKey(publicKeyPath);
	}
	
	@Bean(name = "validator")
	Validator validator() {
		return new LocalValidatorFactoryBean(); 
	}
	
}
