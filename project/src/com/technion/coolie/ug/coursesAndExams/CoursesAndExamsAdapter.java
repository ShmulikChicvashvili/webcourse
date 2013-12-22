package com.technion.coolie.ug.coursesAndExams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.CourseItem;

public class CoursesAndExamsAdapter extends BaseExpandableListAdapter {

	private final Context context;
	private LayoutInflater inflater;
	private final ArrayList<CourseItem> parentItems;

	public CoursesAndExamsAdapter(final ArrayList<CourseItem> parents,
			final Context context) {
		parentItems = parents;
		this.context = context;
	}

	public void setInflater(final LayoutInflater inflater,
			final Activity activity) {
		this.inflater = inflater;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			final boolean isLastChild, View convertView, final ViewGroup parent) {

		if (convertView == null)
			convertView = inflater.inflate(
					R.layout.ug_courses_and_exams_child_item, null);

		final TextView moed = (TextView) convertView
				.findViewById(R.id.courses_and_exams_moed);
		final TextView examDate = (TextView) convertView
				.findViewById(R.id.courses_and_exams_exam_date);

		moed.setText(childPosition % 2 == 0 ? context.getString(R.string.moedA)
				: context.getString(R.string.moedB));
		final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy",
				Locale.getDefault());
		final Calendar cal = parentItems.get(groupPosition).getExams()
				.get(childPosition).getDate();
		final String date = cal != null ? formatter.format(parentItems
				.get(groupPosition).getExams().get(childPosition).getDate()
				.getTime()) : "";
		examDate.setText(date != "" ? date : context
				.getString(R.string.no_exam));
		return convertView;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, final ViewGroup parent) {

		if (convertView == null)
			convertView = inflater.inflate(
					R.layout.ug_courses_and_exams_group_item, null);
		final TextView courseName = (TextView) convertView
				.findViewById(R.id.courses_and_exams_course_name);
		final TextView courseId = (TextView) convertView
				.findViewById(R.id.courses_and_exams_course_id);
		final TextView points = (TextView) convertView
				.findViewById(R.id.courses_and_exams_points);
		courseName.setText(parentItems.get(groupPosition).getCoursName());
		courseId.setText("(" + parentItems.get(groupPosition).getCourseId()
				+ ")");
		points.setText(parentItems.get(groupPosition).getPoints());
		return convertView;
	}

	@Override
	public Object getChild(final int groupPosition, final int childPosition) {
		return null;
	}

	@Override
	public long getChildId(final int groupPosition, final int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(final int groupPosition) {
		return parentItems.get(groupPosition).getExams().size();
	}

	@Override
	public Object getGroup(final int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(final int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(final int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(final int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(final int groupPosition,
			final int childPosition) {
		return false;
	}

}
