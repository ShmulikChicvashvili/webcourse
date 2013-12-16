package com.technion.coolie.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.Menu;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.adapters.CourseAdapter;
import com.technion.coolie.studybuddy.graphs.GraphFactory;
import com.technion.coolie.studybuddy.views.EditCourse;
import com.technion.coolie.studybuddy.views.NowLayout;
import com.technion.coolie.studybuddy.views.StudyBuddyActivity;
import com.technion.coolie.studybuddy.views.TasksActivity;

public class MainActivity extends StudyBuddyActivity
{

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

		// DataStore.addFakeCourses();

//		ImageView imageView = (ImageView) findViewById(R.id.graph_view);
//		imageView.setOnClickListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				v.getContext().startActivity(
//						new Intent(v.getContext(), TasksActivity.class));
//			}
//		});
		

		
		NowLayout layout = (NowLayout) findViewById(R.id.course_list);

		CourseAdapter adapter = new CourseAdapter(this);
		layout.setAdapter(adapter);
		
		// ANNA GRAPH HERE - JUST REMOVE COMMENTS TO SEE
		LinearLayout _layout = (LinearLayout) findViewById(R.id.Chart_layout);
		 _layout.addView(GraphFactory.getSampleCourseProgress(getBaseContext()),
				 new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.stb_add_curse:
			Intent intent = new Intent(this, EditCourse.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSherlock().getMenuInflater().inflate(R.menu.stb_main_menu, menu);
		return true;
	}
}
