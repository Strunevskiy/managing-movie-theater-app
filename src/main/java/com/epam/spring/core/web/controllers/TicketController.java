package com.epam.spring.core.web.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.web.beans.UserEventBean;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private IBookingService bookingService;	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void bookTickets(@RequestBody Set<Ticket> tickets) {
		bookingService.bookTickets(tickets);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/pdf" })
	public Set<Ticket> getPurchasedTicketsForEventPdf(
			@RequestParam(required = false) long eventId, 
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) String date) 
	{
		Event event = new Event();
		event.setId(eventId);
		Set<Ticket> purchasedTickets = new HashSet<>();//bookingService.getPurchasedTicketsForEvent(event, DateTime.parse(date).toDate());
		
		Ticket ticket1 = new Ticket();
		ticket1.setId(1l);
		ticket1.setSeat(1l);
		ticket1.setTicketPrice(20l);
		purchasedTickets.add(ticket1);
		
		Ticket ticket2 = new Ticket();
		ticket2.setId(2l);
		ticket2.setSeat(2l);
		ticket2.setTicketPrice(30l);
		purchasedTickets.add(ticket2);
		
		return purchasedTickets;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Set<Ticket> getPurchasedTicketsForEventJson(
			@RequestParam(required = false) long eventId, 
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) String date) 
	{
		Event event = new Event();
		event.setId(eventId);
		Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(event, DateTime.parse(date).toDate());
		return purchasedTickets;
	}
	
	@RequestMapping(value = "/price", method = RequestMethod.POST)
	public Ticket getTicketsPrice(
			@RequestBody UserEventBean userEventBean, 
			@RequestParam Date dateTime, 
			@RequestParam(value="seat") Collection<Long> seats) 
	{
		double ticketPrice = bookingService.getTicketsPrice(userEventBean.getEvent(), dateTime, userEventBean.getUser(), new HashSet<Long>(seats));
		
		Ticket ticket = new Ticket();
		ticket.setTicketPrice(ticketPrice);
		return ticket;
	}
	
}
