package com.technion.coolie.assignmentor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.technion.coolie.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewTask extends FragmentActivity implements View.OnClickListener {
	
	EditText taskName, courseName, courseId;
	TextView dueDate;
	Button buttonAdd, buttonCancel;
	
	private OnDateSetListener mDateListener;
	

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am_activity_add_new_task);
		
		getActionBar().hide();
		
		taskName = (EditText) findViewById(R.id.am_new_task_name_edittext);
		courseName = (EditText) findViewById(R.id.am_new_task_course_name_edittext);
		courseId = (EditText) findViewById(R.id.am_new_task_course_id_edittext);
		dueDate = (TextView) findViewById(R.id.am_new_task_due_date_textview);
		
		final Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		dueDate.setText(df.format(c.getTime()));
		
		mDateListener = new OnDateSetListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onDateSet(DatePicker view, int year, int month,
					int day) {
				
				GregorianCalendar gc = new GregorianCalendar(year, month, day);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				dueDate.setText(df.format(gc.getTime()));
			}
		};
		
		dueDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment(mDateListener);
				newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
		
		buttonAdd = (Button) findViewById(R.id.am_new_task_button_add);
		buttonAdd.setOnClickListener(this);
		
		buttonCancel = (Button) findViewById(R.id.am_new_task_button_cancel);
		buttonCancel.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {

		Intent resultIntent = new Intent();
		
		switch(v.getId()) {
		
			case R.id.am_new_task_button_add:
				String[] newTasksInfo = new String[4];
				newTasksInfo[0] = taskName.getText().toString();
				newTasksInfo[1] = courseName.getText().toString();
				newTasksInfo[2] = courseId.getText().toString();
				newTasksInfo[3] = dueDate.getText().toString();
				resultIntent.putExtra("newTasksInfo", newTasksInfo);
				setResult(RESULT_OK, resultIntent);
				finish();
				break;
				
			case R.id.am_new_task_button_cancel:
				setResult(RESULT_CANCELED, resultIntent);
				finish();
				break;
		}
	}
	
}

class DatePickerFragment extends DialogFragment
				implements DatePickerDialog.OnDateSetListener {
	
	private OnDateSetListener mOnDateSetListener;
	
	public DatePickerFragment(OnDateSetListener callback) {
		mOnDateSetListener = (OnDateSetListener) callback;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), mOnDateSetListener, year, month, day);
	}
	
	@Override
	public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
		
	}
}


