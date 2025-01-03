package com.spring.main.service;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
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
	
	@Qualifier("consumerPublicKey")
	final RSAPublicKey consumerPublicKey;
	
	@Qualifier("clientPrivateKey")
	final RSAPrivateKey clientPrivateKey;

	public JWEServiceImpl( RSAPublicKey consumerPublicKey,RSAPrivateKey clientPrivateKey) {
		this.consumerPublicKey = consumerPublicKey;
		this.clientPrivateKey = clientPrivateKey;
	}

	/**
	 * Encryption Logic
	 * @param payloadToEncryptAndSign
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws JOSEException 
	 */
	@Log(logParameter = true,logReturn = true)
	@Override
	public String jweEncryptAndSign(String payloadToEncryptAndSign) throws NoSuchAlgorithmException, JOSEException {
		JWEAlgorithm algorithm = JWEAlgorithm.RSA_OAEP_256;
		EncryptionMethod method = EncryptionMethod.A128GCM;
		String encryptedResult = jweEncrypt(consumerPublicKey,algorithm,method,payloadToEncryptAndSign);
		return jwSign(clientPrivateKey,encryptedResult);
	}

	/**
	 * 
	 * @param consumerPublicKey2
	 * @param algorithm
	 * @param method
	 * @param payloadToEncryptAndSign
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws JOSEException 
	 */
	private String jweEncrypt(RSAPublicKey consumerPublicKey, JWEAlgorithm algorithm, EncryptionMethod method,
			String payloadToEncryptAndSign) throws NoSuchAlgorithmException, JOSEException {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(method.cekBitLength());
		SecretKey key = generator.generateKey();
		JWEHeader header = new JWEHeader(algorithm, method);
		JWEObject jweObject = new JWEObject(header, new Payload(payloadToEncryptAndSign));
		jweObject.encrypt(new RSAEncrypter(consumerPublicKey, key));
		return jweObject.serialize();
	}

	/**
	 * 
	 * @param clientPrivateKey 
	 * @param encryptedResult
	 * @return
	 * @throws JOSEException 
	 */
	private String jwSign(RSAPrivateKey clientPrivateKey, String encryptedResult) throws JOSEException {
		JWSSigner jwsSigner = new RSASSASigner(clientPrivateKey);
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.RS256),new Payload(encryptedResult));
		jwsObject.sign(jwsSigner);
		return jwsObject.serialize();
	}

	/**
	 * De-cryption Logic
	 * @param payloadToVerifyAndDecrypt
	 * @return
	 */
	@Log(logParameter = true,logReturn = true)
	@Override
	public String jweVerifyAndDecrypt(String payloadToVerifyAndDecrypt) throws ParseException, JOSEException {
		JWVerifyObject jwVerifyObject = jwSignatureVerify(consumerPublicKey, payloadToVerifyAndDecrypt);
		if(jwVerifyObject.isSignatureValid()) {
			try {
				return jweDecrypt(clientPrivateKey, jwVerifyObject.getPayloadAfterVerification());
			} catch (Exception e) {
				throw new DecryptionException("Exception Occured In Decryption : "+ e);
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
			jwVerifyObject.setPayloadAfterVerification(jwsObject.getPayload().toString());
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
