package com.feign.domain;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import feign.Headers;




// used for real servers
@FeignClient("APPOINTMENT-AND-STAFF-SERVICE")

// used for mocking external servers
//@FeignClient(name = "httpbin", url = "${base.url.httpbin}")
public interface FiegnClientDomain {
	@RequestMapping(method=RequestMethod.GET, value="/testeurekaport")
    User getPort();
	@RequestMapping(method=RequestMethod.GET, value="/testFiegn")
    User getUser();
	// get all the staffs
	@GetMapping("/staffs")
	public ResponseEntity<List<StaffInfo>> getAllStaff(Pageable pageable);
	// get one staff by id
	@GetMapping("/staffs/{staff_id}")
	public Page<Staff> getStaffByStaffId(@PathVariable(value = "staff_id") Integer staff_id, Pageable pageable);
	
	// save staff to the database
	@PostMapping("/staffs")
	public ResponseEntity<Staff> createStaff(@RequestBody Staff staff);
	
	// update staff
	@PostMapping("/staffs/{staff_id}")
	public ResponseEntity<String> updateStaffByStaffId(@PathVariable(value = "staff_id") int staffId, @RequestBody Staff staff);
	
	// delete staff by id
	@DeleteMapping("/staffs/{staff_id}")
	public ResponseEntity<ResponseMessage> deleteStaffByStaffId(@PathVariable(value = "staff_id") int staffId);
	
	/**********Staff certificates************/
	// get all staff certificates by staff id from database
	@GetMapping("/staffs/{staffId}/certificates")
	public ResponseEntity<Page<Certificate>> getAllCertByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable);
	
	// create certificates for specific staff
	@PostMapping(value="/staffs/{staffId}/certificates", consumes ="multipart/form-data")
	@Headers("Content-Type: multipart/form-data")
	
	public ResponseEntity<ResponseMessage> createCertificate(@PathVariable (value = "staffId") int staffId, @RequestPart(value = "file") MultipartFile file);
	
	// Get all the certificates name and links from the storage dir
	@GetMapping("/staffs/{staffId}/certificates/all")
	  public ResponseEntity<List<FileInfo>> getListFiles(@PathVariable (value = "staffId") int staffId);
	
	// download specific certificate
	@GetMapping("/staffs/{staffId}/certificates/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String filename, @PathVariable (value = "staffId") int staffId);
	
	
	/************* staffs charges ************/
	// get staff charges
	@GetMapping("/staffs/{staffId}/charges")
	public ResponseEntity<Charges> getAllChargesByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable);
	
	// set staff charges
	@PostMapping("/staffs/{staffId}/charges")
	public ResponseEntity<Charges> createCharges(@PathVariable (value = "staffId") int staffId, @RequestBody Charges charges);
	
	/***
	 * 
	 * 
	 * Previous jobs
	 * 
	 */
	// create previous job
	@PostMapping("/staffs/{staffId}/previousJobs")
	public ResponseEntity<PreviousJobs> createPJ(@PathVariable (value = "staffId") int staffId, @RequestBody PjContainer pjc);
	
	// get all previous jobs by staff id
	@GetMapping("/staffs/{staffId}/previousJobs")
	public List<PjContainer> getAllPJByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable);
	
	/******
	 * 
	 * 
	 * schedule 
	 * 
	 */
	// save schedule 
	@PostMapping("/staffs/{staffId}/schedule")
	public ResponseEntity<ResponseMessage> createSD(@PathVariable (value = "staffId") int staffId, @RequestBody ScheduleUpdateRequestMapper sd);
	
	// get schedule by staff id
	@GetMapping("/staffs/{staffId}/schedule")
	public ResponseEntity<List<Schedule>> getSaterdayByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable);
	
	
	/***************************** appointment controller ***************************/
	// get all appointments
	@GetMapping("/appointments")
	public ResponseEntity<List<ResponseAppointWrapper>> getAllTheAppoint(Pageable pageable);

	// save appointment to the database
	@PostMapping("/appointments")
	public ResponseEntity<AppointWrapper> createAppoint(@RequestBody AppointWrapper apw);
	
	

	
	
	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}",
	 * produces = "application/json") Post getPostById(@PathVariable("postId") Long
	 * postId);
	 */

}
