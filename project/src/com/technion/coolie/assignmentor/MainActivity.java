package com.technion.coolie.assignmentor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.CollieNotification;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.assignmentor.EnhancedListView.Undoable;
import com.technion.coolie.assignmentor.TaskSettings.TaskSettingsFragment;

public class MainActivity extends CoolieActivity implements MenuItem.OnMenuItemClickListener {
	
	// Projection array. Creating indices for this array instead of doing
	// dynamic lookups improves performance.
	
	/* ****************************************************************************** */
	/* We need to consider whether we want to support syncing with google calendar    */ 
	/* for devices with api lower than 14!											  */
	/* ****************************************************************************** */
	public static final String[] EVENT_PROJECTION = new String[] {
		Calendars._ID,							// 0
		Calendars.ACCOUNT_NAME, 				// 1
		Calendars.CALENDAR_DISPLAY_NAME,		// 2
		Calendars.OWNER_ACCOUNT					// 3
	};
	
	// The indices for the projection array above.
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
	private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
	
	private static final int NEW_TASK_REQUEST = 3535;
	private static final int TASK_SETTINGS_REQUEST = 4545;
	
	public static final String AM_TAG = "AssignMentor";
	public static final String COURSE_LIST = "CourseList";
	public static final String DATA_FETCHED = "com.technion.coolie.assignmentor.DATA_FETCHED";
	
	public static final String KEY_AM_PREFS_SORT_BY = "am_prefs_sort_by";
	
	public static MyAdapter mAdapter;
	
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	private BroadcastReceiver mReceiver;
	private EnhancedListView mListView;
	private ProgressBar mProgressBar;
	private TextView progressPercent;
	private TextView emptyViewRefreshTv;
	private MySqliteOpenHelper dbHelper;	
	
	// Temporary list to hold course ids.
	ArrayList<String> courseList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am_activity_main);
		
		// Set the default values for the general settings. 
		PreferenceManager.setDefaultValues(this, R.xml.am_preferences, false);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.getBoolean(GeneralSettings.KEY_GS_CALENDAR_SYNC, false);
		prefs.getString(GeneralSettings.KEY_GS_UPDATES_FREQ, "Never");
		prefs.getString(GeneralSettings.KEY_GS_REMINDER, "No Reminder");
		
		// Set a receiver to get broadcasts from the update service whenever its done.
		mReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context c, Intent i) {
//				Log.i(AM_TAG, "broadcast received!");
				String action = i.getAction();
				if (action.equals(DATA_FETCHED)) {
//					Log.i(MainActivity.AM_TAG, "broadcast DATA_FETCHED received!");
					mAdapter.openDB();
				}
			}
		};
		IntentFilter intentFilter = new IntentFilter(DATA_FETCHED);
		registerReceiver(mReceiver, intentFilter);
		
		mProgressBar = (ProgressBar) findViewById(R.id.am_technion_tasks_progress_bar);
		progressPercent = (TextView) findViewById(R.id.am_technion_tasks_progress_percent);
		
		mListView = (EnhancedListView) findViewById(R.id.am_technion_tasks_listview);
		mAdapter = new MyAdapter(this);
		mAdapter.openDB();
		mListView.setAdapter(mAdapter);
		mListView.setEmptyView(findViewById(R.id.am_technion_tasks_empty_view));
		emptyViewRefreshTv = (TextView) findViewById(R.id.am_empty_view_refresh);
		emptyViewRefreshTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent serviceIntent = new Intent(getApplicationContext(), TaskParser.class);
				serviceIntent.putStringArrayListExtra(COURSE_LIST, courseList);
				startService(serviceIntent);
			}
		});
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long id) {
				
				if(!mAdapter.isDone(position)) {
					mAdapter.markAsDone(position);
				} else {
					mAdapter.markAsUndone(position);
				}
			}
		});
		
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@SuppressLint("NewApi")
			@Override
			public boolean onItemLongClick(AdapterView<?> list, View view,
					int position, long id) {
				// Setting a scale up animation upon long click on an item.
				ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(
	                    view, 0, 0, view.getWidth(), view.getHeight());
				
				// Write the clicked tasks info to the shared preference.
				prepareTaskPreference(position);
				
				// Add the clicked task position in the list as an extra data. this data is used when the
				// activity is finished to update the view (handled in onActivityResult()).
				Intent myIntent = new Intent(getApplicationContext(), TaskSettings.class);
				myIntent.putExtra("position", position);
				startActivityForResult(myIntent, TASK_SETTINGS_REQUEST, opts.toBundle());
//				Log.i(AM_TAG, "Long click on item: " + String.valueOf(position));
				return true;
			}
		});
		
		// Handles the re-adding of an item that was deleted (user pressed Undo).
		mListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
			
			@Override
			public Undoable onDismiss(EnhancedListView listView,final int position) {
				final TasksInfo removedItem = (TasksInfo) mAdapter.getItem(position);
				mAdapter.remove(position);
				return new EnhancedListView.Undoable() {
					
					@Override
					public void undo() {
						mAdapter.insert(position, removedItem);
					}
				};
			}
		});
		
		// Set swipe-to-delete configuration. 
		mListView.setSwipingLayout(R.id.am_list_item_layout);
		mListView.setUndoStyle(EnhancedListView.UndoStyle.MULTILEVEL_POPUP);
		mListView.setUndoHideDelay(3000);
		mListView.enableSwipeToDismiss();
		mListView.setSwipeDirection(EnhancedListView.SwipeDirection.BOTH);
		mListView.setRequireTouchBeforeDismiss(false);
		
		// Setting a temporary course list to fetch from the web.
		// This list is passed to the service in the intent's extra data.
		// Start the update service by pressing 'Sort by progress' on the overflow menu.
		courseList.add("234107"); 
