package com.technion.coolie.studybuddy.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.views.ResourceFragment.OnFragmentInteractionListener;

public class CourseActivity extends StudyBuddyActivity implements
		ActionBar.OnNavigationListener, OnFragmentInteractionListener
{

	public static final String COURSE_ID = "COURSE_ID";
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private String courseNumber;

	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_activity_course);
		Bundle data = getIntent().getExtras();
		if (data.containsKey(COURSE_ID))
		{
			courseNumber = data.getString(COURSE_ID);
			getSherlock().getActionBar().setTitle(courseNumber);
		}
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] { "OverView",
								"Lectures", "tutorials" }), this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSherlock().getMenuInflater().inflate(R.menu.stb_course, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.technion.coolie.CoolieActivity#onOptionsItemSelected(com.
	 * actionbarsherlock.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.stb_edit_curse:
			Intent intent = new Intent(this, EditCourse.class);
			intent.putExtra(EditCourse.COURSE_ID, courseNumber);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (itemPosition)
		{
		case 0:
			Fragment fragment = CourseOverViewFragment.newInstance(courseNumber);
			ft.replace(R.id.stb_container, fragment).commit();
			break;
		case 1:
		case 2:
			ResourceFragment fragment1 = ResourceFragment.newInstance(
					itemPosition == 1 ? "Lecture" : "tutorial", courseNumber);

			ft.replace(R.id.stb_container, fragment1).commit();
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public void onFragmentInteraction(Uri uri)
	{
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */

}
