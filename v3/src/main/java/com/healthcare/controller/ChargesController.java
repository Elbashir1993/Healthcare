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
import com.healthcare.domain.Charges;
import com.healthcare.domain.Saterday;
import com.healthcare.domain.Staff;
import com.healthcare.repo.CertificateRepo;
import com.healthcare.repo.ChargesRepo;
import com.healthcare.repo.StaffRepo;

@CrossOrigin
@RestController
public class ChargesController {
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private ChargesRepo chargesRepo;
	
	@GetMapping("/staffs/{staffId}/charges")
	public Page<Charges> getAllChargesByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		return chargesRepo.findByStaffId(staffId, pageable);
	}
	
	@PostMapping("/staffs/{staffId}/charges")
	public Charges createCharges(@PathVariable (value = "staffId") int staffId, @RequestBody Charges charges, Pageable pageable) {
		Staff s = staffRepo.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("staffid " + staffId + " not found"));
		  if(s == null) {
			  return null;
		  }
	      charges.setStaff(s);
	      Page<Charges> sa = chargesRepo.findByStaffId(staffId, pageable);
		     
	      if(sa.isEmpty()) {
	    	  return chargesRepo.save(charges);
	      }
	      return new Charges();
	      
	}
}
