package com.api.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.api.domain.User;

@CrossOrigin
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	

	Optional<User> findByUsername(String username);
	User findUserByUsername(String username);
	
}

