package com.technion.coolie.ug.model;

import java.util.Date;

import com.technion.coolie.ug.Enums.SemesterSeason;

public class Exam {
	public Exam(final String courseNumber, final SemesterSeason ss,
			final int moed, final Date examDate, final String place) {
		super();
		this.courseNumber = courseNumber;
		this.ss = ss;
		this.moed = moed;
		this.examDate = examDate;
		this.place = place;
	}

	private String courseNumber;
	private SemesterSeason ss;
	private int moed;
	private Date examDate;
	private String place;

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(final String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public SemesterSeason getSs() {
		return ss;
	}

	public void setSs(final SemesterSeason ss) {
		this.ss = ss;
	}

	public int getMoed() {
		return moed;
	}

	public void setMoed(final int moed) {
		this.moed = moed;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(final Date examDate) {
		this.examDate = examDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(final String place) {
		this.place = place;
	}
}
