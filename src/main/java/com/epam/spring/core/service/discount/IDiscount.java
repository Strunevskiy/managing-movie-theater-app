package com.epam.spring.core.service.discount;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.user.User;

/**
 * @author alehstruneuski
 */
public interface IDiscount {

	public double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull Date airDateTime, long numberOfTickets);
}
