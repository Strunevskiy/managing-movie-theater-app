package com.epam.spring.core.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogInOutController {
	
	private static final String ACCESS_DENIED_PAGE = "access_denied";
	private static final String LOG_IN_OUT_PAGE = "log/loginout";

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public ModelAndView accessDeniedPage() {    	
    	ModelAndView accessDenied = new ModelAndView(ACCESS_DENIED_PAGE);
        return accessDenied;
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
    	ModelAndView loginout = new ModelAndView(LOG_IN_OUT_PAGE);
        return loginout;
    }
         
}
