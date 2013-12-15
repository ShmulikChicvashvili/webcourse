package com.technion.coolie.ug;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFragment;
import com.tecnion.coolie.ug.utils.FragmentsFactory;

public class TransparentActivity extends CoolieActivity {
	public String key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_transparent_layout);
		Bundle b = getIntent().getExtras();
		key = b.getString("key");
		onListClicked(key, b);

	}

	public void onListClicked(String link, Bundle bundle) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		Fragment gradesSheetLayout; // Fragment 1
		Fragment coursesAndExamsLayout; // Fragment 2
		CourseDisplayFragment courseDisplayFragment;
		SearchFragment searchFragment;
		// Layout3 layout3; // Fragment 3
		// Layout4 layout4; // Fragment 4

		if (link.equals("gradesSheetLayout")) {
			gradesSheetLayout = FragmentsFactory.getGradesSheetLargeFragment();
			fragmentTransaction
					.replace(R.id.non_transparent, gradesSheetLayout);
			fragmentTransaction.commit();
		} else if (link.equals("coursesAndExamsLayout")) {
			coursesAndExamsLayout = FragmentsFactory.getCoursesAndExamsLargeFragment();
			fragmentTransaction.replace(R.id.non_transparent,
					coursesAndExamsLayout);
			fragmentTransaction.commit();
		} else if (link.equals(CourseDisplayFragment.class.toString())) {
			courseDisplayFragment = new CourseDisplayFragment();
			courseDisplayFragment.setArguments(bundle);
			fragmentTransaction.replace(R.id.non_transparent,
					courseDisplayFragment);
			fragmentTransaction.commit();
		} else if (link.equals(SearchFragment.class.toString())) {
			searchFragment = new SearchFragment();
			searchFragment.setArguments(bundle);
			fragmentTransaction.replace(R.id.non_transparent, searchFragment);
			fragmentTransaction.commit();
		}
		// } else if (link.equals("layout4")) {
		// layout4 = new Layout4();
		// fragmentTransaction.replace(R.id.non_transparent, layout4);
		// fragmentTransaction.commit();
		// }
	}

	public void transparentClick(View view) {
		finish();
	}

}
