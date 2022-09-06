package com.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.api.domain.MyUserDetails;
import com.api.domain.User;
import com.api.service.MyUserDetailsService;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebSecurity
@EnableWebFluxSecurity
@CrossOrigin
public class SecurityConfig extends WebSecurityConfigurerAdapter implements ReactiveAuthenticationManager {

	@Autowired
	ReactiveUserDetailsService userDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	JwtRequestFilterWebFlux jwtRequestFilterWebFlux;
	/*
	 * password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	
	/**
	 * Authorization
	 * 
	 * @return
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests()

				.antMatchers("/authenticate").permitAll()

				/*
				 * .antMatchers("/testport").hasRole("ADMIN")
				 * .antMatchers("/user").hasAnyRole("USER", "ADMIN")
				 * .antMatchers("/admin").hasRole("ADMIN") .antMatchers("/**").hasRole("ADMIN")
				 */
				// .antMatchers("/admin").hasAnyRole("ADMIN")
				.anyRequest().authenticated();

		http.csrf().disable();
		http.cors();
		http.formLogin();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { // TODO Auto-generated method stub
	 * auth.userDetailsService(userDetailsService); }
	 */
	/*
	 * @Override
	 * 
	 * @Bean
	 * 
	 * @Primary protected AuthenticationManager authenticationManager() throws
	 * Exception { // TODO Auto-generated method stub
	 * 
	 * return super.authenticationManager(); }
	 */
	
	  @Bean public SecurityWebFilterChain
	  springSecurityFilterChain(ServerHttpSecurity http) { http .csrf(csrf ->
	  csrf.disable());
	  
		
		  http .authorizeExchange() .pathMatchers("/authenticate").permitAll()
		  .pathMatchers("/testApi").hasRole("ADMIN")
					.anyExchange().authenticated() /*
													 * .and() .addFilterBefore(new JwtRequestFilter(),
													 * SecurityWebFiltersOrder.HTTP_BASIC)
													 */
			.and()
			.httpBasic().and()
		  .formLogin();
		  //http.fil(jwtRequestFilterWebFlux, SecurityWebFiltersOrder.AUTHENTICATION);
		 
	  return http.build(); 
	  }
	 

	@Override

	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
	
	


	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		// TODO Auto-generated method stub
		return null;
	}




}
