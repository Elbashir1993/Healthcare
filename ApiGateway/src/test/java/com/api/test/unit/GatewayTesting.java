package com.api.test.unit;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.ExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.api.ApiGateway;
import com.api.domain.AuthenticationResponse;
import com.api.repo.UserRepo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import reactor.core.publisher.Flux;

//import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ObjectMapper.class, ApiGateway.class})
@ActiveProfiles("test")
//@TestInstance(Lifecycle.PER_CLASS)
class GatewayTesting {
	@Autowired
    private WebTestClient webTestClient;
	//@Autowired
	private static WireMockServer wireMockServer;
	@BeforeAll
	 static void setUpBeforeClass() throws Exception {
		//Configuration for wiremock server
		/*wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());*/
		wireMockServer = new WireMockServer(
		        new WireMockConfiguration().port(7071)
		    );
		wireMockServer.start();
			WireMock.configureFor("localhost", 7071);
			
			//setupStub();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		wireMockServer.stop();
	}

	@BeforeEach
	void setUp() throws Exception {
	//	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	private String defaultUser = ("this is default response");
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
		 wireMockServer.stubFor(
				 WireMock.get("/user/1")
	                .willReturn(WireMock.aResponse()
	                		.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
	                		.withBody(defaultUser)));
	                		
	                		
	                
	}
	@Test
	void gatewayIntegrationTest() throws Exception {
		// perform a request to get the User
		System.out.println(wireMockServer.baseUrl());
		if(wireMockServer.isRunning()) {
			System.out.println("server is running");
		}else {
			System.out.println("server is not running!");
		}
		
		wireMockServer.stubFor(
				 WireMock.get(urlPathMatching("/testApi"))
	                .willReturn(WireMock.aResponse()
	                		.withStatus(200)
	                		//.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
	                		.withBody(defaultUser)));
		final String link = "http://localhost:8009/testApi";
				this.webTestClient
						.get()
						.uri(link)
						
						// if jwt expired, run authentication test, and copy the jwt from the terminal
						.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MTg0MjUyNSwiZXhwIjoxNjYxODYwNTI1fQ.jDJmGQ0_zs9xdOSXeqGpLle-fDzPnNLzO6s6Lz1akd4")
						.accept(MediaType.APPLICATION_JSON)
						.exchange()
						.expectStatus().isOk();
				if(wireMockServer.isRunning()) {
					System.out.println("server is still running");
				}else {
					System.out.println("server is not running!");
				}
					
//.andDo(print())
						
						//.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(defaultUser.getId()))
						//.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(defaultUser.getUsername()));
	
	
	}
	
	@Test 
	  @DisplayName("TEST AUTHENTICATION")
	  void testAuthentication() throws Exception{ 
		com.api.domain.AuthenticationRequest defaultAuth = new com.api.domain.AuthenticationRequest("admin", "admin");
		  final String link = "/authenticate"; 
		  //JsonContent<AuthenticationRequest> obj = this.json.write(defaultAuth); 
		  System.out.println(objectMapper.writeValueAsString(defaultAuth));
		  // mock the external service
		  ExchangeResult result = this.webTestClient
				  .post()
				  .uri(link)
				  .accept(MediaType.APPLICATION_JSON) 
				  .contentType(MediaType.APPLICATION_JSON)
				  .header("mode", "cors")
				  .body(BodyInserters.fromObject(defaultAuth))
				  .exchange()
				  .expectBody()
				  .returnResult();
				  
		  		  
		  //Flux<AuthenticationResponse> response = result.getResponseBody();
		  System.out.println("from response: "+result);
		  //AuthenticationResponse authRespo = objectMapper.readValue(jwt, AuthenticationResponse.class);
			/*
			 * JsonNode root = objectMapper.readTree(response); JsonNode jwt =
			 * root.get("jwt"); System.out.println("jwt deserlized: "+jwt);
			 */
		  

	  }
}
