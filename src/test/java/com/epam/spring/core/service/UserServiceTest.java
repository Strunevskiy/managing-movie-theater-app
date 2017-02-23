package com.epam.spring.core.service;

import java.util.Date;

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
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IUserService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	private static final String TEST_USER_FIRST_NAME = "Aleh";
	private static final String TEST_USER_LAST_NAME = "Struneuski";
	private static final String TEST_USER_EMAIL = "aleh_struneuski@epam.com";
	private static final DateTime TEST_USER_BIRTHDAY_DATE_TIME = new DateTime(1993, 8, 12, 10, 29);
	private static final Date TEST_USER_BIRTHDAY = TEST_USER_BIRTHDAY_DATE_TIME.toDate();
	
	@Autowired
	private IUserService userService;

	private User expectedUser;
	
	@BeforeClass
	public void initTest() {
		expectedUser = new User();
		expectedUser.setFirstName(TEST_USER_FIRST_NAME);
		expectedUser.setLastName(TEST_USER_LAST_NAME);
		expectedUser.setEmail(TEST_USER_EMAIL);
		expectedUser.setBirthday(TEST_USER_BIRTHDAY);
	}
	
	@Rollback
	@Test(description = "save() and getById()")
	public void userServiceSaveTest(){
		User persistedUser = userService.save(expectedUser);		
		Assert.assertTrue(userService.getAll().size() == 1);
		Assert.assertEquals(persistedUser.getId(), userService.getUserByEmail(TEST_USER_EMAIL).getId());
	}
		
}
