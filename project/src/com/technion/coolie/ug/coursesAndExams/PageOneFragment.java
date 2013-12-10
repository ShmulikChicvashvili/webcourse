package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageOneFragment extends BasePage {

		public void setGroupParents() {
		parentItems.add(new CourseItem("מבוא למדעי המחשב", "234114", "3.0"));
		parentItems.add(new CourseItem("מקבוזר", "236370", "3.0"));
		parentItems.add(new CourseItem("כימיה כללית", "111111", "3.5"));
		parentItems.add(new CourseItem("יפנית למתחילים", "398393", "2.5"));
	}

	public void setChildData() {

		// Android
		ArrayList<String> child = new ArrayList<String>();
		child.add("Core");
		child.add("Games");
		childItems.add(child);

		// Core Java
		child = new ArrayList<String>();
		child.add("Apache");
		child.add("Applet");
		child.add("AspectJ");
		child.add("Beans");
		child.add("Crypto");
		childItems.add(child);

		// Desktop Java
		child = new ArrayList<String>();
		child.add("Accessibility");
		child.add("AWT");
		child.add("ImageIO");
		child.add("Print");
		childItems.add(child);

		// Enterprise Java
		child = new ArrayList<String>();
		child.add("EJB3");
		child.add("GWT");
		child.add("Hibernate");
		child.add("JSP");
		childItems.add(child);
	}
}
