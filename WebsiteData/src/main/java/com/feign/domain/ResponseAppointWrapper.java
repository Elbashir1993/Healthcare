package com.feign.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseAppointWrapper  implements Serializable{
	private static final long serialVersionUID = 550107488L;
	private Appointment appointment;
	
	private Staff staff;
	
	private List<ContactInfo> contactInfo;
	public ResponseAppointWrapper(Appointment appointment, Staff staff, List<ContactInfo> contactInfo) {
		this.appointment = appointment;
		this.staff = staff;
		this.contactInfo = contactInfo;
	}
	public ResponseAppointWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the appointment
	 */
	public Appointment getAppointment() {
		return appointment;
	}
	/**
	 * @param appointment the appointment to set
	 */
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
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
	
	
}
