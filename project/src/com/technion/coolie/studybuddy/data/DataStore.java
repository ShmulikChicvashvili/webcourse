package com.technion.coolie.studybuddy.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.preference.PreferenceManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.studybuddy.exceptions.CourseAlreadyExistsException;
import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.models.WorkStats;
import com.technion.coolie.studybuddy.presenters.CourseListPresenter;
import com.technion.coolie.studybuddy.presenters.CoursePresenter;
import com.technion.coolie.studybuddy.presenters.EditCoursePresenter;

public class DataStore extends Observable
{
	private static String[]				menus			= new String[] {
			"Tasks", "Courses", "Crazy mode"			};

	public static final String			SEMESTERSTART	= "stb_semester_start";
	public static final String			SEMESTERLENGTH	= "stb_simester_length";

	public static Set<Course>			coursesSet		= new HashSet<Course>();
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

	public static DataStore getInstance()
	{
		if (dataStore == null)
		{
			dataStore = new DataStore();
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

	private Context		context;

	private WorkStats	workStats;

	/**
	 * 
	 */
	private DataStore()
	{

		OpenHelperManager.setOpenHelperClass(SBDatabaseHelper.class);
		loadCourses();
		loadWorkStats();
	}

	public void addCourse(String newCourseId, String courseName,
			int numLectures, int numTutorials)
			throws CourseAlreadyExistsException
	{

		if (coursesById.containsKey(newCourseId))
			throw new CourseAlreadyExistsException();

		Course c = new Course(newCourseId, courseName);
		c.addStudyResource(StudyResource.createWithItems(
				StudyResource.LECTURES, numLectures));
		c.addStudyResource(StudyResource.createWithItems(
				StudyResource.TUTORIALS, numTutorials));

		coursesSet.add(c);
		coursesList.add(c);
		coursesById.put(String.valueOf(newCourseId), c);
		Collections.sort(coursesList);

		setChanged();
		notifyObservers(DataStore.CLASS_LIST);

	}

	public void editCourse(String courseID, String newCourseId,
			String courseName, int numLectures, int numTutorials)
			throws CourseAlreadyExistsException
	{

		if (coursesById.containsKey(newCourseId))
			throw new CourseAlreadyExistsException();

		Course c = coursesById.get(courseID);

		c.setID(newCourseId);
		c.setName(courseName);
		c.resizeStudyResource(StudyResource.LECTURES, numLectures);
		c.resizeStudyResource(StudyResource.TUTORIALS, numTutorials);

		if (courseID != newCourseId)
		{
			coursesById.remove(courseID);
			coursesById.put(String.valueOf(newCourseId), c);

			// TODO add persistance logic
		}

		// TODO add persistance logic
		setChanged();
		notifyObservers(DataStore.CLASS_LIST);
	}

	public CoursePresenter getCoursePresenter(String courseNumber)
	{
		return new CoursePresenter(courseNumber);
	}

	public Integer[] getWorkStats(Date today, int days)
	{

		return workStats.getStatsLastXDays(today, days);

	}

	public void initContext(Context context)
	{
		this.context = context.getApplicationContext();
		semester.setStartDate(parseStartDateFromPreferences(context));
	}

	public void loadCourses()
	{

		try
		{
			addCourse("234123", "Operating systems", 14, 14);
			addCourse("234247", "Algorithms", 12, 12);
			addCourse("236353", "Automata and Formal Languages", 12, 12);
			addCourse("134058", "Biology 1", 12, 12);
		} catch (CourseAlreadyExistsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadWorkStats()
	{
		workStats = new WorkStats();

	}
}
