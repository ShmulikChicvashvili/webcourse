package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;

public class PageOneFragment extends BasePage {

	public void setGroupParents() {
		parentItems = new ArrayList<CourseItem>(
				UGDatabase.INSTANCE.getStudentCourses(SemesterSeason.SUMMER));

	}
}
