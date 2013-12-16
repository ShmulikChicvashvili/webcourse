package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.CourseItem;

public class PageOneFragment extends BasePage {

	@Override
	public void setGroupParents() {
		// TODO: need to be UGDatabase.INSTANCE.<name of variable which will
		// store this list>...
		parentItems = new ArrayList<CourseItem>(
				UGDatabase.INSTANCE.getStudentCourses(SemesterSeason.SUMMER));

	}
}
