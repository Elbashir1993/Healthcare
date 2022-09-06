package com.feign.domain;

public class ResponseMessage {
	  private String message;
	  private int status;
	  public ResponseMessage(String message) {
	    this.message = message;
	  }
	  
	  public ResponseMessage(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
	    return message;
	  }
	  public void setMessage(String message) {
	    this.message = message;
	  }
	}