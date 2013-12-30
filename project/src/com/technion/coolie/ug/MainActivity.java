package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer.TrackInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.utils.FragmentsFactory;
import com.technion.coolie.ug.tracking.*;
import com.technion.coolie.ug.gui.searchCourses.*;

public class MainActivity extends CoolieActivity implements
		OnRightMenuItemSelected {

	public static final String DEBUG_TAG = "DEBUG";
	public static Context context;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getApplicationContext();
		setContentView(R.layout.ug_main_screen);

		updateCourses();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// close database connections
		if (isFinishing())
			UGDatabase.getInstance(this).clean();
	}

	/**
	 * GRADES_SHEET, COURSES_AND_EXAMS, ACADEMIC_CALENDAR, PAYMENTS,
	 * TRACKING_COURSES, REGISTRATION
	 */
	@Override
	public void onLeftMenuItemSelected(final LandscapeLeftMenuItems item) {
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
			// f = new TrackingCoursesListFragment();
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

	@Override
	public void onBackPressed() {
		finish();
	}

	private void updateCourses() {
		Course course = new Course(
				"233245",
				"×ž×‘×•×� ×œ×‘×™× ×” ×ž×œ×�×›×•×ª×™×ª",
				2.0f,
				"During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
				new Semester(2013, SemesterSeason.WINTER), Faculty.CS,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, null);

		Course course2 = new Course(
				"273455",
				"×�×ž×� ×•×“× ×™ ×”×œ×›×• ×œ×™×�",
				2.0f,
				"During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
				new Semester(2013, SemesterSeason.WINTER), Faculty.CS,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), null, null, null);

		List<Course> courses = new ArrayList<Course>();
		courses.add(course);
		courses.add(course2);

		UGDatabase.getInstance(this).updateCourses(courses);

	}
	
	public void onRegistrationClick(View v) 
	{
		UGDatabase db = UGDatabase.getInstance(this);
		
		CourseKey ck = new CourseKey("104", new Semester(2011, SemesterSeason.WINTER));
		//db.addTrackingCourseToServer(db.getCurrentLoginObject(),ck);
		//db.deleteTrackingCourseFromServer(db.getCurrentLoginObject(),ck);
		
		final Intent intent = new Intent(this,TransparentActivity.class);
		final Bundle b = new Bundle();
		b.putString("key", TrackingCoursesFragment.class.toString());
		intent.putExtras(b);
		startActivity(intent);
    }  
}
