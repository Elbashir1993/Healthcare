package com.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.domain.Disease;
import com.healthcare.repo.DiseaseRepo;

@RestController
@CrossOrigin
public class DiseaseController {
	@Autowired
	DiseaseRepo diseaseRepo;
	@GetMapping("/diseases")
	private Page<Disease> getAllDiseases(Pageable pageable){
		return diseaseRepo.findAll(pageable);
	}
	
	@PostMapping("/diseases")
	private Disease saveD(@RequestBody Disease disease){
		
		return diseaseRepo.save(disease);
		
	}
}
