package com.healthcare.domain;

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
@Table(name = "duration")
public class Duration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String start;
	@Column
	private String end_date;
	


	public Duration(String start, String end_date) {
		super();
		this.start = start;
		this.end_date = end_date;
	}


	public Duration() {
		super();
		// TODO Auto-generated constructor stub
	}


	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="previous_jobs_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private PreviousJobs previousJobs;
	
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
	 * @return the start
	 */
	public String getStart() {
		return start;
	}


	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}


	/**
	 * @return the end_date
	 */
	public String getend_date() {
		return end_date;
	}


	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}


	/**
	 * @return the previousJobs
	 */
	public PreviousJobs getPreviousJobs() {
		return previousJobs;
	}


	/**
	 * @param previousJobs the previousJobs to set
	 */
	public void setPreviousJobs(PreviousJobs previousJobs) {
		this.previousJobs = previousJobs;
	}

	
}
