package com.spring.main.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.spring.main.annotation.Log;
import com.spring.main.exception.CryptographyException;
import com.spring.main.exception.DecryptionException;
import com.spring.main.model.JWVerifyObject;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Log
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWEServiceImpl implements JWEService {
	
	@Autowired
	@Qualifier("consumerPublicKey")
	private RSAPublicKey consumerPublicKey;

	@Autowired
	@Qualifier("clientPrivateKey")
	private RSAPrivateKey clientPrivateKey;

	/**
	 * De-cryption Logic
	 * @param payloadToVerifyAndDecrypt
	 * @return
	 */
	@Override
	public String jweVerifyAndDecrypt(String payloadToVerifyAndDecrypt) throws ParseException, JOSEException {
		JWVerifyObject jwVerifyObject = jwSignatureVerify(consumerPublicKey, payloadToVerifyAndDecrypt);
		if(jwVerifyObject.isSignatureValid()) {
			try {
				return jweDecrypt(clientPrivateKey, jwVerifyObject.getPayloadAfterVerification());
			} catch (Exception e) {
				throw new DecryptionException("Exception Occured In Decryption : "+e.getMessage());
			}
		}else {
			throw new CryptographyException();
		}
	}

	/**
	 * 
	 * @param publicKey
	 * @param payloadToVerifyAndDecrypt
	 * @return
	 * @throws ParseException
	 * @throws JOSEException
	 */
	private JWVerifyObject jwSignatureVerify(RSAPublicKey publicKey, String payloadToVerifyAndDecrypt) throws ParseException, JOSEException {
		JWVerifyObject jwVerifyObject = new JWVerifyObject();
		JWSObject jwsObject = JWSObject.parse(payloadToVerifyAndDecrypt);
		JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);
		boolean isVerified = jwsObject.verify(jwsVerifier);
		jwVerifyObject.setSignatureValid(isVerified);
		if(isVerified) {
			jwVerifyObject.setPayloadAfterVerification(payloadToVerifyAndDecrypt);
		}
		return jwVerifyObject;
	}

	/**
	 * 
	 * @param privatekey
	 * @param payloadAfterVerification
	 * @return
	 * @throws ParseException
	 * @throws JOSEException
	 */
	private String jweDecrypt(RSAPrivateKey privatekey, String payloadAfterVerification) throws ParseException, JOSEException {
		JWEObject jweObject = JWEObject.parse(payloadAfterVerification);
		jweObject.decrypt(new RSADecrypter(privatekey));
		return jweObject.getPayload().toString();
	}
}
