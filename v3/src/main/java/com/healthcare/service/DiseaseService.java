package com.healthcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.domain.Disease;
import com.healthcare.repo.DiseaseRepo;

@Service

public class DiseaseService {
	@Autowired 
	DiseaseRepo diseaseRepo;
	public List<Disease> getAllDiseases(){
		List<Disease> diseases = new ArrayList<Disease>();
		
		diseaseRepo.findAll().forEach(disease->diseases.add(disease));
		return diseases;
	}
	public void saveDisease(Disease disease){
		
		diseaseRepo.save(disease);
		
	}
}
