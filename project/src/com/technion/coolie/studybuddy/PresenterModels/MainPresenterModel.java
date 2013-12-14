package com.technion.coolie.studybuddy.PresenterModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.util.SparseArray;

import com.technion.coolie.studybuddy.Models.Course;
import com.technion.coolie.studybuddy.Models.Semester;
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

	public Course getCourseByPosition(int position) {
		Course c = sortedCourses.get(position);
		if (c == null) {
			Log.e("MPM", "course is null");
		} else {
			Log.i("MPM", "course = " + c);
		}

		return c;
	}

}
