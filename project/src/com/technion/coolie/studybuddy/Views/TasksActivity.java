package com.technion.coolie.studybuddy.Views;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.GridView;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Adapters.TaskAdapter;

public class TasksActivity extends StudyBuddyActivity
{
	private TaskAdapter adapter;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	// private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_activity_tasks);
		// Show the Up button in the action bar.
		NowLayout layout = (NowLayout) findViewById(R.id.stb_task_list);
		// Set up the drawer.
		adapter = new TaskAdapter(this);
		((GridView) findViewById(R.id.stb_task_list)).setAdapter(adapter);
		SwipeDismissListViewTouchListener listener = new SwipeDismissListViewTouchListener(
				layout,
				new SwipeDismissListViewTouchListener.DismissCallbacks()
				{

					@Override
					public void onDismiss(NowLayout listView,
							int[] reverseSortedPositions)
					{
						for (int i : reverseSortedPositions)
						{
							adapter.remove(i);
						}
						adapter.notifyDataSetChanged();
					}

					@Override
					public boolean canDismiss(int position)
					{
						return true;
					}
				});

		layout.setOnTouchListener(listener);
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
		switch (item.getItemId())
		{
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
