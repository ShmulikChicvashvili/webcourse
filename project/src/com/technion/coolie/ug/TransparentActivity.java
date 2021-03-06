package com.technion.coolie.ug;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.calendar.AcademicCalendarFragment;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.tecnion.coolie.ug.utils.FragmentsFactory;

public class TransparentActivity extends CoolieActivity {
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

}
