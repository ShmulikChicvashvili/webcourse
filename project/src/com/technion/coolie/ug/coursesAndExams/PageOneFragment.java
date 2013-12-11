package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageOneFragment extends BasePage {

	public void setGroupParents() {
		ArrayList<ExamItem> child = new ArrayList<ExamItem>();
		child.add(new ExamItem("מועד א'", "1.1.2014"));
		child.add(new ExamItem("מועד ב'", "5.3.2014"));
		parentItems.add(new CourseItem("מבוא למדעי המחשב", "234114", "3.0",
				child));
		parentItems.add(new CourseItem("מקבוזר", "236370", "3.0", child));
		parentItems.add(new CourseItem("כימיה כללית", "111111", "3.5", child));
		parentItems
				.add(new CourseItem("יפנית למתחילים", "398393", "2.5", child));
	}
}
