package com.healthcare.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.healthcare.domain.Appointment;
import com.healthcare.domain.ContactInfo;

@CrossOrigin
@Repository
public interface ContactInfoRepo extends JpaRepository<ContactInfo, Long> {
	Page<ContactInfo> findByAppointmentId(int appointmentId, Pageable pageable);
}

