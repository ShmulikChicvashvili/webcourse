package com.technion.coolie.ug;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.TrackballGestureDetector;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.calendar.AcademicCalendarFragment;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchForTrackingFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.technion.coolie.ug.utils.FragmentsFactory;
import com.technion.coolie.ug.tracking.*;
import com.technion.coolie.ug.model.*;
import com.technion.coolie.ug.db.*;

public class TransparentActivity extends CoolieActivity implements	ITrackingCourseTrasferrer {
	public String key;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_transparent_layout);
		final Bundle b = getIntent().getExtras();
		key = b.getString("key");
		onListClicked(key, b);
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

	/**
	 * This method is called from the corresponding xml file
	 * 
	 * @param view
	 *            The clicked view
	 */
	public void transparentClick(final View view) {
		finish();
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
		getSupportFragmentManager().beginTransaction().replace(R.id.non_transparent, f).commit();
	}
	
	@Override
	public void onCourseForTrackingSelected(CourseKey ck) {
		
		if (ck == null) 
		{
			Toast.makeText(this,"Problem with adding course", Toast.LENGTH_SHORT).show();
		}
		else
		{
			UGDatabase.getInstance(this).getMyTrackingCourses().add(ck);
		}
		Fragment f = new TrackingCoursesFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.non_transparent, f).commit();
	}

}
