package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.domain.Duration;
import com.healthcare.domain.Staff;
import com.healthcare.domain.Websites;

public interface DurationRepo extends JpaRepository<Duration, Long> {
	Page<Duration> findByPreviousJobsId(int previousJobsId, Pageable pageable);
	
}
