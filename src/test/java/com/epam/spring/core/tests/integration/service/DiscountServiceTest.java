package com.epam.spring.core.tests.integration.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
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
import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.event.EventRating;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration
public class DiscountServiceTest extends AbstractTransactionalTestNGSpringContextTests {

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
	
	//Expected discount
	private static final double EXPECTED_HAPPY_DISCOUNT = 0.5;
	private static final double EXPECTED_BIRTHDAY_DISCOUNT = 0.05;

	@Autowired
	private IDiscountService discountService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IEventService eventService;

	private Event testEvent;
	private User testUser;
	private Auditorium testAuditorium;
	
	private int dayOfMonthUserBirthday;
	private int monthOfUserBirthday;
	
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
		
		eventService.save(testEvent);
	}
	
	@BeforeClass
	public void saveUserTest() {
		testUser = new User();
		testUser.setFirstName(TEST_USER_FIRST_NAME);
		testUser.setLastName(TEST_USER_LAST_NAME);
		testUser.setEmail(TEST_USER_EMAIL);
		testUser.setBirthday(TEST_USER_BIRTHDAY);
		
		userService.save(testUser);
	}
	
	@BeforeClass(dependsOnMethods = {"saveEventTest", "saveUserTest"})
	public void initTest() {
		testEvent = eventService.getByName(TEST_EVENT_NAME);
		testUser = userService.getUserByEmail(TEST_USER_EMAIL);
		
		String userBirthday = testUser.getBirthday().toString();
		dayOfMonthUserBirthday = getDate(userBirthday, "YYYY-MM-dd HH:mm:ss.SSS").getMonthOfYear();
		monthOfUserBirthday = getDate(userBirthday, "YYYY-MM-dd HH:mm:ss.SSS").getDayOfMonth();	
	}
	
	@Test(description = "invoke lucky discount")
	public void getDiscountEachTenthTicket() {
		Date airDateTime = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).minusDays(2).toDate();
		double discountActual = discountService.getDiscount(testUser, testEvent, airDateTime, 20);		
		Assert.assertEquals(discountActual, EXPECTED_HAPPY_DISCOUNT);
	}
	
	@Test(description = "invoke birthday discount")
	public void getDiscountBirthday() {		
		Date airDateTimeEventOnTime = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).toDate();
		double discountActualWithinOneDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventOnTime, 9);		
		Assert.assertEquals(discountActualWithinOneDay, EXPECTED_BIRTHDAY_DISCOUNT);
		
		Date airDateTimeEventFiveDay = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).plusDays(5).toDate();
		double discountActualWithinFiveDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventFiveDay, 9);		
		Assert.assertEquals(discountActualWithinFiveDay, EXPECTED_BIRTHDAY_DISCOUNT);
	}
	
	@Test(description = "invoke all discounts")
	public void getDiscountMatchAlllRequirements() {
		Date airDateTimeEventFiveDay = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).plusDays(2).toDate();
		double discountActualWithinFiveDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventFiveDay, 10);		
		Assert.assertEquals(discountActualWithinFiveDay, EXPECTED_HAPPY_DISCOUNT);
	}
	
    private static DateTime getDate(String date, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(date).withZone(DateTimeZone.getDefault());
    }
	
}
