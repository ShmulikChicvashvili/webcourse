package com.technion.coolie.studybuddy.presenters;

import com.technion.coolie.studybuddy.data.DataStore;

public class MainPresenter
{

	public MainPresenter()
	{
	}

	public String getCourseNameById(int id)
	{
		return DataStore.coursesById.get(id).getName();
	}

	public int getCoursesCount()
	{
		return DataStore.coursesList.size();
	}

	public String getCourseIdStringByPosition(int position)
	{
		return DataStore.coursesList.get(position).getIdAsString();
	}

	public String getCourseNameByPosition(int position)
	{
		return DataStore.coursesList.get(position).getName();
	}

}
