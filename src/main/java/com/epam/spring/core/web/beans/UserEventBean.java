package com.epam.spring.core.web.beans;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.user.User;

public class UserEventBean {
	
	private User user;
	private Event event;
	
	private UserEventBean() {
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

}
