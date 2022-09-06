package com.feign.test.integeration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.feign.controller.FiegnClientController;

import com.feign.domain.User;
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
@Import({ObjectMapper.class, FiegnClientController.class})
@ActiveProfiles("test")
//@TestInstance(Lifecycle.PER_CLASS)

class GatewayTesting {
	
	private WebSocketStompClient webSocketStompClient;
	
	private String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MjEzNTgxNywiZXhwIjoxNjYyMTUzODE3fQ.nDezqjgzSBhrbWjlY-EuWOPcDSVHG_TIiHyhoF1Fb1U";
	// used to mock external server
	//@Autowired
	//private static WireMockServer wireMockServer;
	
	@BeforeAll
	 static void setUpBeforeClass() throws Exception {
		//Configuration for wiremock server
		/*wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());*/
		/*
		 * wireMockServer = new WireMockServer( new WireMockConfiguration().port(7070)
		 * ); wireMockServer.start(); WireMock.configureFor("localhost", 7070);
		 */
			//setupStub();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//wireMockServer.stop();
	}

	@BeforeEach
	void setUp() throws Exception {
		this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
			    List.of(new WebSocketTransport(new StandardWebSocketClient()))));
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	/***************************************************************************************
	 * 
	 * ******************************* Staff controller Testing *****************************
	 *
	 */
	@Test
	void chatConnectionTest() throws Exception {
		StompSession session = webSocketStompClient
				  .connect(String.format("ws://localhost:%d/ws-endpoint", 8002), new StompSessionHandlerAdapter() {
				  })
				  .get(1, SECONDS);
	}
	
	
	
}
