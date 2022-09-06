package com.healthcare.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.domain.Websites;
@Repository
public interface WebRepository extends JpaRepository<Websites, Long> {
	Page<Websites> findByDiseaseId(int diseaseId, Pageable pageable);
	
}
