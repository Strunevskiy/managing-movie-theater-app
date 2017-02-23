package com.epam.spring.core.service;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.user.User;

/**
 * @author alehstruneuski
 */
public interface IDiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     * 
     * @param user
     *            User that buys tickets. Can be <code>null</code>
     * @param event
     *            Event that tickets are bought for
     * @param airDateTime
     *            The date and time event will be aired
     * @param numberOfTickets
     *            Number of tickets that user buys
     * @return discount value from 0 to 100
     */
    double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull Date airDateTime, long numberOfTickets);

}
