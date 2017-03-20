package com.epam.spring.core.web.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.epam.spring.core.web.controllers")
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<String> expHandler(Exception e) {
		return new ResponseEntity<String>("Exception occured " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
