package com.epam.spring.core.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IBookingService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
public class BookingServiceTest extends AbstractTransactionalTestNGSpringContextTests {
	
	// User
	private static final String TEST_USER_FIRST_NAME = "Aleh";
	private static final String TEST_USER_LAST_NAME = "Struneuski";
	private static final String TEST_USER_EMAIL = "aleh_struneuski@epam.com";
	private static final DateTime TEST_USER_BIRTHDAY_DATE_TIME = new DateTime(1993, 8, 12, 10, 29);
	private static final Date TEST_USER_BIRTHDAY = TEST_USER_BIRTHDAY_DATE_TIME.toDate();
	
	// Event
	private static final String TEST_EVENT_NAME = "EPAM";
	private static final double TEST_EVENT_PRICE = 100.0;
	private static final EventRating TEST_EVENT_RATING = EventRating.HIGH;
	private static final Date TEST_EVENT_DATE_TIME = new DateTime(2017, 2, 3, 18, 30, 0, 0).toDate();
	
	// Auditorium
	private final static String TEST_AUDITORIUM_NAME = "small";
	private final static int TEST_AUDITORIUM_NUMBER_OF_SEATS = 50;
	private final static List<Long> TEST_AUDITORIUM_VIP_SEATS_OF_SMALL = Arrays.asList(5l, 6l ,7l);

	
	private static final Date TEST_DATE_TIME = new DateTime(2017, 2, 3, 18, 30, 0, 0).toDate();
	private static final double EXPECTED_TICKETS_PRICE = 240.0;

	@Autowired
	private IBookingService bookingService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IEventService eventService;	

	private Event testEvent;
	private User testUser;
	private Auditorium testAuditorium;

	
	@BeforeClass
	public void saveEventTest() {
		testAuditorium = new Auditorium();
		testAuditorium.setName(TEST_AUDITORIUM_NAME);
		testAuditorium.setNumberOfSeats(TEST_AUDITORIUM_NUMBER_OF_SEATS);
		testAuditorium.setVipSeats(new HashSet<Long>(TEST_AUDITORIUM_VIP_SEATS_OF_SMALL));
		testAuditorium.setDate(TEST_EVENT_DATE_TIME);
		
		testEvent = new Event();
		testEvent.setName(TEST_EVENT_NAME);
		testEvent.setBasePrice(TEST_EVENT_PRICE);
		testEvent.setRating(TEST_EVENT_RATING);
		testEvent.assignAuditorium(TEST_EVENT_DATE_TIME, testAuditorium);
		
		testEvent = eventService.save(testEvent);
	}
	
	@BeforeClass
	public void saveUserTest() {
		testUser = new User();
		testUser.setFirstName(TEST_USER_FIRST_NAME);
		testUser.setLastName(TEST_USER_LAST_NAME);
		testUser.setEmail(TEST_USER_EMAIL);
		testUser.setBirthday(TEST_USER_BIRTHDAY);
		
		testUser = userService.save(testUser);
	}

	@BeforeClass(dependsOnMethods = "saveEventTest")
	public void initEventTest() {
		testEvent = eventService.getByName(TEST_EVENT_NAME);
	}
	
	@BeforeClass(dependsOnMethods = "saveUserTest")
	public void initUserTest() {	
		testUser = userService.getUserByEmail(TEST_USER_EMAIL);
	}

	@Test(description = "getTicketsPrice()")
	public void getTicketsPriceTest() {
		Set<Long> seats = new HashSet<>(Arrays.asList(1l, 2l));
		double actualTicketsPrice = bookingService.getTicketsPrice(testEvent, TEST_DATE_TIME, testUser, seats);
		Assert.assertEquals(actualTicketsPrice, EXPECTED_TICKETS_PRICE);
	}
	
	@Test(description = "getPurchasedTicketsForEvent()")
	public void getPurchasedTicketsForEventTest() {
		Ticket firstTestTicket = new Ticket(testUser, testEvent, TEST_EVENT_DATE_TIME,  1); 
		Ticket secondTestTicket = new Ticket(testUser, testEvent, TEST_EVENT_DATE_TIME, 2);
		
		Set<Ticket> tickets = new HashSet<>();
		tickets.add(firstTestTicket);
		tickets.add(secondTestTicket);
		
		bookingService.bookTickets(tickets);
		Set<Ticket> ticketsActual = bookingService.getPurchasedTicketsForEvent(testEvent, TEST_DATE_TIME);
		Assert.assertEquals(ticketsActual, tickets);
	}

}
