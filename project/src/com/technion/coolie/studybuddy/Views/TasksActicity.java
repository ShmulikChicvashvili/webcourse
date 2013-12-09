package com.technion.coolie.studybuddy.Views;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Adapters.TaskAdapter;
import com.technion.coolie.studybuddy.data.DataStore;

public class TasksActicity extends CoolieActivity
{
	private TaskAdapter adapter;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_activity_tasks);
		// Show the Up button in the action bar.
		
		// Set up the drawer.
		adapter = new TaskAdapter(this);

	}

	public void onSectionAttached(int number)
	{
		mTitle = DataStore.getCourse(number);

		// TODO change coursess data mechanism

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.technion.coolie.CoolieActivity#onCreateOptionsMenu(com.actionbarsherlock
	 * .view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu)
	{
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
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
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

}
