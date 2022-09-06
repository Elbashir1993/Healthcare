package com.feign.domain;


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


public class Appointment {

	private int id;
	
	private int session;
	
	private String date;
	
	private int week_day;
	
	





	public Appointment(int session, String date, int day) {
		super();
		this.session = session;
		this.date = date;
		this.week_day = day;
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
	public String getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}


	



	
}
