package com.epam.spring.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.spring.core.domain.user.Role;
import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.service.IUserService;

/**
 * @author alehstruneuski
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private IUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		   
		User user = userService.getUserByEmail(email);	        	        
		if (user == null){
			throw new UsernameNotFoundException("User was not found by userId");
		}
		return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), user.getPassword(), 
	                 true, true, true, true, getGrantedAuthorities(user));
	}
	
	 private List<GrantedAuthority> getGrantedAuthorities(User user){
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();	         
		 for(Role userRole : user.getRoles()){
			 authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole()));     
		 }
		 return authorities;
	 }

}
