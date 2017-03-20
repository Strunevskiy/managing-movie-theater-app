package com.epam.spring.core.web.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IUserService;
import com.epam.spring.core.web.exception.ConflictException;
import com.epam.spring.core.web.exception.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<User> getUserByEmail(@RequestParam(required = false) String email) {
		Collection<User> users = null;
		if (null != email) {
			User user = userService.getUserByEmail(email);
			users = Arrays.asList(user);
		} else {
			users = userService.getAll();
		}
		if (users == null) {
			throw new NotFoundException();
		}
		return users;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable long id) {
		User user = userService.getById(id);
		return user;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<String> removeUser(@RequestParam long id) {
		User userToDelete = new User();
		userToDelete.setId(id);
		if (!userService.exists(userToDelete)) {
			throw new NotFoundException();
		}
		userService.remove(userToDelete);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveUser(@RequestBody User userToSave) {
		if (userService.exists(userToSave)) {
			throw new ConflictException();
		}
		User user = userService.save(userToSave);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", String.format("/user/%s", user.getId()));
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
}
