package com.healthcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.ResourceNotFoundException;
import com.healthcare.domain.Certificate;
import com.healthcare.domain.Charges;
import com.healthcare.domain.Duration;
import com.healthcare.domain.PjContainer;
import com.healthcare.domain.PreviousJobs;
import com.healthcare.domain.Saterday;
import com.healthcare.domain.Staff;
import com.healthcare.domain.Websites;
import com.healthcare.repo.ChargesRepo;
import com.healthcare.repo.DiseaseRepo;
import com.healthcare.repo.StaffRepo;
import com.healthcare.repo.WebRepository;

@CrossOrigin
@RestController
@RequestMapping("/")
public class StaffController {
	@Autowired
	private StaffRepo staffRepo;
	@Value("${server.port}")
	private int port;
	
	static <T> Page<T> pageFromList(List<T> li, Pageable pageable){
		if(li == null) {
			throw new IllegalArgumentException("li is null");
		}
		int start = pageable.getPageNumber() * pageable.getPageSize();
		if(start > li.size()) {
			return new PageImpl<>(new ArrayList<>(), pageable, 0);
		}
		int end = Math.min(start + pageable.getPageSize(), li.size());
		return new PageImpl<>(li.subList(start, end), pageable, li.size());
	}
	
	@GetMapping("/testeurekaport")
	public String getPort() {
		return port+"";
	}
	@GetMapping("/staffs")
	public List<StaffInfo> getAllStaff(Pageable pageable){
		
		
		List<StaffInfo> stinfol = new ArrayList<StaffInfo>();
		Page<Staff> sl = staffRepo.findAll(pageable);
		for(Staff st: sl) {
			List<Certificate> cl = st.getCertificates();
			List<Charges> chl = st.getCharges();
			List<PreviousJobs> prjl = st.getPreviousJobs();
			List<PjContainer> pjl = new ArrayList<PjContainer>();
			for(PreviousJobs p: prjl) {
				pjl.add(new PjContainer(p, p.getDuration()));
			}
			List<Saterday> sal = st.getSaterday();
			StaffInfo stinfo = new StaffInfo(st, cl, chl, pjl, sal, port);
			stinfol.add(stinfo);
		}
		
		//List<StaffInfo> stl = stinfol;
		//return pageFromList(stinfol, pageable);
		return stinfol;
 	}
	@GetMapping("/staffs/{staff_id}")
	public Page<Staff> getStaffByStaffId(@PathVariable(value = "staff_id") Integer staff_id, Pageable pageable){
		return staffRepo.findStaffById(staff_id, pageable);
	}
	
	@PostMapping("/staffs")
	public Staff createStaff(@RequestBody StaffInfo staffInfo) {
			  Staff s = staffInfo.getStaff();
			  List<Certificate> cl = staffInfo.getCertificate();
			  List<Charges> chl = staffInfo.getCharges();
			  List<PjContainer> prjl = staffInfo.getPjContainer();
			  List<Saterday> sal = staffInfo.getSaterday();
			  List<PreviousJobs> pjl = new ArrayList<PreviousJobs>();
			  for(Certificate c: cl) {
				  c.setStaff(s);
			  }
			  for(Charges c: chl) {
				  c.setStaff(s);
			  }
			 
			  for(PjContainer c: prjl) {
				  c.getPj().setStaff(s);
					
				  List<Duration> dl = c.getD(); 
				  
				  for(Duration d: dl) {
					  d.setPreviousJobs(c.getPj());
					  System.out.print(d.getStart());
				  }
				  c.getPj().setDuration(dl);
					 
				  //List<Duration> dl = c.getDuration();
					/*
					 * for(Duration d: dl) { d.setPreviousJobs(c); }
					 */
				 // c.setDuration(dl);
				 //System.out.print(c.getDuration());
				  pjl.add(c.getPj());
			  }
			  
			  for(Saterday c: sal) {
				  c.setStaff(s);
			  }
			  s.setCertificates(cl);
			  s.setCharges(chl);
			  s.setPreviousJobs(pjl);
			  s.setSaterday(sal);
			  return staffRepo.save(s);
	}
}