package com.technion.coolie.studybuddy.data;

import java.util.ArrayList;
import java.util.List;

import com.technion.coolie.studybuddy.Models.Course;
import com.technion.coolie.studybuddy.Models.Semester;
import com.technion.coolie.studybuddy.Models.StudyResource;
import com.technion.coolie.studybuddy.PresenterModels.MainPresenterModel;

public class DataStore {
	private static String[] menus = new String[] { "Tasks", "Courses",
			"Crazy mode" };
	private static List<Course> courses = new ArrayList<Course>();
	private static Semester semester = new Semester();
	private static MainPresenterModel mainPresenterModel;

	public static final int taskForCourse = 14;

	/**
	 * 
	 */
	public DataStore() {
		super();
		addFakeCourses();
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
		if (null == mainPresenterModel) {
			mainPresenterModel = new MainPresenterModel(semester, courses);
		}

		return mainPresenterModel;
	}

	// public public static Course getCourse(int position) {
	// return courses.get(position);
	// }
	//
	// public static String getMenu(int position) {
	// return menus[position];
	// }
	//
	// public static int getCoursesCount() {
	// return courses.size();
	// }
	//
	// public static int getMenuSize() {
	// return menus.length;
	// }
	//
	// public static int getTaskSize() {
	// return tasks.size();
	// }
	//
	// public static Task getTask(int position) {
	// return tasks.get(position);
	// }
	//
	// public static void removeTask(int poistion) {
	// tasks.remove(poistion);
	// }
	//
	// public static void removeTask(String item) {
	// tasks.remove(item);
	// }
	//
	// public static void addTask(Task task) {
	// tasks.add(task);
	// }
	//
	// public static String getCourseNumber(int position) {
	// return courseNumbers[position];
	// }
}
