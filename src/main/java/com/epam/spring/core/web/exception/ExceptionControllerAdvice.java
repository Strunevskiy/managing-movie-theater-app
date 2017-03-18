package com.epam.spring.core.web.exception;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice("com.epam.spring.core.web.controllers")
public class ExceptionControllerAdvice {

	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Exception occured")
	@ExceptionHandler(Exception.class)
	public ModelAndView expHandler(Exception e) {
		ModelAndView errorView = new ModelAndView("error_view");
		errorView.addObject("message",  e.getMessage());
		errorView.addObject("stackTrace", ExceptionUtils.getStackTrace(e));	
		return errorView;
	}
}
