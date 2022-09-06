package com.fiegn.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.jgit.api.CheckoutResult.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.feign.FeignApplication;
import com.feign.config.JwtRequestFilter;
import com.feign.config.SecurityConfig;
import com.feign.controller.FiegnClientController;
import com.feign.controller.TestingController;
import com.feign.domain.User;
import com.feign.repo.UserRepo;
import com.feign.service.MyUserDetailsService;
import com.feign.service.ServiceForTesting;
import com.feign.util.JwtTokenUtil;


@Import({SecurityConfig.class, MyUserDetailsService.class, JwtRequestFilter.class, JwtTokenUtil.class})

@WebMvcTest(TestingController.class) 
//@SpringBootTest(classes = {TestingController.class})
@ContextConfiguration(classes = {TestingController.class})
//@Log4j2
public class RestControllerTest {
	
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private ServiceForTesting serviceForTesting;
	@Autowired
	private MockMvc mockMvc;
	
	
	static String defaultUser;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		defaultUser = "has return";
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

	@Test
	@DisplayName("TestIfControllerCanReachEndPoint")
	void testEndPointUserName() throws Exception {
		
		 Mockito .when(this.serviceForTesting.getAdmin())
		 .thenReturn(defaultUser);
		 
		final String link = "/test/admintest";
		this.mockMvc.perform(MockMvcRequestBuilders
				.get(link)
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MDQyNjg0NSwiZXhwIjoxNjYwNDQ0ODQ1fQ.obaX4DdcmqJnzBhnGIKcueQMO_-GlRcWJVeSkSNG-tg")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("has return"));
	}

}
