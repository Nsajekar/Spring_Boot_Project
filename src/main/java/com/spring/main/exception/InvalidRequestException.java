package com.spring.main.exception;

public class InvalidRequestException extends RuntimeException{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4490482828424619387L;

	public InvalidRequestException(String error) {
		super(error);
	}

}
