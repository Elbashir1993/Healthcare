package com.healthcare.domain;

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

@Entity
@Table(name = "websites")
public class Websites {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String name;

	public Websites() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Websites(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="disease_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Disease disease;

	/**
	 * @return the disease
	 */
	public Disease getDisease() {
		return disease;
	}

	/**
	 * @param disease the disease to set
	 */
	public void setDisease(Disease disease) {
		this.disease = disease;
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
	
	
	
}
