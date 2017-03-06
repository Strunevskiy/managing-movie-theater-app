package com.epam.spring.core.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.core.dao.repository.UserAccountRepository;
import com.epam.spring.core.domain.UserAccount;
import com.epam.spring.core.service.IUserAccountService;

@Service
public class UserAccountServiceImpl implements IUserAccountService {
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public UserAccount save(UserAccount object) {
		return userAccountRepository.save(object);
	}

	@Override
	public void remove(UserAccount object) {	
		userAccountRepository.delete(object);
	}

	@Override
	public UserAccount getById(Long id) {
		return userAccountRepository.findOne(id);
	}

	@Override
	public Collection<UserAccount> getAll() {
		return userAccountRepository.findAll();
	}

}
