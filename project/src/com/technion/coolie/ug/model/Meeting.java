package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.Date;

import com.technion.coolie.ug.Enums.DayOfWeek;

public class Meeting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3880934967869395104L;

	private String id;
	private String lecturerName;
	private DayOfWeek day;
	private Date startingHour;
	private Date endingHour;
	private String place;

	public Meeting(final String id, final String lecturerName,
			final DayOfWeek day, final Date startingHour,
			final Date endingHour, final String place) {
		super();
		this.id = id;
		this.lecturerName = lecturerName;
		this.day = day;
		this.startingHour = startingHour;
		this.endingHour = endingHour;
		this.place = place;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(final String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(final DayOfWeek day) {
		this.day = day;
	}

	public Date getStartingHour() {
		return startingHour;
	}

	public void setStartingHour(final Date startingHour) {
		this.startingHour = startingHour;
	}

	public Date getEndingHour() {
		return endingHour;
	}

	public void setEndingHour(final Date endingHour) {
		this.endingHour = endingHour;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(final String place) {
		this.place = place;
	}

}
