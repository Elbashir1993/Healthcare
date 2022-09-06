package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.healthcare.domain.Certificate;
import com.healthcare.domain.Charges;
@CrossOrigin
@Repository
public interface CertificateRepo extends JpaRepository<Certificate, Long> {
	Page<Certificate> findByStaffId(int staffId, Pageable pageable);
	
}
