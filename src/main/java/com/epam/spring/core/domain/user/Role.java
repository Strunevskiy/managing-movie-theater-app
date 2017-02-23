package com.epam.spring.core.domain.user;

import java.io.Serializable;

public enum Role implements Serializable {
	
	RESGISTERED_USER("RESGISTERED_USER"), BOOKING_MANAGER("BOOKING_MANAGER");

	private String role;	 
	private Role(String role) {   
		this.role = role;   
	}
	 
	public String getRole(){  
		return role;  
	}
	   
}
