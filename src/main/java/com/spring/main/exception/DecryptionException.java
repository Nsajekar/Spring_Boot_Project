package com.spring.main.exception;

public class DecryptionException extends RuntimeException{

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public DecryptionException(String s) {
		super(s);
	}
}
