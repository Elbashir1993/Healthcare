package com.healthcare.domain;

import java.util.List;

public class PjContainer {
	private PreviousJobs pj;
	private List<Duration> d;
	public PjContainer(PreviousJobs pj, List<Duration> d) {
		super();
		this.pj = pj;
		this.d = d;
	}
	public PjContainer(PreviousJobs pj) {
		// TODO Auto-generated constructor stub
		this.pj = pj;
	}
	/**
	 * @return the pj
	 */
	public PreviousJobs getPj() {
		return pj;
	}
	/**
	 * @param pj the pj to set
	 */
	public void setPj(PreviousJobs pj) {
		this.pj = pj;
	}
	/**
	 * @return the d
	 */
	public List<Duration> getD() {
		return d;
	}
	/**
	 * @param d the d to set
	 */
	public void setD(List<Duration> d) {
		this.d = d;
	}
	
}
