package com.healthcare.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "charges")
public class Charges {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int physical;
	
	@Column
	private int online;

	public Charges() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Charges(int physical, int online) {
		super();
		this.physical = physical;
		this.online = online;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="staff_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
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
	 * @return the physical
	 */
	public int getPhysical() {
		return physical;
	}

	/**
	 * @param physical the physical to set
	 */
	public void setPhysical(int physical) {
		this.physical = physical;
	}

	/**
	 * @return the online
	 */
	public int getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(int online) {
		this.online = online;
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