//		courseList.add("234114");
//		courseList.add("236523");
//		courseList.add("236350");
//		courseList.add("236360");
//		courseList.add("234118");
//		courseList.add("234122");
//		courseList.add("234123");
//		courseList.add("234325");
//		courseList.add("234141");
//		courseList.add("234218");
//		courseList.add("234247");


	}
	
	private void prepareTaskPreference(int position) {
		TasksInfo task = (TasksInfo) mAdapter.getItem(position);
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(TaskSettingsFragment.KEY_TS_TASK_NAME, task.taskName.toString());
		editor.putString(TaskSettingsFragment.KEY_TS_COURSE_NAME, task.courseName);
		editor.putString(TaskSettingsFragment.KEY_TS_COURSE_ID, task.courseId);
		editor.putString(TaskSettingsFragment.KEY_TS_DUE_DATE, task.dueDate);
		editor.putInt(TaskSettingsFragment.KEY_TS_DIFFICULTY, task.difficulty);
		editor.putInt(TaskSettingsFragment.KEY_TS_IMPORTANCE, task.importance);
		editor.putInt(TaskSettingsFragment.KEY_TS_PROGRESS, task.progress);
		editor.commit();
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(mReceiver);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		IntentFilter intentFilter = new IntentFilter(DATA_FETCHED);
		registerReceiver(mReceiver, intentFilter);
		mAdapter.openDB();
		super.onResume();
	}
	
	@Override
	public void onStop() {
		if (mListView != null) {
			mListView.discardUndo();
		}
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NEW_TASK_REQUEST) {
			if (resultCode == RESULT_OK) {
				String[] info = data.getStringArrayExtra("newTasksInfo");
				TasksInfo newTask = new TasksInfo(new SpannableString(info[0]), info[1], info[2], info[3]);
				int[] properties = data.getIntArrayExtra("newTasksProperties");
				newTask.difficulty = properties[0];
				newTask.importance = properties[1];
				// Progress is not set for manually added new tasks, so set it to 0.
				newTask.progress = 0;
				mAdapter.insert(-1, newTask);
				Toast.makeText(this, "New Task Added!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "No New Task!", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == TASK_SETTINGS_REQUEST) {
			if (resultCode == RESULT_OK) {
				int position = data.getIntExtra("position", -1);
				mAdapter.updateTaskFromSharedPrefs(position);
//				Log.i(AM_TAG, "Result OK from tasks settings at position: " + String.valueOf(position));
			} else {
				
			}
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = prefs.edit();
		
		switch(item.getItemId()) {
		
			case R.id.action_sort_by_due_date:
				editor.putInt(KEY_AM_PREFS_SORT_BY, 0);
				editor.commit();
//				Collections.sort(mAdapter.myItems, new TasksInfoComparator(0));
				mAdapter.openDB();
				break;
				
			case R.id.action_sort_by_diff:
				editor.putInt(KEY_AM_PREFS_SORT_BY, 1);
				editor.commit();
//				Collections.sort(mAdapter.myItems, new TasksInfoComparator(1));
				mAdapter.openDB();
				break;
				
			case R.id.action_sort_by_imp:
				editor.putInt(KEY_AM_PREFS_SORT_BY, 2);
				editor.commit();
//				Collections.sort(mAdapter.myItems, new TasksInfoComparator(2));
				mAdapter.openDB();
				break;
				
			case R.id.action_sort_by_prog:
				editor.putInt(KEY_AM_PREFS_SORT_BY, 3);
				editor.commit();
//				Collections.sort(mAdapter.myItems, new TasksInfoComparator(3));
				mAdapter.openDB();;
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.am_main, menu);
		// Giving the settings button item some random id number.
		int settingsButtonId = 509;
		MenuItem settingsButton = menu.add(0, settingsButtonId, 0, getResources().getString(R.string.am_action_settings));
		settingsButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		settingsButton.setIcon(R.drawable.am_settings);
		settingsButton.setOnMenuItemClickListener(this);
		
		// Giving the new task button some random id number.
		int newTaskButtonId = 547;
		MenuItem newTaskButton = menu.add(0, newTaskButtonId, 0, getResources().getString(R.string.am_action_new_task));
		newTaskButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		newTaskButton.setIcon(R.drawable.am_new_task);
		newTaskButton.setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}
	
	// Handle clicks on menu items such as 'Settings' and 'New Task'
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		
		Intent myIntent;
		switch (item.getItemId()) {
		
		// 'Settings' button clicked.
		case 509:
			myIntent = new Intent(MainActivity.this, GeneralSettings.class);
			startActivity(myIntent);
			break;
			
		// 'New Task' button clicked.
		case 547:
			myIntent = new Intent(this, AddNewTask.class);
			startActivityForResult(myIntent, NEW_TASK_REQUEST);
			break;
		}
		return true;
	}
	
	class MyAdapter extends BaseAdapter implements ViewSwitcher.ViewFactory {
		
		private Context context;
		private LayoutInflater inflater;
		List<TasksInfo> myItems;
		private Integer totalNumOfTasks;
		private Integer numOfDoneTasks;
		private ImageSwitcher mSwitcher;
		private Animation fadeIn, fadeOut;
		
		public MyAdapter(Context c) {
			this.context = c;
			
			dbHelper = new MySqliteOpenHelper(c);
			
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			totalNumOfTasks = 0;
			numOfDoneTasks = 0;
			
			myItems = dbHelper.getAllTasks();
			fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
			fadeIn.setDuration(1000);
			fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
			fadeOut.setDuration(1000);
			mSwitcher = (ImageSwitcher) findViewById(R.id.am_technion_tasks_progress_emo_switcher);
			mSwitcher.setInAnimation(fadeIn);
			mSwitcher.setOutAnimation(fadeOut);
			mSwitcher.setFactory(this);
			updateProgress();
		}
		
		public void openDB() {
//			Log.i(AM_TAG, "MyAdapter -> openDB() -> DB size: " + String.valueOf(dbHelper.getTaskCount()));
			myItems = dbHelper.getAllTasks();
			updateView();
		}
		
		@Override
		public View makeView() {
			ImageView mImageView = new ImageView(context);
			mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			return mImageView;
		}
		
		private void updateProgress() {
			Integer percentDone = getPercentDone();
			progressPercent.setText(percentDone.toString() + "%");
			mProgressBar.setProgress(percentDone);
			int imageResource = R.drawable.am_progress0;
			if (percentDone >= 12 && percentDone < 25) imageResource = R.drawable.am_progress25;
			else if (percentDone >= 25 && percentDone < 40) imageResource = R.drawable.am_progress12;
			else if (percentDone >= 40 && percentDone < 50) imageResource = R.drawable.am_progress40;
			else if (percentDone >= 50 && percentDone < 62) imageResource = R.drawable.am_progress50;
			else if (percentDone >= 62 && percentDone < 75) imageResource = R.drawable.am_progress62;
			else if (percentDone >= 75 && percentDone < 90) imageResource = R.drawable.am_progress75;
			else if (percentDone >= 90 && percentDone < 100) imageResource = R.drawable.am_progress90;
			else if (percentDone == 100) imageResource = R.drawable.am_progress100;
			mSwitcher.setImageResource(imageResource);
		}
		
		private Integer getPercentDone() {
			if (totalNumOfTasks == 0) return 100;
			else return (numOfDoneTasks*100)/totalNumOfTasks;
		}
		
		public boolean isDone(int position) {
			
			return myItems.get(position).isDone;
		}
		
		@Override
		public int getCount() {
			return myItems.size();
		}

		@Override
		public Object getItem(int position) {
			return myItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			
			if (null == view) {
				view = inflater.inflate(R.layout.am_list_item, parent, false);
				holder = new ViewHolder();
				holder.courseName = (TextView) view.findViewById(R.id.am_list_item_course_name);
				holder.courseId = (TextView) view.findViewById(R.id.am_list_item_course_id);
				holder.taskName = (TextView) view.findViewById(R.id.am_list_item_task);
				holder.dueDate = (TextView) view.findViewById(R.id.am_list_item_due_date);
				view.setTag(holder);
			}
			else {
				holder = (ViewHolder) view.getTag();
			}
			
			holder.courseName.setText(myItems.get(position).courseName);
			holder.courseId.setText(myItems.get(position).courseId);
			holder.taskName.setText(myItems.get(position).taskName);
			holder.dueDate.setText(myItems.get(position).dueDate);
			
			return view;
		}
		
		public void remove(int position) {
			
			long taskId = myItems.get(position).id;
//			if (myItems.get(position).isDone) numOfDoneTasks--;
//			totalNumOfTasks--;
			myItems.remove(position);
			dbHelper.deleteTask(taskId);
			updateView();
		}
		
		// Used to insert tasks manually or by pressing undo after swipe.
		public void insert(int position, TasksInfo newTask) {
			
			if (position >= 0 && !myItems.contains(newTask)) {
				// Task is re-added (user deleted and then pressed undo) to its original position.
				dbHelper.createTask(newTask);
				myItems.add(position, newTask);
			} else {
				dbHelper.createTask(newTask);
				myItems.add(newTask);
			}
			updateView();
		}
		
		// Used to insert new fetched task from web.
		// *NO* call to notifyDataSetChanged() since this method is being called from
		// a service running on thread different than the UI thread, hence, calling
		// notifyDataSetChange() will throw an exception.
		public void insertFetched(TasksInfo fetchedTask) {
//			Log.i(AM_TAG, "insertFetched -> myItems size (before adding): " + String.valueOf(myItems.size()));
			myItems.add(fetchedTask);
			dbHelper.createTask(fetchedTask);
//			Log.i(AM_TAG, "insertFetched -> myItems size (after adding): " + String.valueOf(myItems.size()));
			totalNumOfTasks = myItems.size();
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			boolean notif = prefs.getBoolean(GeneralSettings.KEY_GS_NOTIF_SYNC, true);
			if (notif) {
				// If notifications are on, pop a notification that a new task was found.
				String notificationText = fetchedTask.taskName + " - " + fetchedTask.courseName;
				CollieNotification cn = new CollieNotification("New H.W!", notificationText, 
						MainActivity.this, CollieNotification.Priority.IMMEDIATELY, 
						true, getApplicationContext());
				cn.sendNotification();
			}
			
			// If a due date is present, insert to calendar.
			boolean sync = prefs.getBoolean(GeneralSettings.KEY_GS_CALENDAR_SYNC, false);
			if (sync && checkDueDate(fetchedTask.dueDate)) insertToCalendar(fetchedTask);
		}
		
		public void updateView() {
			notifyDataSetChanged();
			totalNumOfTasks = myItems.size();
			countDoneTasks();
			updateProgress();
		}
		
		private void countDoneTasks() {
			numOfDoneTasks = 0;
			for (TasksInfo task : myItems) {
				if (task.isDone) numOfDoneTasks++;
			}
		}
		
		public void markAsDone(int position) {
			TasksInfo task = myItems.get(position);
			task.changeStatus(true);
			dbHelper.updateTask(task);
			updateView();
		}
		
		public void markAsUndone(int position) {
			TasksInfo task = myItems.get(position);
			task.changeStatus(false);
			dbHelper.updateTask(task);
			updateView();
		}
		
		public List<TasksInfo> getList() {
			return myItems;
		}
		
		public void setProperties(int position, int difficulty, int importance, int progress) {
			TasksInfo task = myItems.get(position);
			task.difficulty = difficulty;
			task.importance = importance;
			task.progress = progress;
			dbHelper.updateTask(task);
		}
		
		public void updateTaskFromSharedPrefs(int position) {
			TasksInfo task = myItems.get(position);
			
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
			task.taskName = new SpannableString(sharedPrefs.getString(TaskSettingsFragment.KEY_TS_TASK_NAME , "??!"));
			task.courseName = sharedPrefs.getString(TaskSettingsFragment.KEY_TS_COURSE_NAME, "??!");
			task.courseId = sharedPrefs.getString(TaskSettingsFragment.KEY_TS_COURSE_ID, "??!");
			task.dueDate = sharedPrefs.getString(TaskSettingsFragment.KEY_TS_DUE_DATE, "??!");
			task.difficulty = sharedPrefs.getInt(TaskSettingsFragment.KEY_TS_DIFFICULTY, 0);
			task.importance = sharedPrefs.getInt(TaskSettingsFragment.KEY_TS_IMPORTANCE, 0);
			task.progress = sharedPrefs.getInt(TaskSettingsFragment.KEY_TS_PROGRESS, 0);
			dbHelper.updateTask(task);
			
			updateView();
		}
		
		@SuppressLint("NewApi")
		private void insertToCalendar(TasksInfo newTask) {
			// Run query.
			Cursor cur = null;
			ContentResolver cr = getContentResolver();
			Uri uri = Calendars.CONTENT_URI;
			String selection = "((" + Calendars.ACCOUNT_NAME + " GLOB ?) AND ("
					+ Calendars.ACCOUNT_TYPE + " GLOB ?) AND ("
					+ Calendars.OWNER_ACCOUNT + " GLOB ?))";
			// Set selection args to find only the Google accounts, since we're interested only
			// in adding events to google calendar.
			String[] selectionArgs = new String[] {"*gmail.com", "com.google", 
					"*gmail.com"};
			
			// Submit the query and get the cursor object back.
			cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
			
			// Store the google calendars ids in array list, in case the user has more
			// than one google calendar.
			ArrayList<Long> calendarIds = new ArrayList<Long>();
			
			// Use the cursor to step through the returned records.
			while(cur.moveToNext()) {
				long CalID = 0;
				String displayName = null;
				String accountName = null;
				String ownerName = null;
				
				// Get the field values.
				CalID = cur.getLong(PROJECTION_ID_INDEX);
				displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
				accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
				ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
				
				calendarIds.add(CalID);
				
//				Log.i(AM_TAG, "** CalID = " + String.valueOf(CalID) + " displayName = " + displayName
//						+ " accountName = " + accountName + " ownerName = " + ownerName);
			}
			
			String eventTitle = newTask.courseId + " " + newTask.courseName + " - " + newTask.taskName;
			int[] dayMonthYear = dueDateIntArr(newTask.dueDate);
			
			for (Long id : calendarIds) {
//				Log.i(AM_TAG, "Inserting Task: " + eventTitle
//						+ " To calendar: " + String.valueOf(id));
				long startMillis = 0;
				long endMillis = 0;
				Calendar beginTime = Calendar.getInstance();
				beginTime.set(dayMonthYear[2], dayMonthYear[1], dayMonthYear[0], 9, 0);
				startMillis = beginTime.getTimeInMillis();
				Calendar endTime = Calendar.getInstance();
				endTime.set(dayMonthYear[2], dayMonthYear[1], 
						dayMonthYear[0], 10, 0);
				endMillis = endTime.getTimeInMillis();
				
				ContentResolver mCr = getContentResolver();
				ContentValues values = new ContentValues();
				values.put(Events.DTSTART, startMillis);
				values.put(Events.DTEND, endMillis);
				values.put(Events.TITLE, eventTitle);
				values.put(Events.DESCRIPTION, newTask.courseId + " " + newTask.courseName);
				values.put(Events.CALENDAR_ID, id);
				values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
				Uri mUri = mCr.insert(Events.CONTENT_URI, values);
				
				// Get the event id and save it.
				newTask.eventID = Long.parseLong(mUri.getLastPathSegment());
			}
		}
		
		private int[] dueDateIntArr(String dueDate) {
			String[] dueDateStringArr = dueDate.split("/");
			int[] dayMonthYear = new int[3];
			dayMonthYear[0] = Integer.valueOf(dueDateStringArr[0].trim());
			dayMonthYear[1] = Integer.valueOf(dueDateStringArr[1].trim());
			// Months are 0-based when setting calendar time.
			dayMonthYear[1] = dayMonthYear[1] - 1;
			dayMonthYear[2] = Integer.valueOf(dueDateStringArr[2].trim());
			return dayMonthYear;
		}
		
		private boolean checkDueDate(String dueDate) {
			if (dueDate == null || dueDate.isEmpty()) return false;
			int[] dayMonthYear = dueDateIntArr(dueDate);
			int day = dayMonthYear[0];
			int month = dayMonthYear[1];
			int year = dayMonthYear[2];
			if (day>0 && day<32 && month>0 && month<13 && year>2000 && year<9999) return true;
			else return false;
		}
		
		class ViewHolder {
			TextView courseName, courseId, taskName, dueDate;
		}
	}
	
	public void setUpdatesFrequency(String freq) {
		
		if (freq.equals("Never")) {
			// cancel alarm if it exists. 
			if (alarmMgr != null) {
				alarmMgr.cancel(alarmIntent);
			}
			// turn off boot receiver.
			changeBootReceiverState(false);
		} else {
			alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(this, TaskParser.class);
			alarmIntent = PendingIntent.getService(this, 0, intent, 0);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			// Set the updates check to 7:00 am every day.
			calendar.set(Calendar.HOUR_OF_DAY, 7);
			calendar.set(Calendar.MINUTE, 0);
			int hours = 0;
			if (freq.equals("8 Hours")) hours = 8;
			else if (freq.equals("12 Hours")) hours = 12;
			else if (freq.equals("1 Day")) hours = 24;
			else if (freq.equals("2 Days")) hours = 48;
			else if (freq.equals("3 Days")) hours = 72;			
			else if (freq.equals("4 Days")) hours = 96;
			else if (freq.equals("5 Days")) hours = 120;
			else if (freq.equals("1 Week")) hours = 168;
			
			// Set repeating alarm.
			alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 
					1000*60*60*hours, alarmIntent);
			
			// turn on boot receiver.
			changeBootReceiverState(true);
		}
	}

	// Turn BootReceiver on\off.
	private void changeBootReceiverState(boolean enabled) {
		ComponentName receiver = new ComponentName(this, BootReceiver.class);
		PackageManager pm = this.getPackageManager();
		if (enabled) {
			pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 
					PackageManager.DONT_KILL_APP);
		} else {
			pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 
					PackageManager.DONT_KILL_APP);
		}
	}
	
	// A class that receives broadcasts after device boot, and sets an alarm for updates checking.
	class BootReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			String freq = prefs.getString(GeneralSettings.KEY_GS_UPDATES_FREQ, "Never");
			setUpdatesFrequency(freq);
		}
	}
}

