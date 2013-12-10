package com.technion.coolie.ug;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.coursesAndExams.CoursesAndExamsFragment;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;

public class TransparentActivity extends CoolieActivity {
public String key; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_transparent_layout);
		Bundle b = getIntent().getExtras();
		key = b.getString("key");
		onRssItemSelected(key);
		
	}

	public void onRssItemSelected(String link) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		GradesSheetFragment gradesSheetLayout; // Fragment 1
		CoursesAndExamsFragment coursesAndExamsLayout; // Fragment 2
//		Layout3 layout3; // Fragment 3
//		Layout4 layout4; // Fragment 4

		if (link.equals("gradesSheetLayout")) {
			gradesSheetLayout = new GradesSheetFragment();
			fragmentTransaction.replace(R.id.non_transparent, gradesSheetLayout);
			fragmentTransaction.commit();
		} else if (link.equals("coursesAndExamsLayout")) {
			coursesAndExamsLayout = new CoursesAndExamsFragment();
			fragmentTransaction.replace(R.id.non_transparent, coursesAndExamsLayout);
			fragmentTransaction.commit();
		}
//		} else if (link.equals("layout3")) {
//			layout3 = new Layout3();
//			fragmentTransaction.replace(R.id.non_transparent, layout3);
//			fragmentTransaction.commit();
//		} else if (link.equals("layout4")) {
//			layout4 = new Layout4();
//			fragmentTransaction.replace(R.id.non_transparent, layout4);
//			fragmentTransaction.commit();
//		}
	}

}
