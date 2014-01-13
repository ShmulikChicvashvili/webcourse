package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.List;

public class GroupOfCourses implements Serializable {

	List<String> courses;

	GroupOfCourses() {

	}

	public GroupOfCourses(final List<String> courses) {
		super();
		this.courses = courses;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(final List<String> courses) {
		this.courses = courses;
	}

	private static final long serialVersionUID = 4294358536463302320L;

}
