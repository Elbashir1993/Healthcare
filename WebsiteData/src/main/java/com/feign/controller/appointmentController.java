package com.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feign.domain.AppointWrapper;
import com.feign.domain.FiegnClientDomain;
import com.feign.domain.ResponseAppointWrapper;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@RequestMapping("/appointment")
@CrossOrigin
public class appointmentController {
	
	@Autowired
	FiegnClientDomain proxey;
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getAllfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getAllfallback")
	@Retry(name = "appointment")
	@GetMapping("/all")
	public ResponseEntity<List<ResponseAppointWrapper>> getAll(Pageable pageable){
		
		ResponseEntity<List<ResponseAppointWrapper>> apwl = proxey.getAllTheAppoint(pageable);
		for(ResponseAppointWrapper rapw: apwl.getBody()) {
			int staffId = rapw.getStaff().getId();
			System.out.println(staffId);
		}
		return apwl;
	}
	public ResponseEntity<List<ResponseAppointWrapper>> getAllfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<List<ResponseAppointWrapper>> getAllfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "createAppointfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "createAppointfallback")
	@Retry(name = "appointment")
	@PostMapping("/save")
	public ResponseEntity<AppointWrapper> createAppoint(@RequestBody AppointWrapper apw){
		System.out.println(proxey.createAppoint(apw).getBody().getStaffId());
		return proxey.createAppoint(apw);
	}
	
	public ResponseEntity<AppointWrapper> createAppointfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<AppointWrapper> createAppointfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
}
