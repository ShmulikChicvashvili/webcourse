package com.technion.coolie.ug.gui.courseDisplay;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.RegistrationGroup;

public class CourseGroupsAdapter extends ArrayAdapter<RegistrationGroup> {

	Context context;
	List<RegistrationGroup> results;
	OnClickListener listener;

	public CourseGroupsAdapter(final Context context,
			final List<RegistrationGroup> objects,
			final OnClickListener listener) {
		super(context, R.layout.ug_course_screen_layout, objects);
		this.context = context;
		results = objects;
		this.listener = listener;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View row = convertView;
		GroupHolder holder = null;

		if (row == null) {
			final LayoutInflater inflater = ((Activity) context)
					.getLayoutInflater();
			row = inflater.inflate(R.layout.ug_course_screen_group_item_row,
					parent, false);

			holder = new GroupHolder();

			// holder.faculty = (TextView)
			// row.findViewById(R.id.course_faculty);
			// holder.number = (TextView) row.findViewById(R.id.course_number);
			// holder.name = (TextView) row.findViewById(R.id.course_name);
			// holder.points = (TextView) row.findViewById(R.id.course_points);

			row.setTag(holder);
		} else
			holder = (GroupHolder) row.getTag();

		// final RegistrationGroup course = results.get(position);
		// holder.faculty.setText(course.getFaculty().toString());
		// holder.number.setText(course.getCourseNumber());
		// holder.name.setText(course.getName());

		// holder.points.setText(Float.toString(course.getPoints()));
		// // TODO set color according to availabilty

		row.setOnClickListener(listener);

		return row;
	}

	static class GroupHolder {
		// TextView faculty;
		// TextView number;
		// TextView name;
		// TextView points;
		// Color color; // does it have available slots color( or circle with
		// color
		// // in the top corner)

	}

}
