package com.healthcare.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int session;
	
	@Column(name="date", columnDefinition = "DATE")
	private LocalDate date;
	
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Appointment(int session, LocalDate date) {
		super();
		this.session = session;
		this.date = date;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="staff_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Staff staff;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="appointment")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	List<ContactInfo> contactInfo = new ArrayList<ContactInfo>();

	/**
	 * @return the contactInfo
	 */
	public List<ContactInfo> getContactInfo() {
		return contactInfo;
	}


	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(List<ContactInfo> contactInfo) {
		this.contactInfo = contactInfo;
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
	 * @return the session
	 */
	public int getSession() {
		return session;
	}


	/**
	 * @param session the session to set
	 */
	public void setSession(int session) {
		this.session = session;
	}


	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
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
