package com.feign.domain;

import java.io.Serializable;
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


public class Staff   {
	
	
	private int id;
	
	
	private String name;
	
	
	private String work_location;
	
	
	private String phone;
	
	 
	private String email;
	
	
	private String desc;
	
	
	private String current_job;

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Staff(String name, String work_location, String phone, String email, String desc,
			String current_job) {
		
		this.name = name;
		this.work_location = work_location;
		this.phone = phone;
		this.email = email;
		this.desc = desc;
		this.current_job = current_job;
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
