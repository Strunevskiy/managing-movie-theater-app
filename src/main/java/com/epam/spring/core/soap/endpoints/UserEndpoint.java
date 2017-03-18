package com.epam.spring.core.soap.endpoints;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IUserService;
import com.epam.spring.core.soap.bean.user.GetAllUsersRequest;
import com.epam.spring.core.soap.bean.user.GetAllUsersResponse;
import com.epam.spring.core.soap.bean.user.GetUserByEmailRequest;
import com.epam.spring.core.soap.bean.user.GetUserByIdRequest;
import com.epam.spring.core.soap.bean.user.RemoveUserRequest;
import com.epam.spring.core.soap.bean.user.SaveUserRequest;

@Endpoint               
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://spring-course.com/schemas";  
   
	@Autowired
	private IUserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByEmailRequest")  
    @ResponsePayload
    public User getUserByEmail(@RequestPayload GetUserByEmailRequest email) {
		User user = userService.getUserByEmail(email.getEmail());
    	return user;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")  
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest allUsersRequest) {
    	Collection<User> users = userService.getAll();

    	GetAllUsersResponse allUsersResponse = new GetAllUsersResponse();
    	allUsersResponse.getUser().addAll(users);
    	
		return allUsersResponse;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByIdRequest")  
    @ResponsePayload
    public User getUserById(@RequestPayload GetUserByIdRequest userByIdRequest) {
		User obtainedUser = userService.getById(userByIdRequest.getId());
    	return obtainedUser;
	}
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RemoveUserRequest")  
    public void removeUser(@RequestPayload RemoveUserRequest removeUserRequest) {
		userService.remove(removeUserRequest.getUser());
	}
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserRequest")  
    public void saveUser(@RequestPayload SaveUserRequest saveUserRequest) {
		userService.save(saveUserRequest.getUser());
	}

}