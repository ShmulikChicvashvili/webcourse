package com.technion.coolie.ug.model;

import com.technion.coolie.ug.Enums.SemesterSeason;

public class Semester {

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

	public Semester(int year, SemesterSeason ss) {
		super();
		this.year = year;
		this.ss = ss;
	}

	int year;
	SemesterSeason ss;
}
