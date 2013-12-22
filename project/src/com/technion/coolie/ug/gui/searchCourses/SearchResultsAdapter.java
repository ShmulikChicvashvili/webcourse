package com.technion.coolie.ug.gui.searchCourses;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.Course;

public class SearchResultsAdapter extends ArrayAdapter<Course> {

	Context context;
	List<Course> results;
	OnClickListener listener;

	public SearchResultsAdapter(final Context context,
			final List<Course> objects, final OnClickListener listener) {
		super(context, R.layout.ug_search_screen_fragment, objects);
		this.context = context;
		results = objects;
		this.listener = listener;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View row = convertView;
		CourseHolder holder = null;

		if (row == null) {
			final LayoutInflater inflater = ((Activity) context)
					.getLayoutInflater();
			row = inflater.inflate(R.layout.ug_search_list_item_row, parent,
					false);

			holder = new CourseHolder();

			holder.faculty = (TextView) row.findViewById(R.id.course_faculty);
			holder.number = (TextView) row.findViewById(R.id.course_number);
			holder.name = (TextView) row.findViewById(R.id.course_name);
			holder.points = (TextView) row.findViewById(R.id.course_points);

			row.setTag(holder);
		} else
			holder = (CourseHolder) row.getTag();

		final Course course = results.get(position);
		holder.faculty.setText(course.getFaculty().toString());
		holder.number.setText(course.getCourseNumber());
		holder.name.setText(course.getName());

		holder.points.setText(Float.toString(course.getPoints()));
		// TODO set color according to availabilty

		row.setOnClickListener(listener);

		return row;
	}

	static class CourseHolder {
		TextView faculty;
		TextView number;
		TextView name;
		TextView points;
		Color color; // does it have available slots color( or circle with color
						// in the top corner)

	}

}
