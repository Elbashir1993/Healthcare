package com.chat.test.components;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.messaging.*;
import org.springframework.web.socket.client.*;

import com.chat.config.WebSocketConfig;
import com.chat.controller.ChatController;
import com.chat.domain.ChatMessage;
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
@Import({ObjectMapper.class, WebSocketStompClient.class, WebSocketConfig.class})
@ActiveProfiles("test")
//@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class GatewayTesting {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebSocketStompClient stompClient;
	
	@Mock
	WebSocketClient webSocketClient;
	
	private String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MjE5NDU2NSwiZXhwIjoxNjYyMjEyNTY1fQ.FCLbSMCTrDhg-7Qzzz5nSa6Z8KrFNL5mymuQGZCu06U";
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
		this.stompClient = new WebSocketStompClient(new SockJsClient(
			    List.of(new WebSocketTransport(new StandardWebSocketClient()))));
			
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	
	@Test
	
	void setTheChatRooms() throws Exception {
		// perform a request to get the User
				this.mockMvc.perform(MockMvcRequestBuilders
						.get("/receptionfsdafs/2")
						// if jwt expired, run authentication test, and copy the jwt from the terminal
						.header("Authorization", jwt)
						.accept(MediaType.APPLICATION_JSON))
						//.andDo(print())
						.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	
	void testTheChat() throws InterruptedException, ExecutionException, Exception {
		//StompSession s = stompClient.;
		
		ListenableFuture<StompSession> session = stompClient.connect("ws://localhost:8002/ws", new StompSessionHandlerAdapter() {
		  });
		//assertTrue(session.get().isConnected());
		
		StompSession s = session.get(1, TimeUnit.SECONDS);
		stompClient.setMessageConverter(new SimpleMessageConverter());
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
		s.subscribe("/user/1/queue/messages", new StompFrameHandler() {

			@Override
			public Type getPayloadType(StompHeaders headers) {
				// TODO Auto-generated method stub
				//headers.add("Authorization", jwt);
				return ChatMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				// TODO Auto-generated method stub
				//blockingQueue.add((String) payload);
			}
		});
		
		 // s.send("/app/chat, blockingQueue", "ttest"); wait(1000);
		  assertEquals("Hello, Mike!", blockingQueue.poll());
		 
	
	}

	/***************************************************************************************
	 * 
	 * ******************************* Staff controller Testing *****************************
	 *
	 */
	
	@Configuration
	@EnableAutoConfiguration
	public static class ClientConfig{
		@Bean
		public WebSocketClient webSocketClient() {
			return new SockJsClient(Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient())));
		}
	}
}
