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
import com.healthcare.domain.Charges;
import com.healthcare.domain.PreviousJobs;
import com.healthcare.domain.Staff;
import com.healthcare.repo.ChargesRepo;
import com.healthcare.repo.PreviousJobsRepo;
import com.healthcare.repo.StaffRepo;

@CrossOrigin
@RestController
public class PreviousJobsController {
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private PreviousJobsRepo pjr;
	
	@GetMapping("/staffs/{staffId}/previousJobs")
	public Page<PreviousJobs> getAllPJByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		return pjr.findByStaffId(staffId, pageable);
	}
	
	@PostMapping("/staffs/{staffId}/previousJobs")
	public PreviousJobs createPJ(@PathVariable (value = "staffId") int staffId, @RequestBody PreviousJobs pj) {
		Staff s = staffRepo.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("staffid " + staffId + " not found"));
		  if(s == null) {
			  return null;
		  }
		  
	      pj.setStaff(s);
	      return pjr.save(pj);
	}
}