package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.domain.Saterday;
import com.healthcare.domain.Staff;

public interface SaterdayRepo extends JpaRepository<Saterday, Long> {
	
	Page<Saterday> findByStaffId(int satId, Pageable pageable);

	boolean existsByStaffId(int staffId);
	
	
}