//TasksInfo comparator, compares to tasks according to the parameter passed to the c'tor.
// sortBy values: 0 - due date. 1 - difficulty. 2 - importance. 3 - progress.
//class TasksInfoComparator implements Comparator<TasksInfo> {
//	
//	private int sortBy;
//	
//	public TasksInfoComparator(int sortBy) {
//		this.sortBy = sortBy;
//	}
//	
//	@Override
//	public int compare(TasksInfo task1, TasksInfo task2) {
//		switch(sortBy) {
//		
//		case 0:
//			return datesCompare(fixDate(task1.dueDate), fixDate(task2.dueDate));
//			
//		case 1:
//			return task2.difficulty - task1.difficulty;
//			
//		case 2:
//			return task2.importance - task1.importance;
//			
//		case 3:
//			return task2.progress - task1.progress;
//			
//		default:
//			return 1;
//		}
//		
//	}
//	
//	private String fixDate(String date) {
//		if (date == null || date.isEmpty()) return "";
//    	String[] dateArr = date.split("/");
//    	String day = dateArr[0];
//    	String month = dateArr[1];
//    	String year = dateArr[2];
//    	if (day.length() != 2) day = "0" + day;
//    	if (month.length() != 2) month = "0" + month;
//    	if (year.length() == 2) year = "20" + year;
//    	return day + "/" + month + "/" + year;
//    }
//	
//	// If date2 is after date1 return -1.
//    // if date2 is before date1 return 1.
//    // if date2 equals date1 return 0.
//    private int datesCompare(String date1, String date2) {
//    	
//    	String[] date1Arr = date1.split("/");
//    	String[] date2Arr = date2.split("/");
//    	if (date1Arr.length < 3) return -1;
//    	else if (date2Arr.length < 3) return 1;
//    	int year = date1Arr[2].compareTo(date2Arr[2]);
//	    if (year == 0) {
//		   int month = date1Arr[1].compareTo(date2Arr[1]);
//		   if (month == 0) {
//			   return date1Arr[0].compareTo(date2Arr[0]);
//		   } else {
//		  	   return month;
//		   }
//	    } else {
//		    return year;
//  	    }
//    }
//	
//}
