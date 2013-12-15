package com.technion.coolie.studybuddy.presenters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.utils.SparseArrayMap;

public class MainPresenterModel {
	private final Map<Integer, Course> coursesById = new SparseArrayMap<Course>();
	private final List<Course> sortedCourses = new ArrayList<Course>();
	private final Semester semester;

	public MainPresenterModel(Semester semester, Collection<Course> courses) {
		this.semester = semester;
		for (Course course : courses) {
			coursesById.put(course.getId(), course);
			sortedCourses.add(course);
		}
		Collections.sort(sortedCourses);
	}

	public String getCourseNameById(int id) {
		return coursesById.get(id).getName();
	}

	public int getCoursesCount() {
		return coursesById.size();
	}

	public String getCourseIdByPosition(int position) {
		return String.valueOf(sortedCourses.get(position).getId());
	}

	public String getCourseNameByPosition(int position) {
		return sortedCourses.get(position).getName();
	}

	public Course getCourseByPosition(int position) {
		return sortedCourses.get(position);
	}

}
