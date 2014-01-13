package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.Calendar;

public class ExamItem implements Serializable {

	private final Calendar date;
	private final String place;

	public Calendar getDate() {
		return date;
	}

	public String getPlace() {
		return place;
	}

	public ExamItem(final Calendar date, final String place) {
		this.date = date;
		this.place = place;
	}

	private static final long serialVersionUID = -653511331419637543L;
}
