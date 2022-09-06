package com.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.ResourceNotFoundException;
import com.healthcare.domain.Certificate;
import com.healthcare.domain.Staff;
import com.healthcare.domain.Websites;
import com.healthcare.repo.CertificateRepo;
import com.healthcare.repo.DiseaseRepo;
import com.healthcare.repo.StaffRepo;
import com.healthcare.repo.WebRepository;

@CrossOrigin
@RestController
public class CertificateController {
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private CertificateRepo cerRepo;
	
	@GetMapping("/staffs/{staffId}/certificates")
	public Page<Certificate> getAllCertByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		return cerRepo.findByStaffId(staffId, pageable);
	}
	
	@PostMapping("/staffs/{staffId}/certificates")
	public Certificate createCertificate(@PathVariable (value = "staffId") int staffId, @RequestBody Certificate cert) {
		Staff s = staffRepo.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("DiseaseId " + staffId + " not found"));
		  if(s == null) {
			  return null;
		  }
		  
	      cert.setStaff(s);
	      return cerRepo.save(cert);
	}
}