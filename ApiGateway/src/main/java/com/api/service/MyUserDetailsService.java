package com.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.api.domain.MyUserDetails;
import com.api.domain.User;
import com.api.repo.UserRepo;

import reactor.core.publisher.Mono;

@Service
@CrossOrigin
public class MyUserDetailsService implements ReactiveUserDetailsService {
	@Autowired
	private UserRepo userRepo;

	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException {
	 * 
	 * //Optional<User> user = userRepo.findByUsername(username);
	 * //System.out.print("user: "+username+" pass: "+user.map(MyUserDetails::new).
	 * get().getPassword()); //user.orElseThrow(()->new
	 * UsernameNotFoundException("user not found with the name: "+username)); return
	 * new MyUserDetails(new User(1, "admin", "admin", "ROLE_USER", true)); }
	 */
	// for test purpose
	public User getUser(String username) {
		return userRepo.findUserByUsername(username);
	}
	@Override
	public Mono<UserDetails> findByUsername(String username) {
		// TODO Auto-generated method stub
		UserDetails user = new MyUserDetails(new User(1, "admin", "admin", "ROLE_USER", true));
		return Mono.just(user);
	}

}
