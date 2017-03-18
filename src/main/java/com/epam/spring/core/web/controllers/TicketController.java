package com.epam.spring.core.web.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.web.beans.UserEventBean;
import com.epam.spring.core.web.view.TicketsPdfView;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	
	private static final String TICKETS_VIEW = "ticket/tickets_view";
	private static final String TICKET_ACTION_VIEW = "action_view";
	private static final String TICKET_PRICE_VIEW = "ticket/ticket_price_view";

	@Autowired
	private IBookingService bookingService;	
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView bookTickets(@RequestBody Set<Ticket> tickets) {
		bookingService.bookTickets(tickets);

		ModelAndView actionView = new ModelAndView(TICKET_ACTION_VIEW);
		actionView.addObject("entity", "tickets");
		actionView.addObject("action", "booking");
		return actionView;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
	public ModelAndView getPurchasedTicketsForEventPdf(
			@RequestParam(required = false) long eventId, 
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) String date) 
	{
		Event event = new Event();
		event.setId(eventId);
		Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(event, DateTime.parse(date).toDate());

		ModelAndView actionView = new ModelAndView(TICKETS_VIEW);
		actionView.addObject("entity", "tickets");
		actionView.addObject("tickets", purchasedTickets);
		actionView.setView(new TicketsPdfView());
		return actionView;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "!application/pdf")
	public ModelAndView getPurchasedTicketsForEvent(
			@RequestParam(required = false) long eventId, 
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) String date) 
	{
		Event event = new Event();
		event.setId(eventId);
		Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(event, DateTime.parse(date).toDate());

		ModelAndView actionView = new ModelAndView(TICKETS_VIEW);
		actionView.addObject("entity", "tickets");
		actionView.addObject("tickets", purchasedTickets);
		return actionView;
	}
	
	@RequestMapping(value = "/price", method = RequestMethod.POST)
	public ModelAndView getTicketsPrice(
			@RequestBody UserEventBean userEventBean, 
			@RequestParam Date dateTime, 
			@RequestParam(value="seat") Collection<Long> seats) 
	{
		double ticketPrice = bookingService.getTicketsPrice(userEventBean.getEvent(), dateTime, userEventBean.getUser(), new HashSet<Long>(seats));
		
		ModelAndView ticketPriceView = new ModelAndView(TICKET_PRICE_VIEW);
		ticketPriceView.addObject("entity", "tickets");
		ticketPriceView.addObject("ticketPrice", ticketPrice);
		return ticketPriceView;
	}
	
}
