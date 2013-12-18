package com.technion.coolie.studybuddy.data;

import static com.technion.coolie.studybuddy.data.DataStore.getHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.preference.PreferenceManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.studybuddy.exceptions.CourseAlreadyExistsException;
import com.technion.coolie.studybuddy.models.CompositeElement;
import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;
import com.technion.coolie.studybuddy.presenters.CourseListPresenter;
import com.technion.coolie.studybuddy.presenters.CoursePresenter;
import com.technion.coolie.studybuddy.presenters.EditCoursePresenter;

public class DataStore extends Observable implements CompositeElement
{
	private static String[]				menus			= new String[] {
					"Tasks", "Courses"					};

	public static final String			SEMESTERSTART	= "stb_semester_start";
	public static final String			SEMESTERLENGTH	= "stb_simester_length";

	public static List<Course>			coursesList		= new ArrayList<Course>();
	public static Map<String, Course>	coursesById		= new LinkedHashMap<String, Course>();
	public static Semester				semester		= new Semester();
	private static SBDatabaseHelper		dbHelper;

	private static CourseListPresenter	mainPresenter;
	private static EditCoursePresenter	editPresenter;

	public static final int				taskForCourse	= 14;
	public static final String			CLASS_LIST		= "classes";

	static
	{
		// OpenHelperManager.setOpenHelperClass(SBDatabaseHelper.class);
		// loadCourses();
	}

	private static DataStore			dataStore;

	private static Context				context;

	private WorkStats					workStats;

	public static void destroyHelper()
	{
		OpenHelperManager.releaseHelper();
		dbHelper = null;
	}

	public static EditCoursePresenter getEditCoursePresenter()
	{
		if (null == editPresenter)
		{
			editPresenter = new EditCoursePresenter();
		}

		return editPresenter;
	}

	public static SBDatabaseHelper getHelper()
	{
		return dbHelper;

	}

	public static DataStore getInstance()
	{
		if (dataStore == null)
		{
			dataStore = new DataStore(context);
		}

		return dataStore;
	}

	public static CourseListPresenter getMainPresenter()
	{
		if (null == mainPresenter)
		{
			mainPresenter = new CourseListPresenter();
		}

		return mainPresenter;
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

	public static void setContext(Context context)
	{
		DataStore.context = context.getApplicationContext();
	}

	@SuppressLint("SimpleDateFormat")
	private static Date parseStartDateFromPreferences(Context context)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		try
		{
			return format.parse(PreferenceManager.getDefaultSharedPreferences(
							context).getString(SEMESTERSTART, "1970.01.01"));
		} catch (ParseException e)
		{
		}
		return new Date();
	}

	/**
	 * 
	 */
	private DataStore(Context context)
	{
		OpenHelperManager.setOpenHelperClass(SBDatabaseHelper.class);
		RecursiveDBLoader.getInstance().visit(this);
		semester.setStartDate(parseStartDateFromPreferences(context));
	}

	@Override
	public void accept(CompositeVisitor cv)
	{

		if (semester != null)
		{
			cv.visit(semester);
		}

		cv.visit(WorkStats.getInstance());

		for (Course c : coursesList)
		{
			cv.visit(c);
		}

	}

	public void addCourse(Course c)
	{
		coursesList.add(c);
		coursesById.put(c.getId(), c);
		Collections.sort(coursesList);

	}

	public void addCourse(	String newCourseId,
							String courseName,
							int numLectures,
							int numTutorials)
					throws CourseAlreadyExistsException
	{

		if (coursesById.containsKey(newCourseId))
			throw new CourseAlreadyExistsException();

		Course c = new Course(newCourseId, courseName);
		c.addStudyResource(StudyResource.createWithItems(
						StudyResource.LECTURES, numLectures));
		c.addStudyResource(StudyResource.createWithItems(
						StudyResource.TUTORIALS, numTutorials));

		addCourse(c);

		RecursiveDBStorer.getInstance().visit(c);

		setChanged();
		notifyObservers(DataStore.CLASS_LIST);

	}

	public void editCourse(	String courseID,
							String newCourseId,
							String courseName,
							int numLectures,
							int numTutorials)
					throws CourseAlreadyExistsException
	{

		Course c = coursesById.get(courseID);

		if (false == newCourseId.equals(courseID))
		{
			if (coursesById.containsKey(newCourseId))
				throw new CourseAlreadyExistsException();

			coursesById.remove(courseID);
			// this also updates the object!
			dbHelper.getCourseDao().updateId(c, newCourseId);

			coursesById.put(String.valueOf(newCourseId), c);
		}

		c.setName(courseName);
		c.resizeStudyResource(StudyResource.LECTURES, numLectures);
		c.resizeStudyResource(StudyResource.TUTORIALS, numTutorials);

		RecursiveDBStorer.getInstance().visit(c);

		setChanged();
		notifyObservers(DataStore.CLASS_LIST);
	}

	public CoursePresenter getCoursePresenter(String courseNumber)
	{
		return new CoursePresenter(courseNumber);
	}

	public Integer[] getWorkStats(Date today, int days)
	{

		return WorkStats.getInstance().getStatsLastXDays(today, days);

	}

	public void notifyCourseAdapters(Course course)
	{
		setChanged();
		notifyObservers();
		// RecursiveDBStorer.getInstance().visit(course);
	}

	public void deleteCourse(String courseNumber)
	{
		Course c = coursesById.remove(courseNumber);
		coursesList.remove(c);

	}

}
