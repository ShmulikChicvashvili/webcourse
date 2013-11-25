package com.technion.coolie.assignmentor;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.assignmentor.EnhancedListView.Undoable;

public class MainActivity extends CoolieActivity {
	

	public static final String AM_TAG = "AssignMentor";
	private static final int NEW_TASK_REQUEST = 3535;
	public static final String COURSE_LIST = "CourseList";
	public static final String DATA_FETCHED = "com.technion.coolie.assignmentor.DATA_FETCHED";
	
	public static MyAdapter mAdapter;
	private BroadcastReceiver mReceiver;
	EnhancedListView mListView;
	SpinnerAdapter mSpinnerAdapter;
	OnNavigationListener mOnNavigationListener;
	
	ProgressBar mProgressBar;
	TextView progressPercent;
	
	// Temporary list to hold course ids.
	ArrayList<String> courseList = new ArrayList<String>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am_activity_main);
		
//		PreferenceManager.setDefaultValues(this, R.xml.am_preferences, false);
		
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
				String taskName = ((TasksInfo)mAdapter.getItem(position)).taskName.toString();
				String courseName = ((TasksInfo)mAdapter.getItem(position)).courseName;
				Intent myIntent = new Intent(getApplicationContext(), TaskSettings.class);
				myIntent.putExtra("taskName", taskName);
				myIntent.putExtra("courseName", courseName);
				startActivity(myIntent, opts.toBundle());
				return true;
			}
		});
		
		// Handles the re-adding of an item that was deleted (user pressed Undo).
		mListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
			
			@Override
			public Undoable onDismiss(EnhancedListView listView,final int position) {
				final TasksInfo removedItem = (TasksInfo) mAdapter.getItem(position);
				final boolean isDone = removedItem.isDone;
				mAdapter.remove(position);
				return new EnhancedListView.Undoable() {
					
					@Override
					public void undo() {
						String[] info = removedItem.getStringArrInfo();
						mAdapter.insert(position, new SpannableString(info[0]), info[1], info[2], info[3], isDone);
					}
				};
			}
		});
		
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
				String[] newTasksInfo = data.getStringArrayExtra("newTasksInfo");
				mAdapter.insert(-1,new SpannableString(newTasksInfo[0]), newTasksInfo[1],
						newTasksInfo[2], newTasksInfo[3], false);
				
				Toast.makeText(this, "New Task Added!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "No New Task!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent myIntent;
		ActivityOptions opts = ActivityOptions.makeCustomAnimation(MainActivity.this, 
				R.anim.am_fade_in, R.anim.am_hold);
		
		switch(item.getItemId()) {
		
			case R.id.am_action_settings:
				myIntent = new Intent(MainActivity.this, GeneralSettings.class);
				startActivity(myIntent,opts.toBundle());
				break;
				
			case R.id.am_action_new_task:
				myIntent = new Intent(this, AddNewTask.class);
				startActivityForResult(myIntent, NEW_TASK_REQUEST, opts.toBundle());
				break;
				
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

		getMenuInflater().inflate(R.menu.am_main, menu);
		return super.onCreateOptionsMenu(menu);
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
//			String taskName = "H.W ";
//			String courseName = "Android Project ";
//			String courseId = "236503 ";
//			String dueDate = "Due Date ";
//			
//			for(int i=0; i<9; i++) {
//				Integer j = i + 1;
//				String index = j.toString();
//				
//				TasksInfo newTask = new TasksInfo(new SpannableString(taskName + index), courseName + index, courseId + index, dueDate + index);
//				myItems.add(newTask);
//				totalNumOfTasks++;
//			}
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
			notifyDataSetChanged();
			updateProgress();
		}
		
		// Used to insert tasks manually.
		public void insert(int position, SpannableString taskName, String courseName, 
				String courseId, String dueDate, boolean isDone) {
			
			TasksInfo newTask = new TasksInfo(new SpannableString(taskName), courseName, courseId, dueDate);
			if (position >= 0) {
				// Task is re-added (user deleted and then pressed undo) to its original position.
				if (isDone) { 
					newTask.taskName.setSpan(new StrikethroughSpan(), 0, newTask.taskName.length(), 0);
					newTask.isDone = true;
					numOfDoneTasks++;
				}
				myItems.add(position, newTask);
			} else {
				myItems.add(newTask);
			}
			totalNumOfTasks++;
			notifyDataSetChanged();
			updateProgress();
		}
		
		// Used to insert new fetched tasks from web.
		// *NO* call to notifyDataSetChanged() since this method is being called from
		// a service running on thread different than the UI thread, hence, calling
		// notifyDataSetChange() is not allowed.
		public void insertFetched(List<TasksInfo> fetchedTasks) {
			Log.i(AM_TAG, "insertFetched -> fetchedTasks size: " + String.valueOf(fetchedTasks.size()));
			Log.i(AM_TAG, "insertFetched -> myItems size (before adding): " + String.valueOf(myItems.size()));
			myItems.addAll(fetchedTasks);
			Log.i(AM_TAG, "insertFetched -> myItems size (after adding): " + String.valueOf(myItems.size()));
			totalNumOfTasks = totalNumOfTasks + fetchedTasks.size();
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
			notifyDataSetChanged();
			updateProgress();
		}
		
		public void markAsUndone(int position) {
			myItems.get(position).isDone = false;
			StrikethroughSpan[] stspans = myItems.get(position).taskName.getSpans(0, 
					myItems.get(position).taskName.length(), StrikethroughSpan.class);
			for(StrikethroughSpan st : stspans) {
				myItems.get(position).taskName.removeSpan(st);
			}
			numOfDoneTasks--;
			notifyDataSetChanged();
			updateProgress();
		}
		
		public ArrayList<TasksInfo> getList() {
			return myItems;
		}

		class ViewHolder {
			TextView courseName, courseId, taskName, dueDate;
		}
	}

	// Turn BootReceiver class on\off.
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
//			settings = getSharedPreferences(AM_PREFS, 0);
//			int freq = settings.getInt(UPDATES_KEY, 0);
//			setUpdatesFrequency(freq);
		}
	}
}

class TasksInfo {
	SpannableString taskName;
	String courseId, courseName, dueDate;
	Boolean isDone = false;
	
	public TasksInfo(SpannableString taskName, String courseName, String courseId, String dueDate) {
		this.taskName = taskName;
		this.courseName = courseName;
		this.courseId = courseId;
		this.dueDate = dueDate;
	}
	
	public String[] getStringArrInfo() {
		return new String[] { taskName.toString(), courseName, courseId, dueDate };
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
