package com.technion.coolie.ug.model;

import java.util.Calendar;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.gradessheet.Item;

public class AcademicCalendarEvent implements
		Comparable<AcademicCalendarEvent>, Item {
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public AcademicCalendarEvent(/* DayOfWeek dayOfWeek, */Calendar startingDay,
			String event, String day) {
		super();
		// this.dayOfWeek = dayOfWeek;
		this.startingDay = startingDay;
		this.event = event;
		this.day = day;
	}

	private DayOfWeek dayOfWeek;
	private String day;
	private Calendar startingDay;
	private String event;

	@Override
	public int compareTo(AcademicCalendarEvent another) {
		if (another == null || another.startingDay == null
				|| this.startingDay == null)
			return 0;
		return this.startingDay.compareTo(another.startingDay);
	}

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFooter() {
		// TODO Auto-generated method stub
		return false;
	}

}
