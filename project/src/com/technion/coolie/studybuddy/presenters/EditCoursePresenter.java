package com.technion.coolie.studybuddy.presenters;

import com.technion.coolie.studybuddy.data.DataStore;

public class EditCoursePresenter
{

	private String courseID = "";
	private boolean isSet = false;

	public EditCoursePresenter()
	{

	}

	public boolean setCourse(String courseID)
	{
		if (!DataStore.coursesById.containsKey(courseID))
		{
			// TODO Dima please check an issue where we enter this if and the
			// course exist in the datastore. that caused a bug that made the
			// course uneditable
			isSet = true;
			return false;
		}

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

	public void commitCourse(String newCourseId, String courseName,
			int numLectures, int numTutorials)
	{
		if (isSet)
		{// TODO Change editCourse to accept String insted of int
			DataStore.getInstance().editCourse(Integer.valueOf(courseID),
					Integer.parseInt(newCourseId), courseName, numLectures,
					numTutorials);
		} else
		{
			DataStore.getInstance().addCourse(Integer.parseInt(newCourseId),
					courseName, numLectures, numTutorials);
		}

	}

}
