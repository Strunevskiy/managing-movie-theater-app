package com.epam.spring.core.web.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.service.IEventService;

@Controller
@RequestMapping("/event")
public class EventController {
	
	private static final String EVENTS_VIEW = "event/event_view";
	private static final String EVENTS_ACTION_VIEW = "action_view";
			
	@Autowired
	private IEventService eventService;
		
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getEventById(@RequestParam long id) {
		Event event = eventService.getById(id);
		
		ModelAndView eventsView = new ModelAndView(EVENTS_VIEW);
		eventsView.addObject("entity", "event");
		eventsView.addObject("events", Arrays.asList(event));
		return eventsView;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getEvents(@RequestParam(required = false) String  name) {
		Collection<Event> events;
		if (null != name) {
			Event event = eventService.getByName(name);
			events = Arrays.asList(event);
		} else {
			events = eventService.getAll();
		}
		
		ModelAndView eventsView = new ModelAndView(EVENTS_VIEW);
		eventsView.addObject("entity", "event");
		eventsView.addObject("events", events);
		return eventsView;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView removeEvent(@RequestBody Event event) {
		eventService.remove(event);
		
		ModelAndView eventsView = new ModelAndView(EVENTS_ACTION_VIEW);
		eventsView.addObject("entity", "event");
		eventsView.addObject("action", "removing");
		return eventsView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveEvent(@RequestBody Event event) {
		eventService.save(event);
		
		ModelAndView eventsView = new ModelAndView(EVENTS_ACTION_VIEW);
		eventsView.addObject("entity", "event");
		eventsView.addObject("action", "persisting");
		return eventsView;
	}
}
