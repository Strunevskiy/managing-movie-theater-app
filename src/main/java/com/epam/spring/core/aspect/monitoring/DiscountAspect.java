package com.epam.spring.core.aspect.monitoring;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.statistics.IDiscountAspectDao;
import com.epam.spring.core.domain.statistics.DiscountStatistics;
import com.epam.spring.core.domain.user.User;

/**
 * @author alehstruneuski
 */
@Aspect
@Component
public class DiscountAspect {
	
	private static final String LUCKY_DISCOUNT = "0.50";
	private static final String BIRTHDAY_DISCOUNT = "0.05";
	
	@Autowired
	private IDiscountAspectDao discountAspectDao;
	
    @AfterReturning(pointcut = "execution(* com.epam.spring.core.service.IDiscountService.getDiscount(..))", 
    		returning = "retVal")
    public void accessDiscount(JoinPoint joinPoint, Object retVal) {
    	User user = (User) joinPoint.getArgs()[0];
    	Long userId = user.getId();
    	Double discount = (Double) retVal;
    	String discountStr = String.valueOf(discount);
    	
    	boolean isDiscountToReport = Arrays.asList(LUCKY_DISCOUNT, BIRTHDAY_DISCOUNT).contains(discountStr);
    	if (isDiscountToReport) {
        	DiscountStatistics discountStatistics = discountAspectDao.getStatisticsById(userId);
    	  	if (discountStatistics != null) {
    	  		updateData(discountStr, discountStatistics);
    	  		discountAspectDao.updateStatistics(discountStatistics);
    	  	} else {
    	  		DiscountStatistics discountStatisticsNew = new DiscountStatistics();
    	  		discountStatisticsNew.setId(userId);
    	  		updateData(discountStr, discountStatisticsNew);
    	  		discountAspectDao.insertStatistics(discountStatisticsNew);	
    	  	}
    	}
    }
    
    private void updateData(String discount, DiscountStatistics discountStatistics) {
    	switch (discount) {
    		case BIRTHDAY_DISCOUNT:
    			discountStatistics.increaseNumberOfBirthdayDiscount();
    			break;
    		case LUCKY_DISCOUNT:
    			discountStatistics.increaseNumberOfLuckyDiscount();
    			break;
    	}
    }
    
}
