package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.Calendar;

import com.technion.coolie.ug.Enums.DayOfWeek;

public class AcademicCalendarEvent implements
		Comparable<AcademicCalendarEvent> , Serializable{
	
	private DayOfWeek dayOfWeek;
	private String day;
	private Calendar startingDay;
	private String event;
	private String month;

	public AcademicCalendarEvent() {
	}

	public AcademicCalendarEvent(final Calendar startingDay,
			final String event, final String day, final String month) {
		super();
		this.startingDay = startingDay;
		this.event = event;
		this.day = day;
		this.month = month;
	}

	
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(final DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Calendar getStartingDay() {
		return startingDay;
	}

	public void setStartingDay(final Calendar startingDay) {
		this.startingDay = startingDay;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(final String event) {
		this.event = event;
	}

	public String getDay() {
		return day;
	}

	public void setDay(final String day) {
		this.day = day;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}


	@Override
	public String toString() {
		return (day + "  " + startingDay.getTime() + "  " + event);
	}
	
	@Override
	public int compareTo(final AcademicCalendarEvent another) {
		if (another == null || another.startingDay == null
				|| startingDay == null)
			return 0;
		return startingDay.compareTo(another.startingDay);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3768662301161097807L;

}
