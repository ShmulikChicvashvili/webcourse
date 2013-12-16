package com.technion.coolie.studybuddy.views;

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
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.models.StudyResource;
import com.technion.coolie.studybuddy.presenters.EditCoursePresenter;

public class EditCourse extends StudyBuddyActivity
{

	public static final String COURSE_ID = "courseID";
	private EditText courseName;
	private EditText courseNumber;
	private EditText lectureCount;
	private EditText tutorialsCount;
	private CheckBox lectureEnabled;
	private CheckBox tutorialsEnabled;
	private EditCoursePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_activity_edit_course);

		presenter = DataStore.getEditCoursePresenter();

		courseName = (EditText) findViewById(R.id.stb_course_name);
		courseNumber = (EditText) findViewById(R.id.stb_course_number);
		lectureCount = (EditText) findViewById(R.id.stb_lecture_count);
		tutorialsCount = (EditText) findViewById(R.id.stb_tutorial_count);
		Bundle extras = getIntent().getExtras();
		getSherlock().getActionBar().setTitle("Add course");
		if (extras != null && extras.containsKey(COURSE_ID))
		{
			getSherlock().getActionBar().setTitle("Edit Course");

			String courseIdentificator = extras.getString(COURSE_ID);
			if (!presenter.setCourse(Integer.parseInt(courseIdentificator)))
			{
				// TODO: handle no such course
			}

			try
			{
				courseName.setText(presenter.getCourseName());
				courseNumber.setText(presenter.getCourseIdAsString());
				lectureCount.setText(String.valueOf(presenter
						.getCourseResourceAmount(StudyResource.LECTURES)));
				tutorialsCount.setText(String.valueOf(presenter
						.getCourseResourceAmount(StudyResource.TUTORIALS)));

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
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
						// TODO: implement
						// presenter.commitCourse(idString,nameString,numLectures,numTutorials);
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
