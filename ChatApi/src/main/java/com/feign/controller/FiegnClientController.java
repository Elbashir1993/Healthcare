package com.feign.controller;
import java.util.List;

import javax.cache.Cache;
import javax.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreaker;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.feign.domain.AuthenticationRequest;
import com.feign.domain.AuthenticationResponse;
import com.feign.domain.FiegnClientDomain;
import com.feign.domain.User;
import com.feign.service.ServiceForTesting;
import com.feign.util.JwtTokenUtil;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@CrossOrigin
@RequestMapping("/staffs")
public class FiegnClientController {
	@Autowired
	private FiegnClientDomain fc;
	@Autowired
	CacheManager cm;
	@Autowired
	AuthenticationManager authManager;
	@Autowired 
	UserDetailsService userDetailsService;
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	// testing service
	
	private String result;
	/***
	 * Authenticate the api
	 * @param authReq
	 * @return
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception{
		try {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
				);
		}catch(BadCredentialsException e) {
			throw new Exception("incorrect info!!");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authReq.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		// print jwt
		System.out.println(jwt);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "fallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "fallback")
	@Retry(name = "appointment")	
	@GetMapping("/testport")
	
	public User getStaffs() {
		System.out.println("entered testPort");
		long l = 1;
		try{
			System.out.print("getting result from cached data...");
			return (User) cm.getCache("customerCache").get(l);
		}catch(NullPointerException e) {
			System.out.print(e.getMessage());
			System.out.print("caching data...");
			cm.getCache("customerCache").put(l, new User());
		}
		
		System.out.print("getting result from server...");
		return null;
	}
	
	public String fallback(CallNotPermittedException e) {
		return ("server is down! please try again after some time");
	}
	public String fallback(Exception e) {
		
		long l = 1;
		return "cached: "+getCachedData(l);
	}
	
	public String getCachedData(Long i)  {
			try{
				return cm.getCache("customerCache").get(i).toString();
			}catch(NullPointerException e) {
				System.out.print(e.getMessage());
				return "try to refresh the browser";
			}
	}
	
	
	@GetMapping("/admin")
	public String getAdmin() {
		return "this is accessed by admins only";
	}
	
	 
}
