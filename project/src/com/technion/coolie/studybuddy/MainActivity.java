package com.technion.coolie.studybuddy;

import java.util.Date;

import org.achartengine.GraphicalView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.Menu;
import com.technion.coolie.R;
import com.technion.coolie.STBSettingsActivity;
import com.technion.coolie.studybuddy.adapters.CourseAdapter;
import com.technion.coolie.studybuddy.graphs.GraphFactory;
import com.technion.coolie.studybuddy.views.EditCourse;
import com.technion.coolie.studybuddy.views.NowLayout;
import com.technion.coolie.studybuddy.views.StudyBuddyActivity;

public class MainActivity extends StudyBuddyActivity {

	GraphicalView graphView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.technion.coolie.CoolieActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_view_main);

		// DataStore.addFakeCourses();

		NowLayout layout = (NowLayout) findViewById(R.id.course_list);

		CourseAdapter adapter = new CourseAdapter(this);
		layout.setAdapter(adapter);

		// WeeklyGraph
		LinearLayout _layout = (LinearLayout) findViewById(R.id.Chart_layout);
		Date d = new Date();
		graphView = GraphFactory.getWeeklyProgressGraph(getBaseContext(), d,
				new int[] { 1, 1, 2, 3, 5, 8, 13 });
		_layout.addView(graphView);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {

		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.stb_add_curse:
			intent = new Intent(this, EditCourse.class);
			startActivity(intent);
			return true;
		case R.id.stb_main_settings:
			intent = new Intent(this, STBSettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().getMenuInflater().inflate(R.menu.stb_main_menu, menu);
		return true;
	}
}
