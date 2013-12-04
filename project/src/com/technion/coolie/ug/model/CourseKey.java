package com.technion.coolie.ug.model;

import java.io.Serializable;

public class CourseKey implements Serializable {

	public CourseKey(String id, Semester semester) {
		super();
		this.number = id;
		this.semester = semester;
	}

	private String number;
	private Semester semester;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	private static final long serialVersionUID = 939036143890035323L;

}
