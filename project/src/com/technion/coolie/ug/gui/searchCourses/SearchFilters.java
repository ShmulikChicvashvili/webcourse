package com.technion.coolie.ug.gui.searchCourses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.Faculty;
import com.technion.coolie.ug.model.Semester;

public class SearchFilters implements Serializable {

	Semester semester; // There are only three available semesters at any time.
						// you can only choose one.
	boolean hasFreePlaces;
	Faculty faculty;

	public SearchFilters(final Semester semester, final boolean hasFreePlaces,
			final Faculty faculty) {
		super();
		this.semester = semester;
		this.hasFreePlaces = hasFreePlaces;
		this.faculty = faculty;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	public boolean isHasFreePlaces() {
		return hasFreePlaces;
	}

	public void setHasFreePlaces(final boolean hasFreePlaces) {
		this.hasFreePlaces = hasFreePlaces;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(final Faculty faculty) {
		this.faculty = faculty;
	}

	public List<Course> filter(final List<Course> courses, final String query) {
		final List<Course> filteredList = new ArrayList<Course>();
		for (final Course course : courses)
			if (meetCriterions(course, query))
				filteredList.add(course);
		return filteredList;
	}

	private boolean meetCriterions(final Course course, final String query) {
		return (faculty == Faculty.ALL_FACULTIES || course.getFaculty() == faculty)
				&& course.getSemester().equals(semester)
				&& (!hasFreePlaces || course.hasFreePlaces())
				&& isSubstring(
						query.toLowerCase(Locale.US),
						course.getName().toLowerCase(Locale.US) + " "
								+ course.getCourseNumber());

	}

	/**
	 * checks for each word in the query and checks that its in the string
	 */
	private boolean isSubstring(final String query, final String strOnCheck) {
		for (final String word : query.split(" "))
			if (strOnCheck.indexOf(word) == -1)
				return false;

		return true;
	}

	private static final long serialVersionUID = 5080644007328929764L;
}
