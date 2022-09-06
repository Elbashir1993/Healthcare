/**
 * 
 */
package com.fiegn.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.feign.domain.User;
import com.feign.repo.UserRepo;
import com.feign.service.MyUserDetailsService;

/**
 * @author Elbas
 *
 */


@ExtendWith(MockitoExtension.class)
public class ServiceLayerTest {

	@Mock
	private UserRepo userRepo;
	@InjectMocks
	private MyUserDetailsService myUserDetailsService;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testService() {
		// mock the service
		User defaultUser = new User(1, "user", "user", "ROLE_USER", true);
		Mockito.when(this.userRepo.findUserByUsername("user"))
		.thenReturn(defaultUser);
		
		User fromService = this.myUserDetailsService.getUser("user");
		assertEquals(defaultUser, fromService);
	}

}
