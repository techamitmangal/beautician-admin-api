package com.beautician.app.exceptions;

import org.springframework.http.HttpStatus;

public class AdminUserServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3550134228239018854L;
	
	private String resMessage ;
	public String getResMessage() {
		return resMessage;
	}

	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}

	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	private HttpStatus httpStatusCode;
	
	public AdminUserServiceException (String message) {
		super(message);
	}
	
	public AdminUserServiceException (String resMessage, HttpStatus httpStatusCode) {
		this.resMessage = resMessage;
		this.httpStatusCode = httpStatusCode ;
	}
}
