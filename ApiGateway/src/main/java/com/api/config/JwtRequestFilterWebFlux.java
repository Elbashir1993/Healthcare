package com.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.web.server.WebFilterChain;

import com.api.service.MyUserDetailsService;
import com.api.util.JwtTokenUtil;

import reactor.core.publisher.Mono;
@Component
public class JwtRequestFilterWebFlux implements  GatewayFilter{
	@Autowired
	private JwtTokenUtil jwtUtil;
	@Autowired
	MyUserDetailsService myUserDetailsSerice;
	
	/*
	 * @Override public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain
	 * chain) { // TODO Auto-generated method stub final String autherizationHeader
	 * = exchange.getRequest().getHeaders().getFirst("Authorization");
	 * 
	 * String username = null; String jwt =null;
	 * System.out.println("authorization "+autherizationHeader+" end...");
	 * if(autherizationHeader != null && autherizationHeader.startsWith("Bearer "))
	 * { System.out.println("************condition 1********"); jwt =
	 * autherizationHeader.substring(7); username = new
	 * JwtTokenUtil().getUsernameFromToken(jwt);
	 * System.out.println("username: "+username); } if(username != null &&
	 * SecurityContextHolder.getContext().getAuthentication() == null) {
	 * System.out.println("************condition 2********"); UserDetails
	 * userDetails = new MyUserDetailsService().loadUserByUsername(username); if(new
	 * JwtTokenUtil().validateToken(jwt, userDetails)) {
	 * System.out.println("************condition 3********");
	 * UsernamePasswordAuthenticationToken usernamPasswordAuthenticationToken = new
	 * UsernamePasswordAuthenticationToken(userDetails,null,
	 * userDetails.getAuthorities());
	 * SecurityContextHolder.getContext().setAuthentication(
	 * usernamPasswordAuthenticationToken);
	 * System.out.println(userDetails.getUsername()+" authority"+
	 * userDetails.getAuthorities()); } } return chain.filter(exchange); }
	 */

	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return null;
		// TODO Auto-generated method stub 
		/*
		 * final String autherizationHeader =
		 * exchange.getRequest().getHeaders().getFirst("Authorization");
		 * 
		 * String username = null; String jwt =null;
		 * System.out.println("authorization "+autherizationHeader+" end...");
		 * if(autherizationHeader != null && autherizationHeader.startsWith("Bearer "))
		 * { System.out.println("************condition 1********"); jwt =
		 * autherizationHeader.substring(7); username = new
		 * JwtTokenUtil().getUsernameFromToken(jwt);
		 * System.out.println("username: "+username); } if(username != null &&
		 * SecurityContextHolder.getContext().getAuthentication() == null) {
		 * System.out.println("************condition 2********"); UserDetails
		 * userDetails = new MyUserDetailsService().loadUserByUsername(username); if(new
		 * JwtTokenUtil().validateToken(jwt, userDetails)) {
		 * System.out.println("************condition 3********");
		 * UsernamePasswordAuthenticationToken usernamPasswordAuthenticationToken = new
		 * UsernamePasswordAuthenticationToken(userDetails,null,
		 * userDetails.getAuthorities());
		 * SecurityContextHolder.getContext().setAuthentication(
		 * usernamPasswordAuthenticationToken);
		 * System.out.println(userDetails.getUsername()+" authority"+
		 * userDetails.getAuthorities()); } } return chain.filter(exchange);
		 */
	}

}
