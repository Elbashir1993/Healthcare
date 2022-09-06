package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.domain.AuthenticationRequest;
import com.api.domain.AuthenticationResponse;
import com.api.util.JwtTokenUtil;
@RestController
@CrossOrigin
@RequestMapping("/")
public class ApiController {

	@Autowired
	ReactiveAuthenticationManager authManager;
	@Autowired 
	ReactiveUserDetailsService userDetailsService;
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	/***
	 * Authenticate the api
	 * @param authReq
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception{
		System.out.println("oipi");
		try {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
				);
		}catch(BadCredentialsException e) {
			throw new Exception("incorrect info!!");
		}
		final UserDetails userDetails = (UserDetails) userDetailsService.findByUsername(authReq.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		// print jwt
		System.out.println(jwt);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	@GetMapping("/admin")
	public String getAdmin() {
		return "this is accessed by admins only";
	}
	
}
