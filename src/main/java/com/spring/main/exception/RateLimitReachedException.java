package com.spring.main.exception;

public class RateLimitReachedException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public RateLimitReachedException(String error) {
		super(error);
	}
}
