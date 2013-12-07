package com.technion.coolie.ug.model;

import java.util.Calendar;

import com.technion.coolie.ug.Enums.DayOfWeek;

public class AcademicCalendarEvent implements Comparable<AcademicCalendarEvent>
{
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Calendar getStartingDay() {
		return startingDay;
	}
	public void setStartingDay(Calendar startingDay) {
		this.startingDay = startingDay;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public AcademicCalendarEvent(DayOfWeek dayOfWeek, Calendar startingDay, String event) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.startingDay = startingDay;
		this.event = event;
	}
	private DayOfWeek dayOfWeek;
	private Calendar startingDay;
	private String event;
	@Override
	public int compareTo(AcademicCalendarEvent another) {
		if (another==null || another.startingDay==null || this.startingDay==null) 
			return 0;
		return this.startingDay.compareTo(another.startingDay); 
	}
	
}
