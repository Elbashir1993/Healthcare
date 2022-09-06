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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "saturday")
public class Saterday {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean session1;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean session2;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean session3;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean session4;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean session5;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean session6;
	
	public Saterday() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Saterday( boolean session1, boolean session2, boolean session3, boolean session4, boolean session5,
			boolean session6) {
		super();
		
		this.session1 = session1;
		this.session2 = session2;
		this.session3 = session3;
		this.session4 = session4;
		this.session5 = session5;
		this.session6 = session6;
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
	 * @return the session1
	 */
	public boolean isSession1() {
		return session1;
	}

	/**
	 * @param session1 the session1 to set
	 */
	public void setSession1(boolean session1) {
		this.session1 = session1;
	}

	/**
	 * @return the session2
	 */
	public boolean isSession2() {
		return session2;
	}

	/**
	 * @param session2 the session2 to set
	 */
	public void setSession2(boolean session2) {
		this.session2 = session2;
	}

	/**
	 * @return the session3
	 */
	public boolean isSession3() {
		return session3;
	}

	/**
	 * @param session3 the session3 to set
	 */
	public void setSession3(boolean session3) {
		this.session3 = session3;
	}

	/**
	 * @return the session4
	 */
	public boolean isSession4() {
		return session4;
	}

	/**
	 * @param session4 the session4 to set
	 */
	public void setSession4(boolean session4) {
		this.session4 = session4;
	}

	/**
	 * @return the session5
	 */
	public boolean isSession5() {
		return session5;
	}

	/**
	 * @param session5 the session5 to set
	 */
	public void setSession5(boolean session5) {
		this.session5 = session5;
	}

	/**
	 * @return the session6
	 */
	public boolean isSession6() {
		return session6;
	}

	/**
	 * @param session6 the session6 to set
	 */
	public void setSession6(boolean session6) {
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
