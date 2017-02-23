package com.epam.spring.core.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IAuditoriumService;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;

/**
 * @author alehstruneuski
 */
@Service
public class BookingServiceImpl implements IBookingService {
	
	private static final double RATE_FOR_HIGHT_EVENT_RAITING = 1.2;
	private static final double RATE_VIP_SEAT = 2;

	@Autowired
	private IDiscountService discountService;
	@Autowired
	private IAuditoriumService auditoriumService;
	@Autowired
	private IEventService eventService;
	@Autowired
	private IUserService userService;
	
	@Override
	public double getTicketsPrice(Event event, Date dateTime, User user, Set<Long> seats) {	
		double discount = discountService.getDiscount(user, event, dateTime, seats.size());
		double basePrice = event.getBasePrice();
		if (event.getRating() == EventRating.HIGH) {
			basePrice = basePrice * RATE_FOR_HIGHT_EVENT_RAITING;
		}
		
		basePrice = (basePrice * discount) + basePrice;

		double totalPrice = 0;
		for (Long id : seats) {
			Long eventId = event.getId();
			Event eventById = eventService.getById(eventId);
			Auditorium	auditorium = eventById.getAuditoriums().get(dateTime);
			boolean isSeatVip = auditoriumService.getByName(auditorium.getName()).isSeatVip(id);	
			if (isSeatVip) {
				totalPrice += basePrice * RATE_VIP_SEAT;
			} else {
				totalPrice += basePrice; 
			}
		}
		return totalPrice;
	}

	@Override
	public void bookTickets(Set<Ticket> tickets) {		
		for (Ticket ticket : tickets) {
			Long eventId = ticket.getEvent().getId();
			Event event = eventService.getById(eventId);
			event.getAuditoriums().get(ticket.getDate()).addTicket(ticket);
			
			User userOfTicket = ticket.getUser();
			Long userId = userOfTicket.getId();
			
			if (userService.getById(userId) != null) {
				userOfTicket.getTickets().add(ticket);
				userService.save(userOfTicket);
			}
		}		
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, Date dateTime) {
		Event choosenEvent = eventService.getById(event.getId());
		Map<Date, Auditorium> auditoriums = choosenEvent.getAuditoriums();
		Auditorium auditorium = auditoriums.get(dateTime);
		return auditorium.getTickets();
	}
	
}
