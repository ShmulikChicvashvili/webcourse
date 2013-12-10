package com.technion.coolie.assignmentor;

import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.assignmentor.EnhancedListView.Undoable;
import com.technion.coolie.assignmentor.TaskSettings.TaskSettingsFragment;
import com.technion.coolie.CollieNotification;

public class MainActivity extends CoolieActivity implements MenuItem.OnMenuItemClickListener {
	
	private static final int NEW_TASK_REQUEST = 3535;
	private static final int TASK_SETTINGS_REQUEST = 4545;
	
	public static final String AM_TAG = "AssignMentor";
	public static final String COURSE_LIST = "CourseList";
	public static final String DATA_FETCHED = "com.technion.coolie.assignmentor.DATA_FETCHED";
	
	public static MyAdapter mAdapter;
	
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	private BroadcastReceiver mReceiver;
	private EnhancedListView mListView;
	private ProgressBar mProgressBar;
	private TextView progressPercent;
	
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
				Log.i(AM_TAG, "broadcast received!");
				String action = i.getAction();
				if (action.equals(DATA_FETCHED)) {
					Log.i(MainActivity.AM_TAG, "broadcast DATA_FETCHED received!");
					mAdapter.updateView();
				}
			}
		};
		IntentFilter intentFilter = new IntentFilter(DATA_FETCHED);
		registerReceiver(mReceiver, intentFilter);
		
		mProgressBar = (ProgressBar) findViewById(R.id.am_technion_tasks_progress_bar);
		progressPercent = (TextView) findViewById(R.id.am_technion_tasks_progress_percent);
		
		mListView = (EnhancedListView) findViewById(R.id.am_technion_tasks_listview);
		mAdapter = new MyAdapter(this);
		mListView.setAdapter(mAdapter);
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
				Log.i(AM_TAG, "Long click on item: " + String.valueOf(position));
				return true;
			}
		});
		
		// Handles the re-adding of an item that was deleted (user pressed Undo).
		mListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
			
			@Override
			public Undoable onDismiss(EnhancedListView listView,final int position) {
				final TasksInfo removedItem = (TasksInfo) mAdapter.getItem(position);
//				final boolean isDone = removedItem.isDone;
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
		mListView.setUndoStyle(EnhancedListView.UndoStyle.SINGLE_POPUP);
		mListView.setUndoHideDelay(3000);
		mListView.enableSwipeToDismiss();
		mListView.setSwipeDirection(EnhancedListView.SwipeDirection.BOTH);
		
		// Setting a temporary course list to fetch from the web.
		// This list is passed to the service in the intent's extra data.
		// Start the update service by pressing 'Sort by progress' on the overflow menu.
		courseList.add("234107");
		courseList.add("234114");
		courseList.add("236523");
		courseList.add("236350");
		courseList.add("236360");
		
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
				Log.i(AM_TAG, "Result OK from tasks settings at position: " + String.valueOf(position));
			} else {
				
			}
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch(item.getItemId()) {
		
			case R.id.action_sort_by_due_date:
				Toast.makeText(this, "Sorting By Due Date", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.action_sort_by_diff:
				Toast.makeText(this, "Sorting By Difficulty Level", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.action_sort_by_imp:
				Toast.makeText(this, "Sorting By Importance Level", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.action_sort_by_prog:
//				Toast.makeText(this, "Sorting By Progress Level", Toast.LENGTH_SHORT).show();
				Intent serviceIntent = new Intent(this, TaskParser.class);
				serviceIntent.putStringArrayListExtra(COURSE_LIST, courseList);
				startService(serviceIntent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.am_main, menu);
		// Giving the settings button item some random id number.
		int settingsButtonId = 509;
		MenuItem settingsButton = menu.add(0, settingsButtonId, 0, "Settings");
		settingsButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		settingsButton.setIcon(R.drawable.am_settings);
		settingsButton.setOnMenuItemClickListener(this);
		
		// Giving the new task button some random id number.
		int newTaskButtonId = 547;
		MenuItem newTaskButton = menu.add(0, newTaskButtonId, 0, "New Task");
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
		ArrayList<TasksInfo> myItems;
		private Integer totalNumOfTasks;
		private Integer numOfDoneTasks;
		private ImageSwitcher mSwitcher;
		private Animation fadeIn, fadeOut;
		
		public MyAdapter(Context c) {
			this.context = c;
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			totalNumOfTasks = 0;
			numOfDoneTasks = 0;
			
			myItems = new ArrayList<TasksInfo>();
			String taskName = "H.W ";
			String courseName = "Android Project ";
			String courseId = "236503 ";
			String dueDate = "Due Date ";
			
			for(int i=0; i<9; i++) {
				Integer j = i + 1;
				String index = j.toString();
				
				TasksInfo newTask = new TasksInfo(new SpannableString(taskName + index), courseName + index, courseId + index, dueDate + index);
				myItems.add(newTask);
				totalNumOfTasks++;
			}
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
			if (myItems.get(position).isDone) numOfDoneTasks--;
			totalNumOfTasks--;
			myItems.remove(position);
			updateView();
		}
		
		// Used to insert tasks manually.
		public void insert(int position, TasksInfo newTask) {
			
			if (position >= 0) {
				// Task is re-added (user deleted and then pressed undo) to its original position.
				if (newTask.isDone) { 
					numOfDoneTasks++;
				}
				myItems.add(position, newTask);
			} else {
				myItems.add(newTask);
			}
			totalNumOfTasks++;
			updateView();
		}
		
		// Used to insert new fetched task from web.
		// *NO* call to notifyDataSetChanged() since this method is being called from
		// a service running on thread different than the UI thread, hence, calling
		// notifyDataSetChange() will throw an exception.
		public void insertFetched(TasksInfo fetchedTask) {
			Log.i(AM_TAG, "insertFetched -> myItems size (before adding): " + String.valueOf(myItems.size()));
			myItems.add(fetchedTask);
			Log.i(AM_TAG, "insertFetched -> myItems size (after adding): " + String.valueOf(myItems.size()));
			totalNumOfTasks++;
			
			// Pop up notification that a new task was found.
			String notificationText = fetchedTask.taskName + " - " + fetchedTask.courseName;
			CollieNotification cn = new CollieNotification("New H.W!", notificationText, 
					MainActivity.this, CollieNotification.Priority.IMMEDIATELY, 
					true, getApplicationContext());
			cn.sendNotification();
		}
		
		public void updateView() {
			notifyDataSetChanged();
			updateProgress();
		}
		
		public void markAsDone(int position) {
			myItems.get(position).isDone = true;
			myItems.get(position).taskName.setSpan(new StrikethroughSpan(), 0, 
					myItems.get(position).taskName.length(), 0);
			numOfDoneTasks++;
			updateView();
		}
		
		public void markAsUndone(int position) {
			myItems.get(position).isDone = false;
			StrikethroughSpan[] stspans = myItems.get(position).taskName.getSpans(0, 
					myItems.get(position).taskName.length(), StrikethroughSpan.class);
			for(StrikethroughSpan st : stspans) {
				myItems.get(position).taskName.removeSpan(st);
			}
			numOfDoneTasks--;
			updateView();
		}
		
		public ArrayList<TasksInfo> getList() {
			return myItems;
		}
		
		public void setProperties(int position, int difficulty, int importance, int progress) {
			myItems.get(position).difficulty = difficulty;
			myItems.get(position).importance = importance;
			myItems.get(position).progress = progress;
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
			
			updateView();
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

class TasksInfo {
	SpannableString taskName;
	String courseId, courseName, dueDate;
	Boolean isDone = false;
	int difficulty, importance, progress;
	
	
	public TasksInfo(SpannableString taskName, String courseName, String courseId, String dueDate) {
		this.taskName = taskName;
		this.courseName = courseName;
		this.courseId = courseId;
		this.dueDate = dueDate;
	}
	
	public String[] getStringArrInfo() {
		return new String[] { taskName.toString(), courseName, courseId, dueDate };
	}
	
	public int[] getIntArrInfo() {
		return new int[] { difficulty, importance, progress };
	}
	
	// Eclipse Auto-Generated functions hashCode() and equals()
	// Used to compare new fetched tasks against tasks in the list.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseId == null) ? 0 : courseId.hashCode());
		result = prime * result
				+ ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TasksInfo other = (TasksInfo) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!(taskName.toString()).equals(other.taskName.toString()))
			return false;
		return true;
	}
}
