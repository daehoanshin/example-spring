package me.xbb123.configuration.exception;

import me.xbb123.configuration.http.BaseResponseCode;

public abstract class AbstractorException extends RuntimeException {

	private static final long serialVersionUID = 8342235231880246631L;
	protected BaseResponseCode responseCode;
	protected Object[] args;
	
	public AbstractorException() {
		
	}

	public AbstractorException(BaseResponseCode responseCode) {		
		this.responseCode = responseCode;
	}

	public BaseResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(BaseResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}
