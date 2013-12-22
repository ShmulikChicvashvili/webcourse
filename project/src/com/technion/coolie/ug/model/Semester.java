package com.technion.coolie.ug.model;

import java.io.Serializable;

import com.technion.coolie.ug.Enums.SemesterSeason;

public class Semester implements Serializable, Comparable<Semester> {

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public SemesterSeason getSs() {
		return ss;
	}

	public void setSs(final SemesterSeason ss) {
		this.ss = ss;
	}

	public Semester(final int year, final SemesterSeason ss) {
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
		result = prime * result + (ss == null ? 0 : ss.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Semester other = (Semester) obj;
		if (ss != other.ss)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public int compareTo(final Semester another) {
		if (another == null || ss == null || another.ss == null)
			return 0;
		if (year != another.year)
			return year - another.year;
		else
			return ss.ordinal() - another.ss.ordinal();
	}
}
