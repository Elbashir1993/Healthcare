package com.feign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feign.domain.User;
import com.feign.repo.UserRepo;
@Service
public class ServiceForTesting {
	@Autowired
	private UserRepo userRepo;
	
	// for test purpose
	// get user from database
	public String getUser(String username) {
		return "";
	}
	public String getAdmin() {
		return "";
	}
}
