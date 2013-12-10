package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.technion.coolie.R;

public class CoursesAndExamsAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	private ArrayList<CourseItem> parentItems;
	private ArrayList<String> child;
	public CoursesAndExamsAdapter(ArrayList<CourseItem> parents,
			ArrayList<Object> childern) {
		this.parentItems = parents;
		this.childtems = childern;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		child = (ArrayList<String>) childtems.get(groupPosition);

		TextView textView = null;

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.ug_courses_and_exams_tab_row, null);
		}

		textView = (TextView) convertView.findViewById(R.id.textView1);
		textView.setText(child.get(childPosition));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(activity, child.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.ug_courses_and_exams_tab_group, null);
		}
		TextView courseName = (TextView) convertView.findViewById(R.id.courses_and_exams_course_name);
		TextView courseId = (TextView) convertView.findViewById(R.id.courses_and_exams_course_id);
		TextView points = (TextView) convertView.findViewById(R.id.courses_and_exams_points);
//		((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
//		((CheckedTextView) convertView).setChecked(isExpanded);
		courseName.setText(parentItems.get(groupPosition).getCoursName());
		courseId.setText(parentItems.get(groupPosition).getCourseId());
		points.setText(parentItems.get(groupPosition).getPoints());
		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) childtems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
