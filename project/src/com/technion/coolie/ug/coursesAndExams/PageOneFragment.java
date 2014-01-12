package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.client.ServerAsyncCommunication;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.Semester;

public class PageOneFragment extends BasePage {

	@Override
	public void setGroupParents() {
		// TODO: need to be UGDatabase.INSTANCE.<name of variable which will
		// store this list>...
		Semester s = new Semester(2012, SemesterSeason.SUMMER);
		//ServerAsyncCommunication.getAllExamsFromClient(s, "1636", "11111100",	MainActivity.context);
//		parentItems = new ArrayList<CourseItem>(UGDatabase.getInstance(
//				getActivity()).getStudentCourses(SemesterSeason.SUMMER));

		
		parentItems = new ArrayList<CourseItem>(UGDatabase.getInstance(
				getActivity()).getCoursesAndExams());
	}
}
