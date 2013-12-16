package com.technion.coolie.studybuddy.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;
import java.util.Observable;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.presenters.EditCoursePresenter;
import com.technion.coolie.studybuddy.presenters.MainPresenter;
import com.technion.coolie.studybuddy.utils.SparseArrayMap;

public class DataStore extends Observable
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
	public static final String			CLASS_LIST		= "classes";

	static
	{
		// OpenHelperManager.setOpenHelperClass(SBDatabaseHelper.class);
		addFakeCourses();
	}

	private static DataStore			dataStore;

	public static DataStore getInstance()
	{
		if (dataStore == null)
		{
			dataStore = new DataStore();
		}

		return dataStore;
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
			c.addStudyResources(StudyResource.createWithItems(
					StudyResource.LECTURES, i--));
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

	public void editCourse(int courseID, int newCourseId, String courseName,
			int numLectures, int numTutorials)
	{
		Course c = coursesById.get(courseID);

		c.setID(newCourseId);
		c.setName(courseName);
		c.resizeStudyResource(StudyResource.LECTURES, numLectures);
		c.resizeStudyResource(StudyResource.TUTORIALS, numTutorials);

		if (courseID != newCourseId)
		{
			coursesById.remove(courseID);
			coursesById.put(newCourseId, c);

			// TODO add persistance logic
		}

		// TODO add persistance logic
		setChanged();
		notifyObservers(DataStore.CLASS_LIST);

	}

	public void addCourse(int newCourseId, String courseName, int numLectures,
			int numTutorials)
	{
		Course c = new Course(newCourseId, courseName);
		c.addStudyResource(StudyResource.createWithItems(
				StudyResource.LECTURES, numLectures));
		c.addStudyResource(StudyResource.createWithItems(
				StudyResource.TUTORIALS, numTutorials));

		coursesList.add(c);
		coursesById.put(newCourseId, c);
		Collections.sort(coursesList);

		setChanged();
		notifyObservers(DataStore.CLASS_LIST);

	}
}
