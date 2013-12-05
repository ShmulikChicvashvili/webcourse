package com.technion.coolie.ug.model;

import java.io.Serializable;

import com.technion.coolie.ug.Enums.SemesterSeason;

public class Semester implements Serializable {

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

	private static final long serialVersionUID = 7728978870620022481L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ss == null) ? 0 : ss.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Semester other = (Semester) obj;
		if (ss != other.ss)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}
