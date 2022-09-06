package com.feign.domain;

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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Schedule {

	
	
	private int id;
	
	
	private int week_day;
	
	
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private int session1;
	
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private int session2;
	
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private int session3;
	
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private int session4;
	
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private int session5;
	
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private int session6;
	
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Schedule( int session1, int session2, int session3, int session4, int session5,
			int session6, int day) {
		super();
		this.week_day = day;
		this.session1 = session1;
		this.session2 = session2;
		this.session3 = session3;
		this.session4 = session4;
		this.session5 = session5;
		this.session6 = session6;
	}
	
	
	/**
	 * @return the week_day
	 */
	public int getWeek_day() {
		return week_day;
	}

	/**
	 * @param week_day the week_day to set
	 */
	public void setWeek_day(int week_day) {
		this.week_day = week_day;
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
	 * @return the session1
	 */
	public int getSession1() {
		return session1;
	}

	/**
	 * @param session1 the session1 to set
	 */
	public void setSession1(int session1) {
		this.session1 = session1;
	}

	/**
	 * @return the session2
	 */
	public int getSession2() {
		return session2;
	}

	/**
	 * @param session2 the session2 to set
	 */
	public void setSession2(int session2) {
		this.session2 = session2;
	}

	/**
	 * @return the session3
	 */
	public int getSession3() {
		return session3;
	}

	/**
	 * @param session3 the session3 to set
	 */
	public void setSession3(int session3) {
		this.session3 = session3;
	}

	/**
	 * @return the session4
	 */
	public int getSession4() {
		return session4;
	}

	/**
	 * @param session4 the session4 to set
	 */
	public void setSession4(int session4) {
		this.session4 = session4;
	}

	/**
	 * @return the session5
	 */
	public int getSession5() {
		return session5;
	}

	/**
	 * @param session5 the session5 to set
	 */
	public void setSession5(int session5) {
		this.session5 = session5;
	}

	/**
	 * @return the session6
	 */
	public int getSession6() {
		return session6;
	}

	/**
	 * @param session6 the session6 to set
	 */
	public void setSession6(int session6) {
		this.session6 = session6;
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
