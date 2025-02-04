package com.spring.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.annotation.IgnoreAop;
import com.spring.main.model.CommonRequestBean;
import com.spring.main.service.CommonRestService;

import io.swagger.v3.oas.annotations.tags.Tag;

@IgnoreAop
@RestController
@Tag(name = "Spring Encrption-Decryption Controller", description = "Spring Boot Test API")
@RequestMapping(value = "/spring/encodingDecoding")
public class EncryptionDecryptionController {

	@Autowired
	CommonRestService commonRestService;

	@PostMapping(value = "encryptData" ,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public CommonRequestBean<Object> encryptData(@RequestBody CommonRequestBean<Object> requestBean) throws Throwable{
		return commonRestService.encryptData(requestBean);
	}
	
	@PostMapping(value = "decryptData" ,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public CommonRequestBean<Object> decryptData(@RequestBody CommonRequestBean<Object> requestBean) throws Throwable{
		return commonRestService.decryptData(requestBean);
	}
	
}
