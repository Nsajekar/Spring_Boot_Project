package com.spring.main.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWVerifyObject {
	
	boolean isSignatureValid = false;
	String payloadAfterVerification;

}
