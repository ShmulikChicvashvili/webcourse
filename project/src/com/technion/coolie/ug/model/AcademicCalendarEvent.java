package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.Calendar;

import com.technion.coolie.ug.Enums.DayOfWeek;
import com.technion.coolie.ug.gradessheet.Item;

public class AcademicCalendarEvent implements
		Comparable<AcademicCalendarEvent>, Item, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public AcademicCalendarEvent(final Calendar startingDay,
			final String event, final String day) {
		super();
		this.startingDay = startingDay;
		this.event = event;
		this.day = day;
	}

	private DayOfWeek dayOfWeek;
	private String day;
	private Calendar startingDay;
	private String event;

	@Override
	public int compareTo(final AcademicCalendarEvent another) {
		if (another == null || another.startingDay == null
				|| startingDay == null)
			return 0;
		return startingDay.compareTo(another.startingDay);
	}

	@Override
	public boolean isSection() {
		return false;
	}

	@Override
	public boolean isFooter() {
		return false;
	}

}
