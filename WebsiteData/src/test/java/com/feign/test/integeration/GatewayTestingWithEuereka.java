package com.feign.test.integeration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.feign.domain.User;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

//import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ObjectMapper.class)
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
//@ContextConfiguration(initializers = {EurekaContainerConfig.Initializer.class})
@ActiveProfiles("test")
//@TestInstance(Lifecycle.PER_CLASS)
class GatewayTestingWithEuereka {
	@Autowired
	private MockMvc mockMvc;
	//@Autowired
	private static WireMockServer wireMockServer;
	@BeforeAll
	 static void setUpBeforeClass() throws Exception {
		//Configuration for wiremock server
		/*wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());*/
		wireMockServer = new WireMockServer(
		        new WireMockConfiguration().port(7070)
		    );
		wireMockServer.start();
			WireMock.configureFor("localhost", 7070);
			//setupStub();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		wireMockServer.stop();
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
		 wireMockServer.stubFor(
				 WireMock.get("/user/1")
	                .willReturn(WireMock.aResponse()
	                		.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
	                		.withBody(objectMapper.writeValueAsString(defaultUser))));
	}
	@Test
	void gatewayIntegrationTest() throws Exception {
		// perform a request to get the User
		System.out.println(wireMockServer.baseUrl());
		assertTrue(wireMockServer.isRunning());
		wireMockServer.stubFor(
				 WireMock.get("/user/1")
	                .willReturn(WireMock.aResponse()
	      
	                		.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
	                		.withBody(objectMapper.writeValueAsString(defaultUser))));
		final String link = "/proxyuser/1";
				this.mockMvc.perform(MockMvcRequestBuilders
						.get(link)
						// if jwt expired, run authentication test, and copy the jwt from the terminal
						.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MDU0NjI4NiwiZXhwIjoxNjYwNTY0Mjg2fQ.lbTptB31Hm69jZOL9iB0wjbQ1ADxQpr1c7erLiCpUgU")
						.accept(MediaType.APPLICATION_JSON))
						//.andDo(print())
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(defaultUser.getId()))
						.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(defaultUser.getUsername()));
	}

}
