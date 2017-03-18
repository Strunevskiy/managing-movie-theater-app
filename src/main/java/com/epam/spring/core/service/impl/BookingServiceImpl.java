package com.epam.spring.core.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.UserAccount;
import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.event.EventRating;
import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IAuditoriumService;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserAccountService;
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
	@Autowired
	private IUserAccountService userAccountService;
	
	@Override
	public void refillingAccount(User user, double money) {
		UserAccount userAccount = userAccountService.getById(user.getId());
		BigDecimal balance = userAccount.getMoneyAmount().add(new BigDecimal(money));
		userAccount.setMoneyAmount(balance);
		userAccountService.save(userAccount);
	}
	
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

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void bookTickets(Set<Ticket> tickets) {	
		if (CollectionUtils.isEmpty(tickets)) {
			throw new IllegalArgumentException();
		}
		
		Ticket ticket = tickets.stream().findAny().get();
		User ticketsOwner = ticket.getUser();
		
		boolean isAvlSeats = isAvailableSeats(tickets);
		if (!isAvlSeats) {
			throw new IllegalStateException();
		}
		
		User user = userService.getUserByEmail(ticketsOwner.getEmail());					
		UserAccount userAccount = userAccountService.getById(user.getId());
		boolean isAvlMoney = isUserAccountHasEnoughMoney(userAccount, tickets);
		if (!isAvlMoney) {
			throw new IllegalStateException();
		}
		
		withdrawMoneyUsertAccount(userAccount, tickets);		
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, Date dateTime) {
		Event choosenEvent = eventService.getById(event.getId());
		Map<Date, Auditorium> auditoriums = choosenEvent.getAuditoriums();
		Auditorium auditorium = auditoriums.get(dateTime);
		return auditorium.getTickets();
	}
	
	private boolean isAvailableSeats(Set<Ticket> tickets) {	
		boolean isAvaliableSeats = true;
		Iterator<Ticket> itr = tickets.iterator();
		while (itr.hasNext()) {
			Ticket ticket = itr.next();
			String eventName = ticket.getEvent().getName();
			Event event = eventService.getByName(eventName);
			Auditorium auditorium = event.getAuditoriums().get(ticket.getDate());
			Set<Long> seats = auditorium.getTickets().stream().map(Ticket::getSeat).collect(Collectors.toSet());
			if (seats.contains(ticket.getSeat())) {
				isAvaliableSeats = false;
			}
		}
		return isAvaliableSeats;
	}
	
	private boolean isUserAccountHasEnoughMoney(UserAccount userAccount, Set<Ticket> tickets) {
		double total = 0.0d;
		for (Ticket ticket : tickets) {
			Event event = eventService.getByName(ticket.getEvent().getName());
			total += event.getTicketPrice().doubleValue();	
		}
		int compareResult = new BigDecimal(total).compareTo(userAccount.getMoneyAmount()); 			
		return Arrays.asList(-1, 0).contains(compareResult);		
	}
	
	private void withdrawMoneyUsertAccount(UserAccount userAccount, Set<Ticket> tickets) {
		
		double total = 0.0d;
		for (Ticket ticket : tickets) {
			String eventName = ticket.getEvent().getName();
			Event event = eventService.getByName(eventName);
			Auditorium auditorium = event.getAuditoriums().get(ticket.getDate());
			
			total += event.getTicketPrice().doubleValue();
			
			User user = userService.getUserByEmail(ticket.getUser().getEmail());
			if (user != null) {
				ticket.setUser(user);
				ticket.setAuditorium(auditorium);
				ticket.setEvent(event);
				user.getTickets().add(ticket);
				userService.updateUser(user);
			}
		}	
		userAccount.setMoneyAmount(userAccount.getMoneyAmount().subtract(new BigDecimal(total)));
		userAccountService.save(userAccount);
	}
	
}
