package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.technion.coolie.R;

public class CoursesAndExamsAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<CourseItem> parentItems;

	public CoursesAndExamsAdapter(ArrayList<CourseItem> parents,
			ArrayList<ExamItem> childern) {
		this.parentItems = parents;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.ug_courses_and_exams_child_item, null);
		}
		TextView moed = (TextView) convertView
				.findViewById(R.id.courses_and_exams_moed);
		TextView examDate = (TextView) convertView
				.findViewById(R.id.courses_and_exams_exam_date);
		moed.setText(parentItems.get(groupPosition).getExams()
				.get(childPosition).getMoed());
		examDate.setText(parentItems.get(groupPosition).getExams()
				.get(childPosition).getExamDate());
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.ug_courses_and_exams_group_item, null);
		}
		// ImageView im = (ImageView) convertView.findViewById(R.id.tag_img);
		TextView courseName = (TextView) convertView
				.findViewById(R.id.courses_and_exams_course_name);
		TextView courseId = (TextView) convertView
				.findViewById(R.id.courses_and_exams_course_id);
		TextView points = (TextView) convertView
				.findViewById(R.id.courses_and_exams_points);
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
		return parentItems.get(groupPosition).getExams().size();
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
