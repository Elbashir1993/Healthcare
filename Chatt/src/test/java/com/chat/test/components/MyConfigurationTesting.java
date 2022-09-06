package com.chat.test.components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.chat.controller.ChatController;

import com.chat.domain.AuthenticationRequest;
import com.chat.domain.AuthenticationResponse;

import com.chat.domain.User;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.jsonwebtoken.io.IOException;

//import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ ObjectMapper.class, ChatController.class })
@ActiveProfiles("test")
//@TestInstance(Lifecycle.PER_CLASS)
class MyConfigurationTesting {
	@Autowired
	CircuitBreakerRegistry cbr;
	@Autowired
	private MockMvc mockMvc;
	// @Autowired
	private static WireMockServer wireMockServer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Configuration for wiremock server
		/*
		 * wireMockServer = new
		 * WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
		 */
		wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));
		wireMockServer.start();
		WireMock.configureFor("localhost", 7070);
		// setupStub();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		wireMockServer.stop();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {

	}

	private User defaultUser = new User(1, "username", "userpass", "ROLE_USER", true);
	
	@Autowired
	private ObjectMapper objectMapper;

	// mock the fiegn client url
	/*
	 * @DynamicPropertySource static void
	 * overrideWebClientBaseUrl(DynamicPropertyRegistry registry) {
	 * registry.add("APPOINTMENT-AND-STAFF-SERVICE", wireMockServer::baseUrl); }
	 */
	// stub function
	public void setupStub() throws JsonProcessingException {
		
		wireMockServer.stubFor(WireMock.get("/user/1")
				.willReturn(WireMock.aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(objectMapper.writeValueAsString(defaultUser))));

	}

	@Test
	@DisplayName("Should Fail if fiegn server not setup")
	// remeber to replace fiegn server url with the test base url

	void gatewayIntegrationTest() throws Exception {
		
		// perform a request to get the User
		System.out.println(wireMockServer.baseUrl());
		assertTrue(wireMockServer.isRunning());
		wireMockServer.stubFor(WireMock.get("/testFiegn").willReturn(WireMock.aResponse()

				.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.withBody(objectMapper.writeValueAsString(defaultUser))));
		final String link = "/proxytest";
		this.mockMvc.perform(MockMvcRequestBuilders.get(link)
				// if jwt expired, run authentication test, and copy the jwt from the terminal
				.header("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MTk2MDYyMSwiZXhwIjoxNjYxOTc4NjIxfQ.aI50Of26LGmf7sSRzxEg9gkM0EKH1M8T5_H2tiCiRvI")
				.accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(defaultUser.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(defaultUser.getUsername()));
	}

	@Test
	@DisplayName("Test port to get port number from external service")

	void getTestPort() throws NullPointerException, CallNotPermittedException, Exception {
		// perform a request to get the User
		System.out.println(wireMockServer.baseUrl());
		assertTrue(wireMockServer.isRunning());
		wireMockServer.stubFor(WireMock.get("/testeurekaport").willReturn(WireMock.aResponse()

				.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withStatus(200)
				.withBody(objectMapper.writeValueAsString(defaultUser))));
		final String link = "/testport";

		try {
			MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(link)

					// if jwt expired, run authentication test, and copy the jwt from the terminal
					.header("Authorization",
							"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MTkxMTU5NSwiZXhwIjoxNjYxOTI5NTk1fQ.Qa2fCtQWwcD-Gry378e_ybif2hExUzPrlwLY4HuWs2s")
					.accept(MediaType.APPLICATION_JSON))
					// .andDo(print())
					.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	@Test
	@DisplayName("Should fail if the circuit breaker not configuiered")
	public void circuitBreakerTesting() {
		io.github.resilience4j.circuitbreaker.CircuitBreaker cb = cbr.getAllCircuitBreakers()
				.filter(c -> c.getName().equalsIgnoreCase("appointment")).get();
		CircuitBreakerConfig cbc = cb.getCircuitBreakerConfig();
		assertEquals(3, cbc.getPermittedNumberOfCallsInHalfOpenState());
	}

	
	  // run authentication test 
	AuthenticationRequest defaultAuth = new AuthenticationRequest("admin", "admin"); 
	//
	//AuthenticationResponse defaultRespo = new AuthenticationResponse();
	  
	  
	  //@Autowired private JacksonTester<AuthenticationRequest> json;
	  
	  
	  
	  @Test
	  
	  @DisplayName("authentication test") void testAuthentication() throws Exception{ 
		  final String link = "/authenticate";
	  //JsonContent<AuthenticationRequest> obj = this.json.write(defaultAuth);
	  //System.out.println(objectMapper.writeValueAsString(defaultAuth)); 
	  // mock the external service 
	  MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders 
			  .post(link)
	  .accept(MediaType.APPLICATION_JSON) 
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(objectMapper.writeValueAsBytes(defaultAuth)))
	  .andExpect(MockMvcResultMatchers.status().isOk()) 
	  .andReturn(); 
	  
	  String response = result.getResponse().getContentAsString();
	  System.out.println("from response: "+response); //
	  //AuthenticationResponse  authRespo = objectMapper.readValue(jwt, AuthenticationResponse.class);
	  JsonNode root = objectMapper.readTree(response); 
	  JsonNode jwt = root.get("jwt"); 
	  System.out.println("jwt deserlized: "+jwt);
	  
	  
	  }
	 
}
