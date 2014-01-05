package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetFragmentListAdapter extends BaseAdapter {

	private final Context context;
	private final List<AccomplishedCourse> values = new ArrayList<AccomplishedCourse>();

	public GradesSheetFragmentListAdapter(final Context context,
			final List<AccomplishedCourse> list) {
		this.context = context;
		values.addAll(list);
		// Collections.sort(values);
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(final int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.ug_list_item_grades_sheet_fragment, parent, false);
		}
		final AccomplishedCourse course = (AccomplishedCourse) getItem(position);
		if (course.getCourseNumber() != null) {
			final TextView courseNameTextView = (TextView) convertView
					.findViewById(R.id.ug_grades_sheet_fragment_course_name);
			courseNameTextView.setText(course.getName());

			final TextView pointsTextView = (TextView) convertView
					.findViewById(R.id.ug_grades_sheet_fragment_points);
			pointsTextView.setText(course.getPoints());

			final TextView gradeTextView = (TextView) convertView
					.findViewById(R.id.ug_grades_sheet_fragment_grade);
			gradeTextView.setText(course.getGrade());
		}
		return convertView;
	}

}
