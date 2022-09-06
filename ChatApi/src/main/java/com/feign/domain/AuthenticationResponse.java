package com.feign.domain;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
	private static final long serialVersionUID = 5085165626007488L;
	private final String jwt;

	public AuthenticationResponse(String jwt) {
		
		this.jwt = jwt;
	}

	/**
	 * @return the jwt
	 */
	public String getJwt() {
		return jwt;
	}
	
}
