package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.domain.PreviousJobs;
import com.healthcare.domain.Staff;
import com.healthcare.domain.Websites;

public interface PreviousJobsRepo extends JpaRepository<PreviousJobs, Long> {
	Page<PreviousJobs> findByStaffId(int staffId, Pageable pageable);
	
}
