package com.technion.coolie.ug.model;

import com.technion.coolie.ug.Enums.SemesterSeason;

public class CourseKey {

	public CourseKey(String id, int year, SemesterSeason ss) {
		super();
		this.id = id;
		this.year = year;
		this.ss = ss;
	}

	private String id;
	private int year;
	private SemesterSeason ss;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public SemesterSeason getSs() {
		return ss;
	}

	public void setSs(SemesterSeason ss) {
		this.ss = ss;
	}

}
