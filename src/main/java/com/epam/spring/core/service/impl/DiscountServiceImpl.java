package com.epam.spring.core.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.IUserService;
import com.epam.spring.core.service.discount.IDiscount;

/**
 * @author alehstruneuski
 */
@Service
public class DiscountServiceImpl implements IDiscountService {

	@Autowired
	private IUserService userService;
	@Resource(name="bunchOfDiscounts")
	private List<IDiscount> discounts;

	@Override
	public double getDiscount(User user, Event event, Date airDateTime, long numberOfTickets) {
		User userFromDB = userService.getById(user.getId());
		User userToCountDiscount = checkIfRegisteredUser(userFromDB, user);
		double discountCurrent = 0;
		for (IDiscount discount : discounts) {
			double obtainedDiscount = discount.getDiscount(userToCountDiscount, event, airDateTime, numberOfTickets);
			if (discountCurrent < obtainedDiscount) {
				discountCurrent = obtainedDiscount;
			}
		}
		return discountCurrent;
	}

	private User checkIfRegisteredUser(User userFromDB, User passeUser) {
		if (userFromDB != null) {
			return userFromDB;
		}
		return passeUser;
	}

	
}
