package com.technion.coolie.webcourse;

import com.actionbarsherlock.app.SherlockActivity;
import com.technion.coolie.R;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;


public class AnnouncementActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_activity_announcement);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    String title = intent.getStringExtra(AnnouncementsFragmentTab.EXTRA_MESSAGE_TITLE);
	    String content = intent.getStringExtra(AnnouncementsFragmentTab.EXTRA_MESSAGE_CONTENT);
	    String time_stamp = intent.getStringExtra(AnnouncementsFragmentTab.EXTRA_MESSAGE_TIME_STAMP);
	    
	    // Get text views
	    TextView titleTV = (TextView) findViewById(R.id.announcement_activity_title);
	    TextView contentTV = (TextView) findViewById(R.id.announcement_activity_content);
	    TextView timeStampTV = (TextView) findViewById(R.id.announcement_activity_time_stamp);
	    
	    // Update text
	    titleTV.setText(title);
	    contentTV.setText(content);
	    timeStampTV.setText(time_stamp); 
	    
	    // Update screen title
	    setTitle(title);
	}
}
