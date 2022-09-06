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
import com.healthcare.domain.PreviousJobs;
import com.healthcare.domain.Saterday;
import com.healthcare.domain.Staff;
import com.healthcare.repo.PreviousJobsRepo;
import com.healthcare.repo.SaterdayRepo;
import com.healthcare.repo.StaffRepo;

@CrossOrigin
@RestController
public class SaterdayController {
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private SaterdayRepo satRepo;
	
	@GetMapping("/staffs/{staffId}/saterday")
	public Page<Saterday> getSaterdayByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		Staff s = staffRepo.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("staffid " + staffId + " not found"));
		  if(s == null) {
			  return null;
		  }
		return satRepo.findByStaffId(staffId, pageable);
	}
	
	@PostMapping("/staffs/{staffId}/saterday")
	public Saterday createSD(@PathVariable (value = "staffId") int staffId, @RequestBody Saterday sd, Pageable pageable) {
		Staff s = staffRepo.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("staffid " + staffId + " not found"));
		  if(s == null) {
			  return null;
		  }
	      sd.setStaff(s);
	      Page<Saterday> sa = satRepo.findByStaffId(staffId, pageable);
	     
	      if(sa.isEmpty()) {
	    	  return satRepo.save(sd);
	      }
	      return new Saterday();
	}
}
