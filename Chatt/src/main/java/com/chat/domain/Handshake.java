package com.chat.domain;

public class Handshake {
	private String state;
	private String rId;
	
	
	public Handshake(String state, String rId) {
		super();
		this.state = state;
		this.rId = rId;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the rId
	 */
	public String getrId() {
		return rId;
	}
	/**
	 * @param rId the rId to set
	 */
	public void setrId(String rId) {
		this.rId = rId;
	}
	
}
