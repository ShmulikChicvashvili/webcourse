package com.technion.coolie.studybuddy.Views;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Models.Course;

public class EditCourse extends StudyBuddyActivity
{

	public static final String courseId = "courseID";
	private EditText courseName;
	private EditText courseNumber;
	private EditText lectureCount;
	private EditText tutorialsCount;
	private CheckBox lectureEnabled;
	private CheckBox tutorialsEnabled;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_activity_edit_course);
		
		courseName = (EditText) findViewById(R.id.stb_course_name);
		courseNumber = (EditText) findViewById(R.id.course_number);
		lectureCount = (EditText) findViewById(R.id.stb_lecture_count);
		tutorialsCount = (EditText) findViewById(R.id.stb_tutorial_count);
		Bundle extras = getIntent().getExtras();
		getSherlock().getActionBar().setTitle("Add course");
		if (extras != null)
		{
			getSherlock().getActionBar().setTitle("Edit Course");
			String courseIdentificator = extras.getString(courseId);
			//TODO read course Data and set 
			
		}
	

		lectureEnabled = ((CheckBox) findViewById(R.id.stb_include_lectures));
		lectureEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				lectureCount.setEnabled(isChecked);
			}
		});
		tutorialsEnabled = ((CheckBox) findViewById(R.id.stb_include_tutorials));
		tutorialsEnabled
				.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked)
					{
						tutorialsCount.setEnabled(isChecked);
					}
				});
		((Button) findViewById(R.id.stb_btn_cancel))
				.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						NavUtils.navigateUpFromSameTask(EditCourse.this);

					}
				});
		((Button) findViewById(R.id.stb_btn_save))
				.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO add to DB
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSherlock().getMenuInflater().inflate(R.menu.stb_edit_course, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
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
