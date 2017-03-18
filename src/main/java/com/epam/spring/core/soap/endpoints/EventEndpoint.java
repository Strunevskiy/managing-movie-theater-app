package com.epam.spring.core.soap.endpoints;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.soap.bean.event.GetEventByIdRequest;
import com.epam.spring.core.soap.bean.event.GetEventByNameRequest;
import com.epam.spring.core.soap.bean.event.GetEventsRequest;
import com.epam.spring.core.soap.bean.event.GetEventsResponse;
import com.epam.spring.core.soap.bean.event.RemoveEventRequest;
import com.epam.spring.core.soap.bean.event.SaveEventRequest;

@Endpoint
public class EventEndpoint {
	
    private static final String NAMESPACE_URI = "http://spring-course.com/schemas";  
	
	@Autowired
	private IEventService eventService;
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEventByIdRequest")  
    @ResponsePayload
	public Event getEventById(@RequestPayload GetEventByIdRequest request) {
		Event event = eventService.getById(request.getId());
		return event;
	}
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEventsRequest")  
    @ResponsePayload
	public GetEventsResponse getEvents(@RequestPayload GetEventsRequest request) {
		Collection<Event> events = eventService.getAll();
		
		GetEventsResponse eventsResponse = new GetEventsResponse();
		eventsResponse.getEvent().addAll(events);
		return eventsResponse;
	}
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEventByNameRequest")  
    @ResponsePayload
	public Event getEventByName(@RequestPayload GetEventByNameRequest request) {
		Event event = eventService.getByName(request.getNameOfEvent());
		return event;
	}
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RemoveEventRequest")  
	public void removeEvent(@RequestPayload RemoveEventRequest request) {
		eventService.remove(request.getEvent());
	}
	
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveEventRequest")  
	public void saveEvent(@RequestPayload SaveEventRequest request) {
		eventService.save(request.getEvent());
	}

}
