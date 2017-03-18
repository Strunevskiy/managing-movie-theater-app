package com.epam.spring.core.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/upload/files")
public class FileUploadController {

	private final static String UPLOAD_FORM = "upload/upload_form";
	private final static String SUCCESS_UPLOAD = "upload/success";
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IEventService eventService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadUploadForm() {
		return UPLOAD_FORM;
	}

	@RequestMapping(value = "/usersEvents", method = RequestMethod.POST)
	public String handleFileUpload(
			@RequestParam(required = true) CommonsMultipartFile fileUsers,
			@RequestParam(required = true) CommonsMultipartFile fileEvents) 
	{
		Gson gson = new Gson();

		User[] users = gson.fromJson(new String(fileUsers.getBytes()), User[].class);
		for (User user : users) {
			userService.save(user);
		}
		
		Event[] events = gson.fromJson(new String(fileEvents.getBytes()), Event[].class);
		for (Event event : events) {
			eventService.save(event);
		}
		return SUCCESS_UPLOAD;
	}
	
}
