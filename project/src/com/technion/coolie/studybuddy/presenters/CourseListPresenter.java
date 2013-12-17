package com.technion.coolie.studybuddy.presenters;

import com.technion.coolie.studybuddy.data.DataStore;

public class CourseListPresenter
{

	public CourseListPresenter()
	{
	}

	public String getNameById(int id)
	{
		return DataStore.coursesById.get(id).getName();
	}

	public int getCount()
	{
		return DataStore.coursesList.size();
	}

	public String getIdByPosition(int position)
	{
		return DataStore.coursesList.get(position).getIdAsString();
	}

	public String getNameByPosition(int position)
	{
		return DataStore.coursesList.get(position).getName();
	}

}
