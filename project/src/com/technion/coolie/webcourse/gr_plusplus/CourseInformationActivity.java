package com.example.gr_plusplus;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.gr_plusplus.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;


public class CourseInformationActivity extends SherlockFragmentActivity {

	ActionBar.Tab mAnnouncements;
	ActionBar.Tab mOfficeHours;
	ActionBar.Tab mStaff;
	ActionBar.Tab mAssignments;
	
	Fragment mAnnouncementFragment = new AnnouncementsFragmentTab();
	Fragment mOfficeHoursFragment = new OfficeHoursFragmentTab();
	Fragment mStaffFragment = new StaffFragmentTab();
	Fragment mAssignmentsFragment = new AssignmentsFragmentTab();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses_information);
		
		// Get action bar
		ActionBar mBar = getSupportActionBar();

		// Hide Actionbar Icon
		mBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		mBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		mBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(CoursesActivity.EXTRA_MESSAGE);
	    

	    	
	    // Set inner fragments text  
	    ((OfficeHoursFragmentTab)mOfficeHoursFragment).SetIntentMessage("Office Hours: " + message);
	    ((StaffFragmentTab)mStaffFragment).SetIntentMessage(message);
	    ((AnnouncementsFragmentTab)mAnnouncementFragment).SetIntentMessage(message);
	    
	   
	    
		// Set Tab Icon and Titles
		mAnnouncements = mBar.newTab().setText(getString(R.string.announcements_tab_title));
		mOfficeHours = mBar.newTab().setText(getString(R.string.office_hours_tab_title));
		mStaff = mBar.newTab().setText(getString(R.string.staff_tab_title));
		mAssignments = mBar.newTab().setText(getString(R.string.assignments_tab_title));
		 
		// Set Tab Listeners
		mAnnouncements.setTabListener(new TabListener(mAnnouncementFragment));
		mOfficeHours.setTabListener(new TabListener(mOfficeHoursFragment));
		mStaff.setTabListener(new TabListener(mStaffFragment));
		mAssignments.setTabListener(new TabListener(mAssignmentsFragment));

		// Add tabs to actionbar
		mBar.addTab(mAnnouncements);
		mBar.addTab(mOfficeHours);
		mBar.addTab(mStaff);
		mBar.addTab(mAssignments);		
	}

}
