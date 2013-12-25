package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.CourseItem;

public class PageTwoFragment extends BasePage {

	@Override
	public void setGroupParents() {
		parentItems = new ArrayList<CourseItem>(UGDatabase.getInstance(
				getActivity()).getStudentCourses(SemesterSeason.WINTER));
	}
}
