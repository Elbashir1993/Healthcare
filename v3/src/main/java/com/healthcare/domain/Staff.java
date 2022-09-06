package com.healthcare.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "staff")
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String work_location;
	
	@Column
	private String phone;
	
	@Column 
	private String email;
	
	@Column
	private String desc;
	
	@Column
	private String current_job;

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Staff(int id, String name, String work_location, String phone, String email, String desc,
			String current_job) {
		super();
		this.id = id;
		this.name = name;
		this.work_location = work_location;
		this.phone = phone;
		this.email = email;
		this.desc = desc;
		this.current_job = current_job;
	}
	

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="staff")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	List<Certificate> certificates = new ArrayList<Certificate>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="staff")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	List<Charges> charges = new ArrayList<Charges>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="staff")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	List<PreviousJobs> previousJobs = new ArrayList<PreviousJobs>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="staff")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	List<Saterday> saterday = new ArrayList<Saterday>();
	
	
	
	
	
	/**
	 * @return the charges
	 */
	public List<Charges> getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(List<Charges> charges) {
		this.charges = charges;
	}

	/**
	 * @return the previousJobs
	 */
	public List<PreviousJobs> getPreviousJobs() {
		return previousJobs;
	}

	/**
	 * @param previousJobs the previousJobs to set
	 */
	public void setPreviousJobs(List<PreviousJobs> previousJobs) {
		this.previousJobs = previousJobs;
	}

	/**
	 * @return the saterday
	 */
	public List<Saterday> getSaterday() {
		return saterday;
	}

	/**
	 * @param saterday the saterday to set
	 */
	public void setSaterday(List<Saterday> saterday) {
		this.saterday = saterday;
	}

	/**
	 * @return the certificates
	 */
	public List<Certificate> getCertificates() {
		return certificates;
	}

	/**
	 * @param certificates the certificates to set
	 */
	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the work_location
	 */
	public String getWork_location() {
		return work_location;
	}

	/**
	 * @param work_location the work_location to set
	 */
	public void setWork_location(String work_location) {
		this.work_location = work_location;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the current_job
	 */
	public String getCurrent_job() {
		return current_job;
	}

	/**
	 * @param current_job the current_job to set
	 */
	public void setCurrent_job(String current_job) {
		this.current_job = current_job;
	}

	
	
	
}
