package com.feign.domain;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class Disease {
	public Disease() {
		
	}
	public Disease(int id, String name, String disc) {
		super();
		this.id = id;
		this.name = name;
		this.disc = disc;
	}
	
	private int id;
	
	
	private String name;
	
	
	private String disc;
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
	 * @return the disc
	 */
	public String getDisc() {
		return disc;
	}
	/**
	 * @param disc the disc to set
	 */
	public void setDisc(String disc) {
		this.disc = disc;
	}
	
	


}
