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
import com.healthcare.domain.Appointment;
import com.healthcare.domain.Certificate;
import com.healthcare.domain.ContactInfo;
import com.healthcare.domain.Staff;
import com.healthcare.repo.AppointmentRepo;
import com.healthcare.repo.CertificateRepo;
import com.healthcare.repo.ContactInfoRepo;
import com.healthcare.repo.StaffRepo;

@CrossOrigin
@RestController
public class ContactInfoController {
	@Autowired
	private ContactInfoRepo contactRepo;
	@Autowired
	private AppointmentRepo appointRepo;
	
	@GetMapping("/appointments/{appointId}/contactInfo")
	public Page<ContactInfo> getContactByAppointId(@PathVariable(value = "appointId") Integer appointId, Pageable pageable){
		return contactRepo.findByAppointmentId(appointId, pageable);
	}
	
	@PostMapping("/appointments/{appointId}/contactInfo")
	public ContactInfo createContact(@PathVariable (value = "appointId") int appointId, @RequestBody ContactInfo contact) {
		Appointment ap = appointRepo.findById(appointId).orElseThrow(() -> new ResourceNotFoundException("DiseaseId " + appointId + " not found"));
		  if(ap == null) {
			  return null;
		  }
		  
	      contact.setAppointment(ap);
	      return contactRepo.save(contact);
	}
}