package com.healthcare.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.healthcare.domain.Appointment;


@CrossOrigin
@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
	Page<Appointment> findByStaffId(int staffId, Pageable pageable);

	
}
