package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.healthcare.domain.Staff;
import com.healthcare.domain.Websites;
@CrossOrigin
@Repository
public interface StaffRepo extends JpaRepository<Staff, Integer> {
	

	Page<Staff> findStaffById(Integer staff_id, Pageable pageable);
	Staff findOneStaffById(Integer staff_id);
	
}
