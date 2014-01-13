package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.calendar.AcademicCalendarFragment;
import com.technion.coolie.ug.calendar.AcademicCalendarListFragment;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetListFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchForTrackingFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.ExamItem;
import com.technion.coolie.ug.model.Faculty;
import com.technion.coolie.ug.model.GroupOfCourses;
import com.technion.coolie.ug.model.Meeting;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;
import com.technion.coolie.ug.tracking.TrackingCoursesFragment;
import com.technion.coolie.ug.utils.FragmentsFactory;
import com.technion.coolie.ug.utils.UGCurrentState;

public class MainActivity extends CoolieActivity implements
		OnRightMenuItemSelected, ITrackingCourseTrasferrer {

	public static final String DEBUG_TAG = "DEBUG";
	public static Context context;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();

		// ServerAsyncCommunication.mainActivity = this;
		setContentView(R.layout.ug_main_screen);

		updateFakeCourses();

		// ServerAsyncCommunication.getCalendarEventsFromServer(); // Will not
		// be here
		// ServerAsyncCommunication.getGradesSheetfromServer(); // Will not be
		// here
		// ServerAsyncCommunication.getAllExams(new Semester
		// (2013,SemesterSeason.WINTER));
		// ServerAsyncCommunication.getAllCoursesFromServer();
		// ServerAsyncCommunication.getCurentSemestersFromClient(null);
		// ServerAsyncCommunication.registrate("094412", "11", "1636",
		// "11111100");
		// Semester s = new Semester(2013, SemesterSeason.SPRING);
		// ServerAsyncCommunication.getAllExamsFromClient(s, "1636", "11111100",
		// this);

		// ServerAsyncCommunication.registrate("094412", "11", "1636",
		// "11111100");
		// ServerAsyncCommunication.unRegistrate("094412", "1636", "11111100",
		// context);

		if (UGCurrentState.getRotationAngle(this) % 2 == 1) {
			Fragment f;
			if (UGCurrentState.currentOpenFragment == "GradesSheetFragment") {
				f = new GradesSheetFragment();

			} else if (UGCurrentState.currentOpenFragment == "AcademicCalendarFragment") {
				f = new AcademicCalendarFragment();
			} else if (UGCurrentState.currentOpenFragment == "CoursesAndExamsFragment") {
				f = new CoursesAndExamsFragment();
			} else if (UGCurrentState.currentOpenFragment == "TrackingCoursesFragment") {
				f = new TrackingCoursesFragment();
			} else {
				f = new SearchFragment();
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.detail_container, f).commit();
		} else {
			if (UGCurrentState.currentOpenFragment != "none") {
				Intent intent = new Intent(this, TransparentActivity.class);
				Bundle b = new Bundle();

				if (UGCurrentState.currentOpenFragment == "GradesSheetFragment") {
					b.putString("key", GradesSheetFragment.class.toString());
				} else if (UGCurrentState.currentOpenFragment == "AcademicCalendarFragment") {
					b.putString("key",
							AcademicCalendarFragment.class.toString());
				} else if (UGCurrentState.currentOpenFragment == "CoursesAndExamsFragment") {
					b.putString("key", CoursesAndExamsFragment.class.toString());
				} else if (UGCurrentState.currentOpenFragment == "TrackingCoursesFragment") {
					b.putString("key", TrackingCoursesFragment.class.toString());
				}
				if (b.getString("key") != null) {
					intent.putExtras(b);
					startActivity(intent);
				}

			}

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// close database connections only when existing the module
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
			f = new TrackingCoursesFragment();
			break;
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

	private void updateFakeCourses() {
		RegistrationGroup group = new RegistrationGroup(3,
				Arrays.asList(new Meeting("df", "the happy farmer", Calendar
						.getInstance().getTime(), Calendar.getInstance()
						.getTime(), "somewhere 340")), null, 5);
		List<RegistrationGroup> reg = new ArrayList<RegistrationGroup>(
				Arrays.asList(group));

		List<GroupOfCourses> kdamim = new ArrayList<GroupOfCourses>(
				Arrays.asList(
						new GroupOfCourses(new ArrayList<String>(Arrays.asList(
								"omg", "jeez"))),
						new GroupOfCourses(new ArrayList<String>(Arrays
								.asList("damn"))),
						new GroupOfCourses(new ArrayList<String>(Arrays.asList(
								"omg", "my", "poms")))));
		List<GroupOfCourses> tsmudim = new ArrayList<GroupOfCourses>(
				Arrays.asList(
						new GroupOfCourses(new ArrayList<String>(Arrays.asList(
								"צמוד", "בינה מלאכותית"))), new GroupOfCourses(
								new ArrayList<String>(Arrays.asList("damn")))));

		Course course = new Course(
				"094412",
				"HISTABRUT",
				2.0f,
				"During the class we will talk about the high level design and your personal roles. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
				new Semester(2014, SemesterSeason.SPRING), Faculty.CS,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), kdamim, tsmudim, reg);

		Course course2 = new Course(
				"273455",
				"MY HEAD IS A HAMSTER",
				2.0f,
				"During the class we design and use roles to acheive happiness. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
				new Semester(2013, SemesterSeason.SPRING), Faculty.CS,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), kdamim, null, null);

		Course course3 = new Course(
				"094412",
				"HISTABRUT",
				2.0f,
				"During the class we design and use roles to acheive happiness. We will also discuss your project topic (with each team). Teams that we already approved will use the time to start the design process",
				new Semester(2013, SemesterSeason.SPRING), Faculty.CS,
				new GregorianCalendar(2014, 2, 11), new GregorianCalendar(2014,
						2, 11), kdamim, null, null);

		List<Course> courses = new ArrayList<Course>();
		courses.add(course3);

		for (int i = 0; i < 1000; i++) {
			Course c = new Course(course);
			c.setCourseNumber((i + 2000) + "");
			courses.add(c);
		}

		courses.add(course);
		courses.add(course2);

		List<AcademicCalendarEvent> academicList = new ArrayList<AcademicCalendarEvent>(
				Arrays.asList(new AcademicCalendarEvent(Calendar.getInstance(),
						"OMG", "dd", null)));

		List<CourseKey> trackingList = new ArrayList<CourseKey>(
				Arrays.asList(course.getCourseKey()));

		List<AccomplishedCourse> accomplishedList = new ArrayList<AccomplishedCourse>(
				Arrays.asList(new AccomplishedCourse("3434", "3434", "3434",
						"201301", "3434", null, false)));

		ArrayList<CourseItem> coursesExamsList = new ArrayList<CourseItem>(
				Arrays.asList(new CourseItem("מפרטים פורמליים במערכות מורכבות",
						"2324", "4.2", new ArrayList<ExamItem>(
								Arrays.asList(
										new ExamItem(Calendar.getInstance(),
												"טאוב 10"), new ExamItem(
												Calendar.getInstance(),
												"טאוב 100"))), "11", null)));

		Student student = new Student(UGDatabase.getInstance(this)
				.getCurrentStudentId());

		UGDatabase.getInstance(this).getCourses();
		UGDatabase.getInstance(this).getTrackingCourses();
		UGDatabase.getInstance(this).getCoursesAndExams();
		UGDatabase.getInstance(this).getStudentInfo();

		UGDatabase.getInstance(this).updateCourses(courses);
		// UGDatabase.getInstance(this).setAcademicCalendar(academicList);
		// UGDatabase.getInstance(this).setGradesSheet(accomplishedList);
		// UGDatabase.getInstance(this).setTrackingCourses(trackingList);
		// UGDatabase.getInstance(this).setCoursesAndExams(coursesExamsList);
		UGDatabase.getInstance(this).setStudentInfo(student);
		UGDatabase.getInstance(this).setCurrentSemesters(
				new Semester[] { new Semester(2013, SemesterSeason.SPRING),
						new Semester(2013, SemesterSeason.SUMMER),
						new Semester(2013, SemesterSeason.WINTER) });

		UGDatabase.getInstance(this).getCourses();
		UGDatabase.getInstance(this).getTrackingCourses();
		UGDatabase.getInstance(this).getCoursesAndExams();
		UGDatabase.getInstance(this).getStudentInfo();
		UGDatabase.getInstance(this).getGradesSheet();
		// CalendarUtils.addMyCourses(this, coursesExamsList);
	}

	public void onRegistrationClick(View v) {
		UGDatabase db = UGDatabase.getInstance(this);

		CourseKey ck = new CourseKey("104", new Semester(2011,
				SemesterSeason.WINTER));
		// db.addTrackingCourseToServer(db.getCurrentLoginObject(),ck);
		// db.deleteTrackingCourseFromServer(db.getCurrentLoginObject(),ck);

		final Intent intent = new Intent(this, TransparentActivity.class);
		final Bundle b = new Bundle();
		b.putString("key", TrackingCoursesFragment.class.toString());
		intent.putExtras(b);
		startActivity(intent);
	}

	public void getAllFragments() {
		List<Fragment> allFragments = getSupportFragmentManager()
				.getFragments();
		for (Fragment f : allFragments) {
			String s = f.getClass().toString();
		}
	}

	public AcademicCalendarListFragment getCalendarFragment() {
		List<Fragment> allFragments = getSupportFragmentManager()
				.getFragments();
		for (Fragment f : allFragments) {
			if (f instanceof AcademicCalendarListFragment)
				return (AcademicCalendarListFragment) f;
		}
		return null;

	}

	public GradesSheetListFragment getGradesSheetFragment() {
		List<Fragment> allFragments = getSupportFragmentManager()
				.getFragments();
		for (Fragment f : allFragments) {
			if (f instanceof GradesSheetListFragment)
				return (GradesSheetListFragment) f;
		}
		return null;

	}

	@Override
	public void onAddingTrackingCourseClicked() {
		Fragment f = new SearchForTrackingFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.non_transparent, f).commit();
	}

	@Override
	public void onCourseForTrackingSelected(CourseKey ck) {

		if (ck == null) {
			Toast.makeText(this, "Problem with adding course",
					Toast.LENGTH_SHORT).show();
		} else {
			List<CourseKey> db = UGDatabase.getInstance(this)
					.getTrackingCourses();
			if (!db.contains(new CourseKey(ck.getNumber(), ck.getSemester()))) {
				UGDatabase.getInstance(this).getTrackingCourses().add(ck);
			} else {
				Toast.makeText(this,
						"This course is already in your tracking list",
						Toast.LENGTH_SHORT).show();
			}
		}
		Fragment f = new TrackingCoursesFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.non_transparent, f).commit();
	}

}
