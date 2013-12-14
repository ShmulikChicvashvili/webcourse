package com.technion.coolie.studybuddy.Views;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Adapters.TaskAdapter;
import com.technion.coolie.studybuddy.data.DataStore;

public class TasksActicity extends StudyBuddyActivity
{
	private TaskAdapter adapter;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
//	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_activity_tasks);
		// Show the Up button in the action bar.
		
		// Set up the drawer.
		adapter = new TaskAdapter(this);
		((GridView)findViewById(R.id.stb_task_list)).setAdapter(adapter);
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
	