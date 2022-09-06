package com.api.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.api.service.MyUserDetailsService;
import com.api.util.JwtTokenUtil;

import reactor.core.publisher.Mono;


@Component
@CrossOrigin
public class JwtRequestFilter extends OncePerRequestFilter{
	@Autowired
	private JwtTokenUtil jwtUtil;
	@Autowired
	MyUserDetailsService myUserDetailsSerice;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String autherizationHeader = request.getHeader("Authorization");
		/*
		 * if(request != null) { System.out.print("request is null"); } final String r =
		 * request.getHeader("Content-Type");
		 */
		//System.out.print(r);
		String username = null;
		String jwt = null;
		System.out.print("authorization "+autherizationHeader+" end...");
		if(autherizationHeader != null && autherizationHeader.startsWith("Bearer ")) {
			jwt = autherizationHeader.substring(7);
			username = jwtUtil.getUsernameFromToken(jwt);
		}
		if(username != null &&  SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = (UserDetails) this.myUserDetailsSerice.findByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamPasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamPasswordAuthenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}
	/*
	 * @Override public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain
	 * chain) { // TODO Auto-generated method stub final String autherizationHeader
	 * = exchange.getRequest().getHeaders().getFirst("Authorization");
	 * System.out.println(autherizationHeader); String username = null; String jwt =
	 * null; System.out.print("authorization "+autherizationHeader+" end...");
	 * if(autherizationHeader != null && autherizationHeader.startsWith("Bearer "))
	 * { jwt = autherizationHeader.substring(7); username = new
	 * JwtTokenUtil().getUsernameFromToken(jwt); } if(username != null &&
	 * SecurityContextHolder.getContext().getAuthentication() == null) { UserDetails
	 * userDetails = new MyUserDetailsService().loadUserByUsername(username); if(new
	 * JwtTokenUtil().validateToken(jwt, userDetails)) {
	 * UsernamePasswordAuthenticationToken usernamPasswordAuthenticationToken = new
	 * UsernamePasswordAuthenticationToken(userDetails,null,
	 * userDetails.getAuthorities());
	 * 
	 * 
	 * SecurityContextHolder.getContext().setAuthentication(
	 * usernamPasswordAuthenticationToken);
	 * System.out.println(userDetails.getUsername()+" authority"+
	 * userDetails.getAuthorities()); } } return chain.filter(exchange); }
	 */
	
}
