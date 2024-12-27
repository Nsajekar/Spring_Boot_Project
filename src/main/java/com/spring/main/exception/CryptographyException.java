package com.spring.main.exception;

public class CryptographyException extends RuntimeException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public CryptographyException() {
		super("Signature Invalid!");
	}
}
