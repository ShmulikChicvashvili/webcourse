package com.technion.coolie.ug;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;
import com.technion.coolie.ug.calendar.AcademicCalendarListFragment;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsListFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.tecnion.coolie.ug.utils.FragmentsFactory;

public class MainActivity extends CoolieActivity implements
		OnRightMenuItemSelected {

	public static final String DEBUG_TAG = "DEBUG";
	public static Context context;
	//private static Fragment defaultHorisontalFragment = FragmentsFactory.getGradesSheetLargeFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.ug_main_screen);
		//getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, defaultHorisontalFragment).commit();
		
		// startActivity(new Intent(this, SearchActivity.class));
		// startActivity(new Intent(this, GradesSheetActivity.class));
	}
	//GRADES_SHEET, COURSES_AND_EXAMS, ACADEMIC_CALENDAR, PAYMENTS, TRACKING_COURSES, REGISTRATION
	@Override
	public void onLeftMenuItemSelected(LandscapeLeftMenuItems item) {
		Fragment f = null;
		switch (item) {
		case GRADES_SHEET:
			f = FragmentsFactory.getGradesSheetLargeFragment();
			break;
		case COURSES_AND_EXAMS:
			 f = FragmentsFactory.getCoursesAndExamsLargeFragment();
			break;
		case ACADEMIC_CALENDAR:
			f = FragmentsFactory.getAcademicCalendarLargeFragment();
			break;
		case PAYMENTS:
			return;
		case TRACKING_COURSES:
			//f = new TrackingCoursesListFragment();
			return;
		case COURSES_SEARCH:
			f = FragmentsFactory.getSearchCorsesLargeFragment();
			break;
		
		default:
			f = new GradesSheetFragment();
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.detail_container, f).commit();
	}

}
