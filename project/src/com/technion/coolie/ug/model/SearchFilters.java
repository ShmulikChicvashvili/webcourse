package com.technion.coolie.ug.model;

import java.util.ArrayList;
import java.util.List;

import com.technion.coolie.ug.Enums.Faculty;

public class SearchFilters {

	Semester semester; // There are only three available semesters at any time.
						// you can only choose one.
	boolean hasFreePlaces;
	Faculty faculty;

	public SearchFilters(Semester semester, boolean hasFreePlaces,
			Faculty faculty) {
		super();
		this.semester = semester;
		this.hasFreePlaces = hasFreePlaces;
		this.faculty = faculty;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public boolean isHasFreePlaces() {
		return hasFreePlaces;
	}

	public void setHasFreePlaces(boolean hasFreePlaces) {
		this.hasFreePlaces = hasFreePlaces;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public List<Course> filter(List<Course> courses, String query) {
		List<Course> filteredList = new ArrayList<Course>();
		for (Course course : courses) {
			if (meetCriterions(course, query))
				filteredList.add(course);
		}
		return filteredList;
	}

	private boolean meetCriterions(Course course, String query) {
		return (faculty == Faculty.ALL_FACULTIES || course.getFaculty() == faculty)
				&& (course.getSemester().ss == semester.ss && course
						.getSemester().year == semester.year)
				&& (!hasFreePlaces || course.hasFreePlaces())
				&& (isSubstring(query,
						course.getName() + " " + course.getCourseNumber()));

	}

	/**
	 * checks for each word in the query and checks that its in the string
	 */
	private boolean isSubstring(String query, String strOnCheck) {
		for (String word : query.split(" "))
			if (strOnCheck.indexOf(word) == -1)
				return false;

		return true;
	}
}
