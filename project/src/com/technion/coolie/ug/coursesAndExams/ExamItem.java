package com.technion.coolie.ug.coursesAndExams;

import java.util.Calendar;

public class ExamItem {
	private final Calendar date;
	private final String place;

	public Calendar getDate() {
		return date;
	}

	public String getPlace() {
		return place;
	}

	public ExamItem(Calendar date, String place) {
		this.date = date;
		this.place = place;
	}
}
