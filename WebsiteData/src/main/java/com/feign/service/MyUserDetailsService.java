package com.feign.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.feign.domain.MyUserDetails;
import com.feign.domain.User;
import com.feign.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Optional<User> user = userRepo.findByUsername(username);
		//System.out.print("user: "+username+" pass: "+user.map(MyUserDetails::new).get().getPassword());
		//user.orElseThrow(()->new UsernameNotFoundException("user not found with the name: "+username));
		return new MyUserDetails(new User(1, "admin", "admin", "ROLE_USER", true));
	}
	// for test purpose
	public User getUser(String username) {
		return userRepo.findUserByUsername(username);
	}

}
