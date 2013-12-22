package com.technion.coolie.ug.model;

import java.util.List;

public class GroupOfCourses {

	List<String> courses;

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

}
