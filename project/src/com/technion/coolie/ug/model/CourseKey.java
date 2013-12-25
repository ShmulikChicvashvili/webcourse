package com.technion.coolie.ug.model;

import java.io.Serializable;

import com.technion.coolie.ug.Enums.SemesterSeason;

public class CourseKey implements Serializable {

	public CourseKey(final String id, final Semester semester) {
		super();
		number = id;
		this.semester = semester;
	}

	private String number;
	private Semester semester;

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	private static final long serialVersionUID = 939036143890035323L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (number == null ? 0 : number.hashCode());
		result = prime * result + (semester == null ? 0 : semester.hashCode());
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
		final CourseKey other = (CourseKey) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (semester == null) {
			if (other.semester != null)
				return false;
		} else if (!semester.equals(other.semester))
			return false;
		return true;
	}

	public String toKeyString()
	{
		return number +" "+ semester.year+" "+semester.ss;
	}
	
	public static  CourseKey keyStringToCourseKey(String keyString){
		String [] words = keyString.split(" ");
		String number = words[0];
		int year = Integer.parseInt(words[1]);
		SemesterSeason ss = SemesterSeason.valueOf(words[2]);
		return new CourseKey(number, new Semester(year, ss));
	}
	
}
