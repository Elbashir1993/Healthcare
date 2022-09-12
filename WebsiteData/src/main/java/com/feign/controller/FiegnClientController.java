package com.feign.controller;
import java.util.List;

import javax.cache.Cache;
import javax.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreaker;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.feign.domain.AuthenticationRequest;
import com.feign.domain.AuthenticationResponse;
import com.feign.domain.Certificate;
import com.feign.domain.Charges;
import com.feign.domain.FiegnClientDomain;
import com.feign.domain.FileInfo;
import com.feign.domain.PjContainer;
import com.feign.domain.PreviousJobs;
import com.feign.domain.ResponseMessage;
import com.feign.domain.Schedule;
import com.feign.domain.ScheduleUpdateRequestMapper;
import com.feign.domain.Staff;
import com.feign.domain.StaffInfo;
import com.feign.domain.User;
import com.feign.repo.UserRepo;
import com.feign.service.ServiceForTesting;
import com.feign.util.JwtTokenUtil;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@CrossOrigin
@RequestMapping("/staffs")
public class FiegnClientController {
	@Autowired
	UserRepo userRepo;
	@Autowired
	private FiegnClientDomain fc;
	@Autowired
	CacheManager cm;
	@Autowired
	AuthenticationManager authManager;
	@Autowired 
	UserDetailsService userDetailsService;
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	// testing service
	
	private String result;
	/***
	 * Authenticate the api
	 * @param authReq
	 * @return
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception{
		try {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
				);
		}catch(BadCredentialsException e) {
			throw new Exception("incorrect info!!");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authReq.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		// print jwt
		System.out.println(jwt);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "fallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "fallback")
	@Retry(name = "appointment")	
	@GetMapping("/testport")
	
	public User getStaffs() {
		System.out.println("entered testPort");
		long l = 1;
		try{
			System.out.print("getting result from cached data...");
			return (User) cm.getCache("customerCache").get(l);
		}catch(NullPointerException e) {
			System.out.print(e.getMessage());
			System.out.print("caching data...");
			cm.getCache("customerCache").put(l, fc.getPort());
		}
		
		System.out.print("getting result from server...");
		return fc.getPort();
	}
	
	public String getCachedData(Long i)  {
			try{
				return cm.getCache("customerCache").get(i).toString();
			}catch(NullPointerException e) {
				System.out.print(e.getMessage());
				return "try to refresh the browser";
			}
	}
	
	
	@GetMapping("/admin/{username}")
	public User getAdmin(@PathVariable(value = "username") String username) {
		System.out.println("debuging");
		return userRepo.findUserByUsername(username);
	}
	
	// test proxy 
	@CrossOrigin
	@GetMapping("/proxytest")
	public User getUser() {
		return fc.getUser();
	}
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getAllStaffsfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getAllStaffsfallback")
	@Retry(name = "appointment")	
	
	@GetMapping("/all")
	public ResponseEntity<List<StaffInfo>> getAllStaffs(Pageable pageable){
		ResponseEntity<List<StaffInfo>> sil = fc.getAllStaff(pageable);
		if (sil.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok().body(sil.getBody());
		}
		return ResponseEntity.internalServerError().build();
	}
	public  ResponseEntity<List<StaffInfo>> getAllStaffsfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public  ResponseEntity<List<StaffInfo>> getAllStaffsfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "saveStafffallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "saveStafffallback")
	@Retry(name = "appointment")	
	@PostMapping("/save")
	public ResponseEntity<Staff> saveStaff(@RequestBody Staff staff){
		return fc.createStaff(staff);
		
	}
	public  ResponseEntity<Staff> saveStafffallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public  ResponseEntity<Staff> saveStafffallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "updateStafffallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "updateStafffallback")
	@Retry(name = "appointment")	
	@PostMapping("/update/{staff_id}")
	public ResponseEntity<String> updateStaff(@PathVariable(value = "staff_id") int staffId, @RequestBody Staff staff){
		return fc.updateStaffByStaffId(staffId, staff);
		
	}
	public  ResponseEntity<String> updateStafffallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public  ResponseEntity<String> updateStafffallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "deleteStafffallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "deleteStafffallback")
	@Retry(name = "appointment")	
	@PostMapping("/delete/{staff_id}")
	public ResponseEntity<ResponseMessage> deleteStaff(@PathVariable(value = "staff_id") int staffId){
		return fc.deleteStaffByStaffId(staffId);
	}	
	public  ResponseEntity<ResponseMessage> deleteStafffallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public  ResponseEntity<ResponseMessage> deleteStafffallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	
	
	/************************ handle Staff Certificates Requests *************/
	// get all certificates from the database
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getAllFromDbfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getAllFromDbfallback")
	@Retry(name = "appointment")	
	@GetMapping("/{staffId}/db/all")
	public ResponseEntity<Page<Certificate>> getAllFromDb(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		return fc.getAllCertByStaffId(staffId, pageable);
	}
	public  ResponseEntity<Page<Certificate>> getAllFromDbfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public  ResponseEntity<Page<Certificate>> getAllFromDbfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	// create certificates for specific staff
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "createCertificatefallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "createCertificatefallback")
	@Retry(name = "appointment")	
	@PostMapping("/{staffId}/storage/save")
	
