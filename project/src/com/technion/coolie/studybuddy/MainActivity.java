package com.technion.coolie.studybuddy;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.achartengine.GraphicalView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.Menu;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.adapters.CourseListAdapter;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.graphs.GraphFactory;
import com.technion.coolie.studybuddy.views.EditCourse;
import com.technion.coolie.studybuddy.views.NowLayout;
import com.technion.coolie.studybuddy.views.StbSettingsActivity;
import com.technion.coolie.studybuddy.views.StudyBuddyActivity;

public class MainActivity extends StudyBuddyActivity implements Observer
{

	GraphicalView			graphView;
	private LinearLayout	_layout;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSherlock().getMenuInflater().inflate(R.menu.stb_main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item)
	{

		Intent intent = null;
		switch (item.getItemId())
		{
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.stb_add_curse:
			intent = new Intent(this, EditCourse.class);
			startActivity(intent);
			return true;
		case R.id.stb_main_settings:
			intent = new Intent(this, StbSettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.technion.coolie.CoolieActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_view_main);

		DataStore.setContext(this);

		NowLayout layout = (NowLayout) findViewById(R.id.course_list);

		CourseListAdapter adapter = new CourseListAdapter(this);
		layout.setAdapter(adapter);
		DataStore.getInstance().addObserver(adapter);

		// WeeklyGraph
		_layout = (LinearLayout) findViewById(R.id.Chart_layout);

		updateGraphView();

		DataStore.getInstance().addObserver(this);
	}

	private void updateGraphView()
	{
		_layout.removeAllViews();
		Date today = new Date();
		graphView = GraphFactory.getWeeklyProgressGraph(getBaseContext(),
						today, DataStore.getInstance().getWorkStats(today, 7));
		_layout.addView(graphView);
	}

	@Override
	public void update(Observable observable, Object data)
	{
		updateGraphView();
	}
}
