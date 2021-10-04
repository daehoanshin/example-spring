package me.xbb123.configuration.web.bind.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import me.xbb123.configuration.exception.BaseException;
import me.xbb123.configuration.http.BaseRespose;

@ControllerAdvice
public class BaseControllerAdvice {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = { BaseException.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	private BaseRespose<?> handleBaseException(BaseException e, WebRequest request) {
		return new BaseRespose<String>(e.getResponseCode(), messageSource.getMessage(e.getResponseCode().name(), e.getArgs(), null));
	}
}
