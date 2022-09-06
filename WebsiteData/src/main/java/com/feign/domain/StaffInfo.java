package com.feign.domain;

import java.util.ArrayList;
import java.util.List;



public class StaffInfo {
	private Staff staff;
	private List<Certificate> certificate;
	private List<Charges> charges;
	private List<PjContainer> pjContainer;
	private List<Schedule> saterday;
	private int port;

	public StaffInfo(Staff staff, List<Certificate> certificate, List<Charges> charges, List<PjContainer> pjContainer,
			List<Schedule> saterday, int port) {
		super();
		this.staff = staff;
		this.certificate = certificate;
		this.charges = charges;
		this.pjContainer = pjContainer;
		this.saterday = saterday;
		this.port = port;
	}
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the charges
	 */
	public List<Charges> getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(List<Charges> charges) {
		this.charges = charges;
	}

	/**
	 * @return the pjContainer
	 */
	public List<PjContainer> getPjContainer() {
		return pjContainer;
	}

	/**
	 * @param pjContainer the pjContainer to set
	 */
	public void setPjContainer(List<PjContainer> pjContainer) {
		this.pjContainer = pjContainer;
	}

	/**
	 * @return the saterday
	 */
	public List<Schedule> getSaterday() {
		return saterday;
	}

	/**
	 * @param saterday the saterday to set
	 */
	public void setSaterday(List<Schedule> saterday) {
		this.saterday = saterday;
	}

	/**
	 * @return the certificate
	 */
	public List<Certificate> getCertificate() {
		return certificate;
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
	 * @param certificate the certificate to set
	 */
	public void setCertificate(List<Certificate> certificate) {
		this.certificate = certificate;
	}
	
	
}
