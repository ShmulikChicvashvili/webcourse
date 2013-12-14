package com.technion.coolie.ug.gui.searchCourses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.Semester;

public class SearchFilters implements Serializable {

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
				&& (course.getSemester().equals(semester))
				&& (!hasFreePlaces || course.hasFreePlaces())
				&& (isSubstring(
						query.toLowerCase(Locale.US),
						course.getName().toLowerCase(Locale.US) + " "
								+ course.getCourseNumber()));

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

	private static final long serialVersionUID = 5080644007328929764L;
}
