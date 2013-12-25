package com.technion.coolie.ug;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;
import com.technion.coolie.ug.db.UGDatabaseHelper;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.utils.FragmentsFactory;

public class MainActivity extends CoolieActivity implements
		OnRightMenuItemSelected {

	public static final String DEBUG_TAG = "DEBUG";
	public static Context context;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		try {
//			Dao<CourseRow, String> courseDao = getHelper(this).getCoursesDao();
//			
//			Course course = new Course(
//					"233245",
//					"מבוא לבינה מלאכותית",
//					2.0f,
//					"During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
//					new Semester(2013, SemesterSeason.WINTER),
//					Faculty.CS, new GregorianCalendar(2014,
//							2, 11), new GregorianCalendar(2014, 2,
//							11), null, null, null);
//			
//			Course course2 = new Course(
//					"273455",
//					"אמא ודני הלכו לים",
//					2.0f,
//					"During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
//					new Semester(2013, SemesterSeason.WINTER),
//					Faculty.CS, new GregorianCalendar(2014,
//							2, 11), new GregorianCalendar(2014, 2,
//							11), null, null, null);
//			
//			courseDao.createOrUpdate(new CourseRow(course,course.getCourseKey()));
//			courseDao.createOrUpdate(new CourseRow(course2,course2.getCourseKey()));
//			Log.d(this.getClass().getName(), "OMG ADDED COURSE TO DB");
//			CourseRow r = courseDao.queryForSameId(new CourseRow(course,course.getCourseKey()));
//			Log.d(this.getClass().getName(), "OMG GOT COURSE "+r.getCourse().getName()+" TO DB");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		context = getApplicationContext();
		setContentView(R.layout.ug_main_screen);
	}
	
	
	
	private UGDatabaseHelper UGDatabaseHelper = null;

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (UGDatabaseHelper != null) {
	        OpenHelperManager.releaseHelper();
	        UGDatabaseHelper = null;
	    }
	}

	private UGDatabaseHelper getHelper(Context context) {
	    if (UGDatabaseHelper == null) {
	        UGDatabaseHelper = (UGDatabaseHelper)OpenHelperManager.getHelper(context, UGDatabaseHelper.class);
	    }
	    return UGDatabaseHelper;
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

}
