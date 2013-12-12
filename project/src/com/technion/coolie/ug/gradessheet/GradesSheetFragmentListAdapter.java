package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.AccomplishedCourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class GradesSheetFragmentListAdapter extends BaseAdapter
{
		
	private final Context context;
	private final List<AccomplishedCourse>  values = new ArrayList<AccomplishedCourse>();
	
	public GradesSheetFragmentListAdapter(Context context, List<AccomplishedCourse> list)
	{
		this.context = context;
		values.addAll(list);
		Collections.sort(values);
	}
	
	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ug_list_item_grades_sheet_fragment, parent, false);
		}
		AccomplishedCourse course = (AccomplishedCourse) getItem(position);
		
		TextView courseNameTextView = (TextView)convertView.findViewById(R.id.ug_grades_sheet_fragment_course_name);
		courseNameTextView.setText(course.getName());
		
		TextView pointsTextView = (TextView)convertView.findViewById(R.id.ug_grades_sheet_fragment_points);
		pointsTextView.setText(course.getPoints());
		
		TextView gradeTextView = (TextView)convertView.findViewById(R.id.ug_grades_sheet_fragment_grade);
		gradeTextView.setText(course.getGrade());
		
		
	    return convertView;
	}
	

	}
