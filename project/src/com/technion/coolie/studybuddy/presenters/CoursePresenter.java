package com.technion.coolie.studybuddy.presenters;

import java.util.Date;
import java.util.Map;

import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.models.Course;

public class CoursePresenter
{
	String	courseNumber;
	Course	course;

	public CoursePresenter(String courseNumber)
	{
		this.courseNumber = courseNumber;
		course = DataStore.coursesById.get(courseNumber);
	}

	public int getCurrentWeekNum(Date today)
	{
		return DataStore.semester.getSemesterWeek(today);
	}

	public Map<String, Integer> getProgressMap()
	{
		return course.getProgressMap();
	}

	public int getSemesterLength()
	{
		return DataStore.semester.WEEKS_IN_SEMESTER;
	}

}
