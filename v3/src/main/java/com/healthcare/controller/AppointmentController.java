package com.healthcare.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.domain.AppointWrapper;
import com.healthcare.domain.Appointment;
import com.healthcare.domain.ContactInfo;
import com.healthcare.domain.Saterday;
import com.healthcare.domain.Staff;
import com.healthcare.repo.AppointmentRepo;
import com.healthcare.repo.StaffRepo;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AppointmentController {
	@Autowired
	private AppointmentRepo appointRepo;
	@Autowired
	private StaffRepo staffRepo;
	
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
	
	@GetMapping("/appointments")
	public List<AppointWrapper> getAllAppoint(Pageable pageable){
		List<AppointWrapper> apwl = new ArrayList<AppointWrapper>();
		Page<Appointment> apl = appointRepo.findAll(pageable);
		for(Appointment ap: apl) {
			int staffId = ap.getStaff().getId();
			List<ContactInfo> cl = ap.getContactInfo();
			AppointWrapper apw = new AppointWrapper(ap, staffId, cl);
			apwl.add(apw);
		}
		
		return apwl;
	}
	
	@PostMapping("/appointments")
	public AppointWrapper createStaff(@RequestBody AppointWrapper apw) {
		Appointment ap = apw.getAppointment();
		int staffId = apw.getStaffId();
		List<ContactInfo> cl = apw.getContactInfo();
		Staff s = staffRepo.findOneStaffById(staffId);
		if(s != null) {
			for(Saterday sat : s.getSaterday()) {
				switch(ap.getSession()) {
					case 1:  sat.setSession1(true);break;
					case 2:  sat.setSession2(true);break;
					case 3:  sat.setSession3(true);break;
					case 4:  sat.setSession4(true);break;
					case 5:  sat.setSession5(true);break;
					case 6:  sat.setSession6(true);break;
					default: return null;
				}
			}
			ap.setStaff(s);
			for(ContactInfo c: cl) {
				c.setAppointment(ap);
			}
			ap.setContactInfo(cl);
			try {
				appointRepo.save(ap);
				return new AppointWrapper(ap, staffId, cl);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}else {
			// staff is null
			System.out.print("staff is null");
		}
		return null;
	}
}
