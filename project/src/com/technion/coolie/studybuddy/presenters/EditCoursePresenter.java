package com.technion.coolie.studybuddy.presenters;

import com.technion.coolie.studybuddy.data.DataStore;

public class EditCoursePresenter
{

	private int		courseID	= 0;
	private boolean	isSet		= false;

	public EditCoursePresenter()
	{

	}

	public boolean setCourse(int courseID)
	{
		if (!DataStore.coursesById.containsKey(courseID))
			return false;

		this.courseID = courseID;
		isSet = true;
		return true;
	}

	public String getCourseName()
	{
		if (!isSet)
			return "";

		return DataStore.coursesById.get(courseID).getName();

	}

	public String getCourseIdAsString()
	{
		if (!isSet)
			return "";

		return DataStore.coursesById.get(courseID).getIdAsString();

	}

	public int getCourseResourceAmount(String name)
	{
		if (!isSet)
			return 0;

		return DataStore.coursesById.get(courseID).getResourceTotalItemCount(
				name);
	}

	public void commitCourse(int newCourseId, String courseName,
			int numLectures, int numTutorials)
	{
		if (isSet)
		{
			DataStore.editCourse(courseID, newCourseId, courseName,
					numLectures, numTutorials);
		} else
		{
			DataStore.addCourse(newCourseId, courseName, numLectures,
					numTutorials);
		}

	}

}
