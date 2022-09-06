package com.fiegn.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.feign.FeignApplication;
import com.feign.controller.TestingController;
import com.feign.domain.User;
//import com.feign.domain.User;
import com.feign.repo.UserRepo;

@Import({User.class})
@ContextConfiguration(classes = {FeignApplication.class})
@DataJpaTest

class DBJpaTesting {
	@Autowired
	 private TestEntityManager entityManager;
	 @Autowired
	 UserRepo userRepo;
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

	@Test
	void test() {
		// detach error: delete the id field
		 this.entityManager.persist(new User("user","user", "ROLE_USER", true));
	        User user = this.userRepo.findUserByUsername("user");
	        assertEquals(user.getUsername(),"user");
	}

}
