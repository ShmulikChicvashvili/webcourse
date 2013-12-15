package com.technion.coolie.studybuddy.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import com.technion.coolie.studybuddy.Models.Course;
import com.technion.coolie.studybuddy.Models.Semester;
import com.technion.coolie.studybuddy.Models.StudyResource;
import com.technion.coolie.studybuddy.PresenterModels.MainPresenterModel;

public class DataStore {
	private static String[] menus = new String[] { "Tasks", "Courses",
			"Crazy mode" };
	private static List<Course> courses = new ArrayList<Course>();
	private static Semester semester = new Semester();
	private static SBDatabaseHelper dbHelper;

	private static MainPresenterModel mainPresenter;

	public static final int taskForCourse = 14;

	static {
		// OpenHelperManager.setOpenHelperClass(SBDatabaseHelper.class);
		addFakeCourses();
	}

	/**
	 * 
	 */
	public DataStore() {
		super();
	}

	public static void addFakeCourses() {

		courses.add(new Course(234123, "Operating systems"));
		courses.add(new Course(234247, "Algorithms"));
		courses.add(new Course(236353, "Automata and Formal Languages"));
		courses.add(new Course(134058, "Biology 1"));

		for (Course c : courses) {
			int i = 4;
			c.addStudyResources(StudyResource.createWithItems(i--));
		}

	}

	public static MainPresenterModel getMainPresenterModel() {
		if (null == mainPresenter) {
			mainPresenter = new MainPresenterModel(semester, courses);
		}

		return mainPresenter;
	}

	public static String getMenu(int position) {
		return menus[position];
	}

	public static int getMenuSize() {
		return menus.length;
	}

	public static void initHelper(Context context) {
		dbHelper = OpenHelperManager.getHelper(context, SBDatabaseHelper.class);
	}

	public static void destroyHelper() {
		OpenHelperManager.releaseHelper();
		dbHelper = null;
	}
}
