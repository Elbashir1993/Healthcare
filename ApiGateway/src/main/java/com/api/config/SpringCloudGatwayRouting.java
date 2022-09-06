package com.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.api.service.MyUserDetailsService;
import com.api.util.JwtTokenUtil;

/******************** Configuring the gateway routings **********************
 * 
 * @author Elbas
 *
 */
@Configuration
public class SpringCloudGatwayRouting {
	@Autowired
	private JwtTokenUtil jwtUtil;
	@Autowired
	MyUserDetailsService myUserDetailsSerice;
	  @Bean public RouteLocator configureRoute(RouteLocatorBuilder builder) { //
	  //FIEGNSERVER is the fiegn client server name registered in eureka server
	  return builder.routes()
			  .route("Chat_Service", r->r.path("/testApi").uri("http://localhost:8002"))
			  //static routing fortesting the api 
			  //.route("fiegnClient",r->r.path("/dynamic/**").uri("lb://FIEGNSERVER")) //dynamic routing 
			  
	  		.route(r -> r.path("/testApi")
				.filters(f -> f.filter(new JwtRequestFilterWebFlux())
						)
				.uri("http://localhost:8002")
			)
	  		.build();
	  }
	 
}
