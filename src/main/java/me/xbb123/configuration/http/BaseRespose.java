package me.xbb123.configuration.http;

import lombok.Data;

@Data
public class BaseRespose<T> {
	private BaseResponseCode code;
	private String message;
	private T data;
	
	public BaseRespose(T data) {
		this.code = BaseResponseCode.SUCCESS;
		this.data = data;
	}

	public BaseRespose(BaseResponseCode code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
}
