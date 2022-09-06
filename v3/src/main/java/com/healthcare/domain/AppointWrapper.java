package com.healthcare.domain;

import java.util.List;

public class AppointWrapper {

	private Appointment appointment;
	private int staffId;
	private List<ContactInfo> contactInfo;
	public AppointWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppointWrapper(Appointment appointment, int staffId, List<ContactInfo> contactInfo) {
		super();
		this.appointment = appointment;
		this.staffId = staffId;
		this.contactInfo = contactInfo;
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
	 * @return the staffId
	 */
	public int getStaffId() {
		return staffId;
	}
	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
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