	public ResponseEntity<ResponseMessage> createCertificate(@PathVariable (value = "staffId") int staffId, @RequestParam(value = "file") MultipartFile file){
		System.out.println(file.getName());
		return fc.createCertificate(staffId, file);
	}
	public  ResponseEntity<ResponseMessage> createCertificatefallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public  ResponseEntity<ResponseMessage> createCertificatefallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	// Get all the certificates name and links from the storage dir
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getListFilesfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getListFilesfallback")
	@Retry(name = "appointment")	
	@GetMapping("/{staffId}/storage/all")
	public ResponseEntity<List<FileInfo>> getListFiles(@PathVariable (value = "staffId") int staffId){
		return fc.getListFiles(staffId);
	}
	public ResponseEntity<List<FileInfo>> getListFilesfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<List<FileInfo>> getListFilesfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	// download specific certificate
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getFilefallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getFilefallback")
	@Retry(name = "appointment")	
	@GetMapping("/{staffId}/storage/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename, @PathVariable (value = "staffId") int staffId){
		return fc.getFile(filename, staffId);
	}
	public ResponseEntity<Resource> getFilefallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<Resource> getFilefallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	/******************************* Staff Charges **********************
	 * 
	 */
	// get staff charges
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getAllChargesByStaffIdfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getAllChargesByStaffIdfallback")
	@Retry(name = "appointment")	
		@GetMapping("/{staffId}/charges")
		public ResponseEntity<Charges> getAllChargesByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
			return fc.getAllChargesByStaffId(staffId, pageable);
		}
	public ResponseEntity<Charges> getAllChargesByStaffIdfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<Charges> getAllChargesByStaffIdfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
		// set staff charges
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "createChargesfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "createChargesfallback")
	@Retry(name = "appointment")	
		@PostMapping("/{staffId}/charges")
		public ResponseEntity<Charges> createCharges(@PathVariable (value = "staffId") int staffId, @RequestBody Charges charges, Pageable pageable){
			return fc.createCharges(staffId, charges);
		}
	public ResponseEntity<Charges> createChargesfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<Charges> createChargesfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	/*********************** staff previous jobs ****************************
	 * 
	 * 
	 * 
	 */
	// create previous job
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "createPJfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "createPJfallback")
	@Retry(name = "appointment")	
	@PostMapping("/{staffId}/previousJobs")
	public ResponseEntity<PreviousJobs> createPJ(@PathVariable (value = "staffId") int staffId, @RequestBody PjContainer pjc) {
		return fc.createPJ(staffId, pjc);
	}
	public ResponseEntity<PreviousJobs> createPJfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<PreviousJobs> createPJfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	// get all previous jobs by staff id
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getAllPJByStaffIdfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getAllPJByStaffIdfallback")
	@Retry(name = "appointment")
	@GetMapping("/{staffId}/previousJobs")
	public List<PjContainer> getAllPJByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		return fc.getAllPJByStaffId(staffId, pageable);
	}

	public List<PjContainer> getAllPJByStaffIdfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return null;
	}
	public List<PjContainer> getAllPJByStaffIdfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return null;
	}
	/************************** staff schedule ******************************
	 * 
	 * 
	 */
	// save schedule 
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "createSDfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "createSDfallback")
	@Retry(name = "appointment")
	@PostMapping("/{staffId}/schedule")
	public ResponseEntity<ResponseMessage> createSD(@PathVariable (value = "staffId") int staffId, @RequestBody ScheduleUpdateRequestMapper sd){
		return fc.createSD(staffId, sd);
	}
	
	public ResponseEntity<ResponseMessage> createSDfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<ResponseMessage> createSDfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	// get schedule by staff id
	/**** resilience4j****/
	@CircuitBreaker(name = "appointment", fallbackMethod = "getSaterdayByStaffIdfallback")
	@RateLimiter(name = "appointment")
	@Bulkhead(name = "appointment", fallbackMethod = "getSaterdayByStaffIdfallback")
	@Retry(name = "appointment")
	@GetMapping("/{staffId}/schedule")
	public ResponseEntity<List<Schedule>> getSaterdayByStaffId(@PathVariable(value = "staffId") Integer staffId, Pageable pageable){
		return fc.getSaterdayByStaffId(staffId, pageable);
	}
	 
	public ResponseEntity<List<Schedule>> getSaterdayByStaffIdfallback(CallNotPermittedException e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
	public ResponseEntity<List<Schedule>> getSaterdayByStaffIdfallback(Exception e) {
		System.out.println("server is down! please try again after some time");
		return ResponseEntity.internalServerError().body(null);
	}
}
