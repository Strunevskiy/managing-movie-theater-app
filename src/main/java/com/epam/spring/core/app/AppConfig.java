package com.epam.spring.core.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.epam.spring.core.app.configs.DataAccessConfig;
import com.epam.spring.core.service.discount.IDiscount;
import com.epam.spring.core.service.discount.impl.BirthdayDiscountImpl;
import com.epam.spring.core.service.discount.impl.LuckyDiscountStrategyImpl;

@Configuration
@ComponentScan("com.epam.spring.core")
@Import({ DataAccessConfig.class })
@EnableAspectJAutoProxy
public class AppConfig {
	  
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholder() {
	    return new PropertySourcesPlaceholderConfigurer();  
	}

	@Bean
	public List<IDiscount> bunchOfDiscounts() {
		List<IDiscount> bunchOfDiscounts = new ArrayList<>();
		bunchOfDiscounts.add(new BirthdayDiscountImpl());
		bunchOfDiscounts.add(new LuckyDiscountStrategyImpl());
		return bunchOfDiscounts;
	}
	
}
