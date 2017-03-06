package com.epam.spring.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.spring.core.dao.repository.UserRepository;
import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.domain.user.Role;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IUserService;

/**
 * @author alehstruneuski
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public User save(User object) {
		object.setRoles(new HashSet<Role>(Arrays.asList(Role.RESGISTERED_USER)));
		object.setPassword(passwordEncoder.encode(object.getPassword()));
		return userRepository.save(object);
	}

	@Override
	public void remove(User object) {
		userRepository.delete(object.getId());
	}

	@Override
	public User getById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public Collection<User> getAll() {
		List<User> targetCollection = new ArrayList<User>();
		Iterable<User> eventIterator = userRepository.findAll();
		CollectionUtils.addAll(targetCollection, eventIterator.iterator());
		return targetCollection;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
}
