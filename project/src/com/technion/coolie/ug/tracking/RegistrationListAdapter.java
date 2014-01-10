package com.technion.coolie.ug.tracking;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.CourseItem;

public class RegistrationListAdapter extends ArrayAdapter<CourseItem> {

	private final List<CourseItem> items;
	private final LayoutInflater vi;

	static class ViewHolder {
		TextView courseNameTextView, courseNumberTextView, groupNumberTextView;
	}

	public RegistrationListAdapter(final Context context,
			final List<CourseItem> items) {
		super(context, 0, items);
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = vi.inflate(R.layout.ug_list_item_registration,
					parent, false);
			// set up the ViewHolder
			viewHolder = new ViewHolder();
			viewHolder.courseNameTextView = (TextView) convertView
					.findViewById(R.id.ug_regisration_item_course_name);
			viewHolder.courseNumberTextView = (TextView) convertView
					.findViewById(R.id.ug_regisration_item_course_number);
			viewHolder.groupNumberTextView = (TextView) convertView
					.findViewById(R.id.ug_regisration_item_group_number);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CourseItem coureItem = items.get(position);
		if (coureItem != null) {
			viewHolder.courseNameTextView.setText(coureItem.getCoursName());
			viewHolder.groupNumberTextView.setText(coureItem.getCourseId());
			// TODO: add group number member to CourseItem + add parsing to
			// group number
			viewHolder.groupNumberTextView.setText("00");
		}
		return convertView;
	}
}
