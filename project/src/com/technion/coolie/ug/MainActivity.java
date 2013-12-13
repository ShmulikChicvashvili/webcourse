package com.technion.coolie.ug;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment;

public class MainActivity extends CoolieActivity implements OnRightMenuItemSelected{

	public static final String DEBUG_TAG = "DEBUG";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ug_main_screen);
		
//		startActivity(new Intent(this, SearchActivity.class));
		//startActivity(new Intent(this, GradesSheetActivity.class));
	}


	@Override
	public void onLeftMenuItemSelected(LandscapeLeftMenuItems item) {
		Fragment f=null;
		switch (item) 
		{
		case GRADES_SHEET :
			f = new GradesSheetFragment();
			break;
		case COURSES_AND_EXAMS :
			f = new CoursesAndExamsListFragment	();
			break;
		case ACADEMIC_CALENDAR :
			f = new AcademicCalendarListFragment();
			break;
		case TRACKING_COURSES :
			f = new TrackingCoursesListFragment();
			break;
			
		default:
			f = new GradesSheetFragment();
			break;
		}
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.detail_container, f).commit();
	}
 
}
