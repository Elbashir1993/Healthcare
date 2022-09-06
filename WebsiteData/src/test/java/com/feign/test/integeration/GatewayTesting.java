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
import org.springframework.mock.web.MockMultipartFile;
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

import com.feign.controller.FiegnClientController;
import com.feign.domain.AppointWrapper;
import com.feign.domain.Appointment;
import com.feign.domain.Charges;
import com.feign.domain.ContactInfo;
import com.feign.domain.Duration;
import com.feign.domain.PjContainer;
import com.feign.domain.PreviousJobs;
import com.feign.domain.ScheduleUpdateRequestMapper;
import com.feign.domain.Staff;
import com.feign.domain.StaffInfo;
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
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	/***************************************************************************************
	 * 
	 * ******************************* Staff controller Testing *****************************
	 *
	 */
	
	
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	class StaffControllerTesting {
		@Autowired
		private MockMvc mockMvc;
		private User defaultUser = new User(1, "username", "userpass", "ROLE_USER", true);
		List<StaffInfo> defaultStaffInfoLi = new ArrayList<StaffInfo>();
		private Staff defaultStaff = new Staff( "iyi", "jlj", "jll", "jlj", "fs", "fsaf");
		//private Staff defaultStaffNoId = new Staff( "testStaff", "jlj", "jll", "jlj", "fs", "fsaf");
		@Autowired
	    private ObjectMapper objectMapper;
		private int staffId;
		// mock the fiegn client url
		/*
		 * @DynamicPropertySource static void
		 * overrideWebClientBaseUrl(DynamicPropertyRegistry registry) {
		 * registry.add("APPOINTMENT-AND-STAFF-SERVICE", wireMockServer::baseUrl); }
		 */

		/******************* get all staffs **********************/
		@Test
		@DisplayName("Should Fail if fiegn server not setup")
		// remeber to replace fiegn server url with the test base url
		
		void gatewayIntegrationTest() throws Exception {
			
			// perform a request to get the User
			// enable wireMock to mock the external server by the code below
			/*
			 * System.out.println(wireMockServer.baseUrl());
			 * assertTrue(wireMockServer.isRunning()); wireMockServer.stubFor(
			 * WireMock.get("/staffs?page=0&size=20") .willReturn(WireMock.aResponse()
			 * 
			 * .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			 * .withBody(objectMapper.writeValueAsString(defaultStaffInfoLi))));
			 */
			// testing real service
			final String link = "/staffs/all";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
							
			}
		
			/****************** save staff ****************/
			@Test
			@Order(1)
			public void saveStaffTest() throws Exception {
				final String link = "/staffs/save";
				System.out.println(objectMapper.writeValueAsBytes(defaultStaff));
				MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
						.post(link)
						
						// if jwt expired, run authentication test, and copy the jwt from the terminal
						.header("Authorization", "Bearer "+jwt)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(defaultStaff)))
						.andExpect(MockMvcResultMatchers.status().isCreated())
						.andReturn();
				String response = result.getResponse().getContentAsString();
				System.out.println("from response: "+response); //
				//AuthenticationResponse  authRespo = objectMapper.readValue(jwt, AuthenticationResponse.class);
				JsonNode root = objectMapper.readTree(response); 
				JsonNode id = root.get("id"); 
				staffId = id.asInt();
				System.out.println("id is: "+id);
			}
			
			/********************* update staff ******************/
			@Test
			@Order(2)
			public void updateStaffTest() throws Exception {
				final String link = "/staffs/update/1098";
				System.out.println(objectMapper.writeValueAsBytes(defaultStaff));
				this.mockMvc.perform(MockMvcRequestBuilders
						.post(link)
						
						// if jwt expired, run authentication test, and copy the jwt from the terminal
						.header("Authorization", "Bearer "+jwt)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(defaultStaff)))
						//.andDo(print())
						.andExpect(MockMvcResultMatchers.status().isOk());
			}
			
			/********************* delete staff ******************/
			@Test
			@Order(3)
			public void deleteStaffTest() throws Exception {
				final String link = "/staffs/delete/1098";
				System.out.println(objectMapper.writeValueAsBytes(defaultStaff));
				this.mockMvc.perform(MockMvcRequestBuilders
						.post(link)
						
						// if jwt expired, run authentication test, and copy the jwt from the terminal
						.header("Authorization", "Bearer "+jwt)
						.accept(MediaType.APPLICATION_JSON))
						//.andDo(print())
						.andExpect(MockMvcResultMatchers.status().isOk());
			}
			/***********************
			 * 
			 * Certificates methods testing
			 *
			 */
			@Nested
			@TestMethodOrder(OrderAnnotation.class)
			class certificatesTesting {
				@Autowired
				private MockMvc mockMvc;
				private User defaultUser = new User(1, "username", "userpass", "ROLE_USER", true);
				List<StaffInfo> defaultStaffInfoLi = new ArrayList<StaffInfo>();
				private Staff defaultStaff = new Staff( "iyi", "jlj", "jll", "jlj", "fs", "fsaf");
				//private Staff defaultStaffNoId = new Staff( "testStaff", "jlj", "jll", "jlj", "fs", "fsaf");
				@Autowired
			    private ObjectMapper objectMapper;
				private int staffId;
			
				/******************* save certificate to storage **********************/
				@Test
				@DisplayName("save Certificate to storage")
				void saveCertToStorage() throws Exception {
					
					
					// testing real service
					final URI link = new URI("/staffs/1123/storage/save");
					MockMultipartFile file = new MockMultipartFile(
								"file",
								"hello6.txt",
								MediaType.TEXT_PLAIN_VALUE,
								"Hello, World".getBytes()
							);
							this.mockMvc.perform(MockMvcRequestBuilders
									.fileUpload(link)
									.file(file)
									.accept(MediaType.MULTIPART_FORM_DATA_VALUE)
									.header("Authorization", "Bearer "+jwt))
									.andExpect(MockMvcResultMatchers.status().isOk());
									
				}
				
				
				
				/********************* get certificates list from storage ******************/
				@Test
				@DisplayName("get certificate list name")
				void getAllFromStorage() throws Exception {
					final String link = "/staffs/1123/storage/all";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
				}
				
				/********************* get one file from storage ******************/
				@Test
				@DisplayName("get file by filename from storage")
				void getCharges() throws Exception {
					final String link = "/staffs/1123/storage/hello.txt";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
				}
			}
			
			/*
			 * staff charges
			 */
			@Nested
			@TestMethodOrder(OrderAnnotation.class)
			class chargesTesting {
				@Autowired
				private MockMvc mockMvc;
				
				@Autowired
			    private ObjectMapper objectMapper;
				private int staffId;
				private Charges  ch = new Charges(1000, 2000);
			
				/******************* save Charges **********************/
				@Test
				@DisplayName("save charges")
				void saveChargeToStorage() throws Exception {
					
					
					// testing real service
					final URI link = new URI("/staffs/1123/charges");
							this.mockMvc.perform(MockMvcRequestBuilders
									.post(link)
									.header("Authorization", "Bearer "+jwt)
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON)
									.content(objectMapper.writeValueAsBytes(ch)))
									.andExpect(MockMvcResultMatchers.status().isCreated());
									
				}
				
				
				
				/********************* get charges ******************/
				@Test
				@DisplayName("get charges")
				void getCharges() throws Exception {
					final String link = "/staffs/1123/charges";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
				}
			}
			
			/*
			 * staff previous job
			 */
			@Nested
			@TestMethodOrder(OrderAnnotation.class)
			class pjTesting {
				@Autowired
				private MockMvc mockMvc;
				
				@Autowired
			    private ObjectMapper objectMapper;
				private int staffId;
				private PreviousJobs  pj = new PreviousJobs("position", "org");
				private Duration d = new Duration("start", "end");
				List<Duration> dl = new ArrayList<Duration>();
				
			
				/******************* save Charges **********************/
				@Test
				@DisplayName("save previous jobs")
				void savePj() throws Exception {
					dl.add(d);
					PjContainer pjc = new PjContainer(pj, dl);
					
					// testing real service
					final URI link = new URI("/staffs/1123/previousJobs");
							this.mockMvc.perform(MockMvcRequestBuilders
									.post(link)
									.header("Authorization", "Bearer "+jwt)
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON)
									.content(objectMapper.writeValueAsBytes(pjc)))
									.andExpect(MockMvcResultMatchers.status().isCreated());
									
				}
				
				
				
				/********************* get previous jobs ******************/
				@Test
				@DisplayName("get previous jobs")
				void getCharges() throws Exception {
					final String link = "/staffs/1123/previousJobs";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
				}
			}
			
			
			/*
			 * staff schedule
			 */
			@Nested
			@TestMethodOrder(OrderAnnotation.class)
			class scheduleTesting {
				@Autowired
				private MockMvc mockMvc;
				
				@Autowired
			    private ObjectMapper objectMapper;
				private int staffId;
				private ScheduleUpdateRequestMapper surm = new ScheduleUpdateRequestMapper(1, 0, 1);
				
			
				/******************* update schedule **********************/
				@Test
				@DisplayName("update schedule")
				void savePj() throws Exception {
					
					
					// testing real service
					final URI link = new URI("/staffs/1123/schedule");
							this.mockMvc.perform(MockMvcRequestBuilders
									.post(link)
									.header("Authorization", "Bearer "+jwt)
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON)
									.content(objectMapper.writeValueAsBytes(surm)))
									.andExpect(MockMvcResultMatchers.status().isOk());
									
				}
				
				
				
				/********************* get previous jobs ******************/
				@Test
				@DisplayName("get schedule")
				void getCharges() throws Exception {
					final String link = "/staffs/1123/schedule";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
				}
			}
		}
	
	
	/***************************** Appointment controller test *************************/
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	class AppointControllerTesting {
		@Autowired
		private MockMvc mockMvc;
		private User defaultUser = new User(1, "username", "userpass", "ROLE_USER", true);
		List<StaffInfo> defaultStaffInfoLi = new ArrayList<StaffInfo>();
		private Staff defaultStaff = new Staff( "iyi", "jlj", "jll", "jlj", "fs", "fsaf");
		//private Staff defaultStaffNoId = new Staff( "testStaff", "jlj", "jll", "jlj", "fs", "fsaf");
		@Autowired
	    private ObjectMapper objectMapper;
		private int staffId;
		// mock the fiegn client url
		/*
		 * @DynamicPropertySource static void
		 * overrideWebClientBaseUrl(DynamicPropertyRegistry registry) {
		 * registry.add("APPOINTMENT-AND-STAFF-SERVICE", wireMockServer::baseUrl); }
		 */

		/******************* get all staffs **********************/
		@Test
		@DisplayName("Should Fail if fiegn server not setup")
		// remeber to replace fiegn server url with the test base url
		@Order(1)
		void getAllAppointTest() throws Exception {
			
			// perform a request to get the User
			// enable wireMock to mock the external server by the code below
			/*
			 * System.out.println(wireMockServer.baseUrl());
			 * assertTrue(wireMockServer.isRunning()); wireMockServer.stubFor(
			 * WireMock.get("/staffs?page=0&size=20") .willReturn(WireMock.aResponse()
			 * 
			 * .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			 * .withBody(objectMapper.writeValueAsString(defaultStaffInfoLi))));
			 */
			// testing real service
			final String link = "/appointment/all?page=0&size=20";
					this.mockMvc.perform(MockMvcRequestBuilders
							.get(link)
							// if jwt expired, run authentication test, and copy the jwt from the terminal
							.header("Authorization", "Bearer "+jwt)
							.accept(MediaType.APPLICATION_JSON))
							//.andDo(print())
							.andExpect(MockMvcResultMatchers.status().isOk());
							
		}
		
		/****************** save appointment ****************/
		@Test
		@Order(2)
		public void saveApointTest() throws Exception {
			final String link = "/appointment/save";
			
			Appointment ap = new Appointment(1,"2022-08-01", 0);
			int stafId = 1123;
			List<ContactInfo> cil = new ArrayList<ContactInfo>();
			cil.add(new ContactInfo("firstname","secondName", "email@fds.cjl", "456789", "hohofs"));
			AppointWrapper apw = new AppointWrapper(ap, 1123, cil);
			
			MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
					.post(link)
					
					// if jwt expired, run authentication test, and copy the jwt from the terminal
					.header("Authorization", "Bearer "+jwt)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(apw)))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andReturn();
		}
	}
}
