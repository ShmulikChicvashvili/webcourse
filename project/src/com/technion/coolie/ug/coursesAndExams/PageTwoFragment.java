package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageTwoFragment extends BasePage {

		public void setGroupParents() {
		parentItems.add(new CourseItem("מבוא לתורת הצפינה", "234443", "3.0"));
		parentItems.add(new CourseItem("מפרטים פורמליים", "236342", "5.0"));
		parentItems.add(new CourseItem("תורת החישוביות", "236343", "3.0"));
		parentItems.add(new CourseItem("תורת הקומפילציה", "236360", "4.5"));
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
