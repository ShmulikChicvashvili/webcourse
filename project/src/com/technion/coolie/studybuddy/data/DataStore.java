package com.technion.coolie.studybuddy.data;

import java.util.ArrayList;
import java.util.List;

public class DataStore
{
	private static String[] menus = new String[] { "Tasks", "Courses",
			"Crazy mode" };
	public static String[] cources = new String[] { "Data structures",
			"Operation systems", "Algorithems", "Math" };
	public static String[] courseNumbers = new String[] { "234218", "234123",
			"234247", "104014" };
	private static final List<Task> tasks = new ArrayList<Task>();
	static
	{
		tasks.add(new Task(cources[0], Task.TypeVideo, 1));
		tasks.add(new Task(cources[0], Task.TypeVideo, 2));
		tasks.add(new Task(cources[0], Task.TypeLecure, 1));
		tasks.add(new Task(cources[1], Task.TypeVideo, 1));
		tasks.add(new Task(cources[1], Task.TypeVideo, 2));
		tasks.add(new Task(cources[2], Task.TypeVideo, 3));
	}
	public static final int taskForCourse = 14;

	/**
	 * 
	 */
	public DataStore()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public static String getCourse(int position)
	{
		return cources[position];
	}

	public static String getMenu(int position)
	{
		return menus[position];
	}

	public static int getCourcesSize()
	{
		return cources.length;
	}

	public static int getMenuSize()
	{
		return menus.length;
	}

	public static int getTaskSize()
	{
		return tasks.size();
	}

	public static Task getTask(int position)
	{
		return tasks.get(position);
	}

	public static void removeTask(int poistion)
	{
		tasks.remove(poistion);
	}

	public static void removeTask(String item)
	{
		tasks.remove(item);
	}

	public static void addTask(Task task)
	{
		tasks.add(task);
	}

	public static String getCourseNumber(int position)
	{
		return courseNumbers[position];
	}
}
