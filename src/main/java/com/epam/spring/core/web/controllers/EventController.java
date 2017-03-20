package com.epam.spring.core.web.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.web.exception.ConflictException;
import com.epam.spring.core.web.exception.NotFoundException;

@RestController
@RequestMapping("/event")
public class EventController {
			
	@Autowired
	private IEventService eventService;
		
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Event getEventById(@RequestParam long id) {
		Event event = eventService.getById(id);	
		return event;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Event> getEvents(@RequestParam(required = false) String  name) {
		Collection<Event> events;
		if (null != name) {
			Event event = eventService.getByName(name);
			events = Arrays.asList(event);
		} else {
			events = eventService.getAll();
		}
		
		if (events == null) {
			throw new NotFoundException();
		}
		
		return events;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<String> removeEvent(@RequestParam long id) {		
		Event eventToDelete = new Event();
		eventToDelete.setId(id);
		
		if (!eventService.exists(eventToDelete)) {
			throw new NotFoundException();
		}
		
		eventService.remove(eventToDelete);
		
		return new ResponseEntity<String>(HttpStatus.OK);		
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveEvent(@RequestBody Event event) {
		if (eventService.exists(event)) {
			throw new ConflictException();
		}
		
		Event persistedEvent = eventService.save(event);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", String.format("/event/%s", persistedEvent.getId()));
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
}
