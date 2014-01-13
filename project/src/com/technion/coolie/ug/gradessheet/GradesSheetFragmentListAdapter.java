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

	private final LayoutInflater vi;
	private final List<AccomplishedCourse> values = new ArrayList<AccomplishedCourse>();

	static class ViewHolder {
		TextView courseNameTextView, pointsTextView, gradeTextView;
	}

	public GradesSheetFragmentListAdapter(final Context context,
			final List<AccomplishedCourse> list) {
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// add to list only grades items
		for (AccomplishedCourse ac : list)
			if (ac.getCourseNumber() != null) {
				values.add(ac);
			}
		// values.addAll(list);
		Collections.sort(values);
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
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = vi.inflate(
					R.layout.ug_list_item_grades_sheet_fragment, parent, false);

			// set up the ViewHolder
			viewHolder = new ViewHolder();
			viewHolder.courseNameTextView = (TextView) convertView
					.findViewById(R.id.ug_grades_sheet_fragment_course_name);
			viewHolder.pointsTextView = (TextView) convertView
					.findViewById(R.id.ug_grades_sheet_fragment_points);
			viewHolder.gradeTextView = (TextView) convertView
					.findViewById(R.id.ug_grades_sheet_fragment_grade);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AccomplishedCourse course = (AccomplishedCourse) getItem(position);
		if (course != null) {
			viewHolder.courseNameTextView.setText(course.getName());
			viewHolder.pointsTextView.setText(course.getPoints());
			viewHolder.gradeTextView.setText(course.getGrade());
		}
		return convertView;
	}
}