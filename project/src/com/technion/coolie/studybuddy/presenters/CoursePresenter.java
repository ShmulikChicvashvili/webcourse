package com.technion.coolie.studybuddy.presenters;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.WorkStats;

public class CoursePresenter extends Observable
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

	public String getResourceName(int itemPosition)
	{
		return course.getResourceName(itemPosition);
	}

	public int getSemesterLength()
	{
		return DataStore.semester.WEEKS_IN_SEMESTER;
	}

	public List<String> getStudyItemsDone(String resourceName)
	{
		return course.getStudyItemsDone(resourceName);
	}

	public List<String> getStudyItemsRemaining(String resourceName)
	{
		return course.getStudyItemsRemaining(resourceName);
	}

	public List<String> getStudyItemsAll(String resourceName)
	{
		return course.getStudyItemsLabels(resourceName);
	}

	public boolean isTaskDone(String resourceName, int position)
	{
		return course.isTaskDone(resourceName, position);
	}

	public void toggleTask(String resourceName, int position)
	{
		course.toggleTask(resourceName, position);

		if (course.isTaskDone(resourceName, position))
		{
			WorkStats.getInstance().increaseDoneForDate(new Date());
		} else
		{
			WorkStats.getInstance().decreaseDoneForDate(new Date());
		}
		DataStore.getInstance().notifyCourseAdapters(course);

		setChanged();
		notifyObservers();
	}

}
