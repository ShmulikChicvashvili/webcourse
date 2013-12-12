package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageThreeFragment extends BasePage {

	public void setGroupParents() {
		ArrayList<ExamItem> child = new ArrayList<ExamItem>();
		child.add(new ExamItem("מועד א'", "1.1.2014"));
		child.add(new ExamItem("מועד ב'", "5.3.2014"));
		parentItems
				.add(new CourseItem("לוגיקה ותורת הקבוצות", "234244", "4.0",child));
		parentItems.add(new CourseItem("חדוא 1 ת'", "134043", "5.5",child));
		parentItems.add(new CourseItem("כדורסל", "394803", "1.0",child));
		parentItems.add(new CourseItem("אוטומטים ושפות פורמליות", "236350",
				"3.0",child));
		parentItems.add(new CourseItem("מערכות דינמיות", "094123", "2.5",child));
		parentItems.add(new CourseItem("תזמורת", "099342", "2.0",child));
	}
}
