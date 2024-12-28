package com.spring.main.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class RSAUtils {
	
	public RSAPublicKey getRSAPublicKey(Resource publicKeyPath) throws IOException, NoSuchAlgorithmException, CertificateException, InvalidKeySpecException {
		RSAPublicKey publicKey = null;
		String publicKeyCert = loadKey(publicKeyPath);
		boolean isCerificate = publicKeyCert.contains("CERTIFICATE");
		publicKeyCert = StringUtils.replaceChars(publicKeyCert,System.lineSeparator(),"")
												.replace("-----BEGIN PUBLIC KEY-----", "")
												.replace("-----END PUBLIC KEY-----", "");
		if(isCerificate) {
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
	        X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(publicKeyPath.getInputStream());
	        publicKey = (RSAPublicKey) cert.getPublicKey();
		}else {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			byte[] decode = Base64.getDecoder().decode(publicKeyCert);
			X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(decode);
			publicKey = (RSAPublicKey) factory.generatePublic(encodedKeySpec);
		}
		return publicKey;
	}

	public RSAPrivateKey getRSAPrivateKey(Resource privateKeyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		String privateKey = loadKey(privateKeyPath);
		privateKey = StringUtils.replaceChars(privateKey, System.lineSeparator(), "")
								.replace("-----BEGIN PRIVATE KEY-----", "")
								.replace("-----END PRIVATE KEY-----", "");
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
		return (RSAPrivateKey) factory.generatePrivate(encodedKeySpec);
	}
	
	private String loadKey(Resource keyPath) throws IOException {
		try(InputStream inputStream = keyPath.getInputStream()){
			return new String(inputStream.readAllBytes());
		}
	}

}
