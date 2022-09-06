package com.feign.test.components;


/****Notice that we do not mock our repository interface, 
service or controllers to achieve our goal. We are 
using real objects so that we verify the whole functionality
of our microservice i.e all our system componets can really 
communicate.****/

import static org.junit.jupiter.api.Assertions.*;

import javax.cache.CacheManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.feign.config.JwtRequestFilter;
import com.feign.config.SecurityConfig;
import com.feign.controller.FiegnClientController;
import com.feign.controller.TestingController;
import com.feign.domain.AuthenticationRequest;
import com.feign.domain.AuthenticationResponse;
import com.feign.domain.FiegnClientDomain;
import com.feign.domain.User;
import com.feign.repo.UserRepo;
import com.feign.service.MyUserDetailsService;
import com.feign.util.JwtTokenUtil;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles({"componenttest"})
//@JsonTest
//@Import({SecurityConfig.class, MyUserDetailsService.class, JwtRequestFilter.class, JwtTokenUtil.class})
@Import(ObjectMapper.class)
//@WebMvcTest(FiegnClientController.class) 
//@ContextConfiguration(classes = {FiegnClientController.class})
class FiegnApplicationComponentTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	/*
	 * @Autowired private UserRepo userRepo;
	 */
	@MockBean
	private FiegnClientDomain proxy;
	@MockBean
	private CacheManager cacheManager;
	/*
	 * @MockBean private AuthenticationManager authenticationManager;
	 */
	// port
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	private User defaultUser = new User(1, "username", "userpass", "ROLE_USER", true);
	
	@Test
	@DisplayName("TEST THE PROXEY")
	void getPortFromExternalService() throws Exception {
		
		final String link = "/proxytest";
		
		  // mock the external service 
		Mockito .when(this.proxy.getUser())
		  .thenReturn(defaultUser);
		 
		
		// perform a request to get the User
		this.mockMvc.perform(MockMvcRequestBuilders
				.get(link)
				// if jwt expired, run authentication test, and copy the jwt from the terminal
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MTkzMjkzMCwiZXhwIjoxNjYxOTUwOTMwfQ.Fy3CrJTWaeSGHa9urbReJmT8G8JL6EfBbKnZjJ0OpOo")
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(defaultUser.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(defaultUser.getUsername()));		
	}

}
