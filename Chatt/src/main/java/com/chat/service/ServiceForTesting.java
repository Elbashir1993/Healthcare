package com.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.domain.User;
import com.chat.repo.UserRepo;
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
