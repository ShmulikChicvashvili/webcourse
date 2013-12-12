package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageTwoFragment extends BasePage {

	public void setGroupParents() {
		ArrayList<ExamItem> child = new ArrayList<ExamItem>();
		child.add(new ExamItem("מועד א'", "1.1.2014"));
		child.add(new ExamItem("מועד ב'", "5.3.2014"));
		parentItems.add(new CourseItem("מבוא לתורת הצפינה", "234443", "3.0",child));
		parentItems.add(new CourseItem("מפרטים פורמליים", "236342", "5.0",child));
		parentItems.add(new CourseItem("תורת החישוביות", "236343", "3.0",child));
		parentItems.add(new CourseItem("תורת הקומפילציה", "236360", "4.5",child));
	}
}
