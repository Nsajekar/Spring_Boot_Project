package com.spring.main.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;

public interface JWEService {

	String jweVerifyAndDecrypt(String string) throws ParseException, JOSEException;

	String jweEncryptAndSign(String toEncryptString);

}
