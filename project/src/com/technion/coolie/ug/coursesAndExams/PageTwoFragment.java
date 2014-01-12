package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.client.ServerAsyncCommunication;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.Semester;

public class PageTwoFragment extends BasePage {

	@Override
	public void setGroupParents() {
		
		Semester s = new Semester(2013, SemesterSeason.WINTER);
		//ServerAsyncCommunication.getAllExamsFromClient(s,  "1636", "11111100",	MainActivity.context);
		
//		parentItems = new ArrayList<CourseItem>(UGDatabase.getInstance(
//				getActivity()).getStudentCourses(SemesterSeason.WINTER));
	
		parentItems = new ArrayList<CourseItem>(UGDatabase.getInstance(
				getActivity()).getCoursesAndExams());
	}
}
