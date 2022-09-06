package com.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feign.domain.User;
import com.feign.service.ServiceForTesting;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestingController {
	// testing service
		@Autowired
		ServiceForTesting serviceForTesting;
		
		@CrossOrigin
		@GetMapping("/usertest/{name}")
		public String getUser(@PathVariable String name) {
			return serviceForTesting.getUser(name);
			//return new User(1, name, "user", "ROLE_USER",true);
		}
		@GetMapping("/admintest")
		public String getAdmin() {
			return serviceForTesting.getAdmin();
		}
}
