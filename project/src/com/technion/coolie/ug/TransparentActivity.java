package com.technion.coolie.ug;

import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.technion.coolie.ug.utils.UGCurrentState;

import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.calendar.AcademicCalendarFragment;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchForTrackingFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.tracking.TrackingCoursesFragment;
import com.technion.coolie.ug.utils.FragmentsFactory;

public class TransparentActivity extends CoolieActivity implements
		ITrackingCourseTrasferrer {
	public String key;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_transparent_layout);
		final Bundle b = getIntent().getExtras();
		key = b.getString("key");
		onListClicked(key, b);
		String title = getTitle(key);
		getSupportActionBar().setTitle(title);
	}

	private String getTitle(String link) {
		String s = null;
		if (link.equals(GradesSheetFragment.class.toString())) {
			s = getString(R.string.ug_grades_sheet);
		} else if (link.equals(CoursesAndExamsFragment.class.toString())) {
			s = getString(R.string.ug_coursesAndExams);
		} else if (link.equals(CourseDisplayFragment.class.toString())) {
			s = getString(R.string.ug_course_info);
		} else if (link.equals(SearchFragment.class.toString())) {
			s = getString(R.string.ug_search);
		} else if (link.equals(AcademicCalendarFragment.class.toString())) {
			s = getString(R.string.ug_calendar);
		} else if (link.equals(TrackingCoursesFragment.class.toString())) {
			s = getString(R.string.ug_tracker);
		}
		return s;
	}

	public void onListClicked(final String link, final Bundle bundle) {
		final FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		Fragment gradesSheetLayout;
		Fragment coursesAndExamsLayout;
		CourseDisplayFragment courseDisplayFragment;
		SearchFragment searchFragment;
		AcademicCalendarFragment academicCalendarFragment;
		TrackingCoursesFragment trackingCoursesFragment;

		if (link.equals(GradesSheetFragment.class.toString())) {
			gradesSheetLayout = FragmentsFactory.getGradesSheetLargeFragment();
			replaceAndCommit(gradesSheetLayout);
		} else if (link.equals(CoursesAndExamsFragment.class.toString())) {
			coursesAndExamsLayout = FragmentsFactory
					.getCoursesAndExamsLargeFragment();
			replaceAndCommit(coursesAndExamsLayout);
		} else if (link.equals(CourseDisplayFragment.class.toString())) {
			courseDisplayFragment = new CourseDisplayFragment();
			courseDisplayFragment.setArguments(bundle);
			replaceAndCommit(courseDisplayFragment);
		} else if (link.equals(SearchFragment.class.toString())) {
			searchFragment = new SearchFragment();
			searchFragment.setArguments(bundle);
			replaceAndCommit(searchFragment);
		} else if (link.equals(AcademicCalendarFragment.class.toString())) {
			academicCalendarFragment = new AcademicCalendarFragment();
			replaceAndCommit(academicCalendarFragment);
		} else if (link.equals(TrackingCoursesFragment.class.toString())) {
			trackingCoursesFragment = new TrackingCoursesFragment();
			replaceAndCommit(trackingCoursesFragment);
		}
	}

	private void replaceAndCommit(final Fragment fragment) {
		fragmentTransaction.replace(R.id.non_transparent, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			UGCurrentState.currentOpenFragment = "none";
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// kill transperant activity when switching to landscape !
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
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
			List<CourseKey> db = UGDatabase.getInstance(this).getTrackingCourses();
			if (!db.contains(new CourseKey(ck.getNumber(), ck.getSemester())))
			{
				List<CourseKey> x = UGDatabase.getInstance(this).getTrackingCourses();
				x.add(ck);
				UGDatabase.getInstance(this).setTrackingCourses(x);
			}
			else
			{
				Toast.makeText(this, "This course is already in your tracking list",
						Toast.LENGTH_SHORT).show();
			}
		}
		Fragment f = new TrackingCoursesFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.non_transparent, f).commit();
	}
	
	@Override
	public void onBackPressed() 
	{
		UGCurrentState.currentOpenFragment = "none";
		super.onBackPressed();
	}

}
