package com.epam.spring.core.aspect;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import com.epam.spring.core.domain.ticket.Ticket;

/**
 * @author alehstruneuski
 */
//@Aspect
//@Component
public class LuckyWinnerAspect {

    @SuppressWarnings("unchecked")
	@Around("execution(* com.epam.spring.core.service.IBookingService.bookTickets(..))")
    public void accessBookTicketst(ProceedingJoinPoint pjp) throws Throwable {
    	Object[] arguments = pjp.getArgs();
    	Set<Ticket> tickets = (Set<Ticket>) arguments[0];
    	Set<Ticket> ticketsAfterCheckLuck = new ConcurrentSkipListSet<>(tickets);
    	
    	boolean isUpdated = false;
    	for (Ticket ticket : ticketsAfterCheckLuck) {
    		if (isLucky()) {
    			isUpdated = true;
    			ticket.setTicketPrice(0);
    		} 
    	}    	
    	
    	if (isUpdated) {
    		pjp.proceed(new Object[]{ticketsAfterCheckLuck});
    	}
    }

    private boolean isLucky() {
    	return Math.random() < 0.5;
    }
   
}
