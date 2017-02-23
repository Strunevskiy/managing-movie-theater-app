package com.epam.spring.core.service.discount.impl;

import java.util.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.discount.IDiscount;

/**
 * @author alehstruneuski
 */
public class BirthdayDiscountImpl implements IDiscount {

	private static final double BIRTHDAY_DISCOUNT = 0.05;
	
	@Override
	public double getDiscount(User user, Event event, Date airDateTime, long numberOfTickets) {
		LocalDate airDateTimeToLocalDate = new LocalDate(airDateTime);
		LocalDate userBirthdayToLocalDate = new LocalDate(user.getBirthday());

		LocalDate dateAirDate = new LocalDate(0, airDateTimeToLocalDate.monthOfYear().get(), airDateTimeToLocalDate.getDayOfMonth());		
		LocalDate dateBirthdayUser = new LocalDate(0, userBirthdayToLocalDate.monthOfYear().get(), userBirthdayToLocalDate.getDayOfMonth());
		int diffDays = Days.daysBetween(dateAirDate, dateBirthdayUser).getDays();
		return (0 >= diffDays && diffDays <= 5) ? BIRTHDAY_DISCOUNT : 0;
	}

}
