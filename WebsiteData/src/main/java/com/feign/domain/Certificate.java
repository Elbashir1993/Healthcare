package com.feign.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Certificate {
	
	private int id;
	
	private String name;
	
	private String file;

	
	
	public Certificate() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Certificate(int id, String name, String file) {
		super();
		this.id = id;
		this.name = name;
		this.file = file;
	}
	
	public Certificate(String originalFilename, String file) {
		// TODO Auto-generated constructor stub
		this.name = originalFilename;
		this.file = file;
	}

	
	private Staff staff;



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
	 * @return the file
	 */
	public String getFile() {
		return file;
	}



	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}



	/**
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}



	/**
	 * @param staff the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	
	
}