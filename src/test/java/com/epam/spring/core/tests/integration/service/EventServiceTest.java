package com.epam.spring.core.tests.integration.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.event.EventRating;
import com.epam.spring.core.service.IEventService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration
public class EventServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	private static final String TEST_EVENT_NAME = "EPAM";
	private static final double TEST_EVENT_PRICE = 100.0;
	private static final EventRating TEST_EVENT_RATING = EventRating.HIGH;
	private static final Date TEST_EVENT_DATE_TIME = new DateTime(2017, 2, 3, 18, 30, 0, 0).toDate();

	private final static String TEST_AUDITORIUM_NAME = "small";
	private final static int TEST_AUDITORIUM_NUMBER_OF_SEATS = 50;
	private final static List<Long> TEST_AUDITORIUM_VIP_SEATS_OF_SMALL = Arrays.asList(5l, 6l ,7l);
	
	@Autowired
	private IEventService eventService;	

	private Event testEvent;
	private Auditorium testAuditorium;
	
	@BeforeClass
	public void initAuditoriums() {
		testAuditorium = new Auditorium();
		testAuditorium.setName(TEST_AUDITORIUM_NAME);
		testAuditorium.setNumberOfSeats(TEST_AUDITORIUM_NUMBER_OF_SEATS);
		testAuditorium.setVipSeats(new HashSet<Long>(TEST_AUDITORIUM_VIP_SEATS_OF_SMALL));
		testAuditorium.setDate(TEST_EVENT_DATE_TIME);
	}
	
	@BeforeClass
	public void initEvent() {
		testEvent = new Event();
		testEvent.setName(TEST_EVENT_NAME);
		testEvent.setBasePrice(TEST_EVENT_PRICE);
		testEvent.setRating(TEST_EVENT_RATING);
		testEvent.assignAuditorium(TEST_EVENT_DATE_TIME, testAuditorium);
	}
	
	@Rollback
	@Test
	public void eventServiceGetByNameTest() {			
		Event persistedEvent = eventService.save(testEvent);
		Assert.assertTrue(eventService.getAll().size() == 1);
		Assert.assertEquals(persistedEvent, eventService.getByName(TEST_EVENT_NAME));
		Assert.assertEquals(persistedEvent, eventService.getById(persistedEvent.getId()));
	}

}
