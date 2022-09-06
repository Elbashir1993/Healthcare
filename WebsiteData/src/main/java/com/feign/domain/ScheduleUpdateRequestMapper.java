package com.feign.domain;

public class ScheduleUpdateRequestMapper {
	private int session;
	private int week_day;
	private int value;
	public ScheduleUpdateRequestMapper(int session, int week_day, int value) {
		super();
		this.session = session;
		this.week_day = week_day;
		this.value = value;
	}
	
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}


	/**
	 * @return the session
	 */
	public int getSession() {
		return session;
	}
	/**
	 * @param session the session to set
	 */
	public void setSession(int session) {
		this.session = session;
	}
	/**
	 * @return the week_day
	 */
	public int getWeek_day() {
		return week_day;
	}
	/**
	 * @param week_day the week_day to set
	 */
	public void setWeek_day(int week_day) {
		this.week_day = week_day;
	}
	
	
}
