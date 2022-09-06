package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.domain.Charges;
import com.healthcare.domain.Staff;
import com.healthcare.domain.Websites;

public interface ChargesRepo extends JpaRepository<Charges, Long> {
	
	Page<Charges> findByStaffId(Integer staffId, Pageable pageable);
}
