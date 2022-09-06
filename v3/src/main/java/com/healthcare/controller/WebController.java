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
import com.healthcare.domain.Websites;
import com.healthcare.repo.DiseaseRepo;
import com.healthcare.repo.WebRepository;
@CrossOrigin
@RestController
public class WebController {
	@Autowired
	private WebRepository webRepo;
	@Autowired
	private DiseaseRepo diseaseRepo;
	
	@GetMapping("/diseases/{diseaseId}/websites/all")
	public Page<Websites> getAllWebsitesByPostId(@PathVariable(value = "diseaseId") Integer diseaseId, Pageable pageable){
		return webRepo.findByDiseaseId(diseaseId, pageable);
	}
	
	@PostMapping("/diseases/{diseaseId}/websites")
	public Websites createWebsite(@PathVariable (value = "diseaseId") int diseaseId, @RequestBody Websites website) {
		 return diseaseRepo.findById(diseaseId).map(disease -> {
	            website.setDisease(disease);
	            return webRepo.save(website);
	        }).orElseThrow(() -> new ResourceNotFoundException("DiseaseId " + diseaseId + " not found"));
	}
}
