package com.spring.main.config;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class SpringBootConfiguration {

	@Bean("gson")
	Gson gson() {
		return new Gson();
	}
	
	@Bean("clientPrivateKey")
	RSAPrivateKey clientPrivateKey() {
		return new RSAPrivateKey() {
			
			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public BigInteger getModulus() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getFormat() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public byte[] getEncoded() {
				// TODO Auto-generated method stub
				return new byte[0];
			}
			
			@Override
			public String getAlgorithm() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public BigInteger getPrivateExponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean("consumerPublicKey")
	RSAPublicKey consumerPublicKey() {
		return new RSAPublicKey() {
			
			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public BigInteger getModulus() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getFormat() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public byte[] getEncoded() {
				// TODO Auto-generated method stub
				return new byte[0];
			}
			
			@Override
			public String getAlgorithm() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public BigInteger getPublicExponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
