package com.feign.controller;



import javax.cache.Cache;
import javax.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreaker;
import com.feign.domain.FiegnClientDomain;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@RequestMapping("/")
public class FiegnClientController {
	@Autowired
	private FiegnClientDomain fc;
	@Autowired
	CacheManager cm;
	
	private String result;
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "fallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "fallback")
	@Retry(name = "appointment")	
	@GetMapping("/testport")
	
	public String getStaffs() {
		
		long l = 1;
		try{
			System.out.print("getting result from cached data...");
			return cm.getCache("customerCache").get(l).toString();
		}catch(NullPointerException e) {
			System.out.print(e.getMessage());
			System.out.print("caching data...");
			cm.getCache("customerCache").put(l, fc.getPort());
		}
		
		System.out.print("getting result from server...");
		return fc.getPort();
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
	@GetMapping("/user")
	public String getUser() {
		return "this is accessed by users and admins";
	}
	@GetMapping("/admin")
	public String getAdmin() {
		return "this is accessed by admins only";
	}

}
