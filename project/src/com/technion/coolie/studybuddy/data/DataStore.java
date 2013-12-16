package com.technion.coolie.studybuddy.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.presenters.EditCoursePresenter;
import com.technion.coolie.studybuddy.presenters.MainPresenter;
import com.technion.coolie.studybuddy.utils.SparseArrayMap;

public class DataStore
{
	private static String[]				menus			= new String[] {
			"Tasks", "Courses", "Crazy mode"			};
	public static List<Course>			coursesList		= new ArrayList<Course>();
	public static Map<Integer, Course>	coursesById		= new SparseArrayMap<Course>();
	public static Semester				semester		= new Semester();
	private static SBDatabaseHelper		dbHelper;

	private static MainPresenter		mainPresenter;
	private static EditCoursePresenter	editPresenter;

	public static final int				taskForCourse	= 14;

	static
	{
		// OpenHelperManager.setOpenHelperClass(SBDatabaseHelper.class);
		addFakeCourses();
	}

	/**
	 * 
	 */
	public DataStore()
	{
		super();
	}

	public static void addFakeCourses()
	{

		coursesList.add(new Course(234123, "Operating systems"));
		coursesList.add(new Course(234247, "Algorithms"));
		coursesList.add(new Course(236353, "Automata and Formal Languages"));
		coursesList.add(new Course(134058, "Biology 1"));

		for (Course c : coursesList)
		{
			int i = 4;
			c.addStudyResources(StudyResource.createWithItems(i--));
		}

		for (Course c : coursesList)
		{
			coursesById.put(c.getId(), c);
		}

		Collections.sort(coursesList);
	}

	public static MainPresenter getMainPresenter()
	{
		if (null == mainPresenter)
		{
			mainPresenter = new MainPresenter();
		}

		return mainPresenter;
	}

	public static EditCoursePresenter getEditCoursePresenter()
	{
		if (null == editPresenter)
		{
			editPresenter = new EditCoursePresenter();
		}

		return editPresenter;
	}

	public static String getMenu(int position)
	{
		return menus[position];
	}

	public static int getMenuSize()
	{
		return menus.length;
	}

	public static void initHelper(Context context)
	{
		dbHelper = OpenHelperManager.getHelper(context, SBDatabaseHelper.class);
	}

	public static void destroyHelper()
	{
		OpenHelperManager.releaseHelper();
		dbHelper = null;
	}

	public static void editCourse(int courseID, int newCourseId,
			String courseName, int numLectures, int numTutorials)
	{
		// TODO Auto-generated method stub

	}

	public static void addCourse(int newCourseId, String courseName,
			int numLectures, int numTutorials)
	{
		// TODO Auto-generated method stub

	}

}
