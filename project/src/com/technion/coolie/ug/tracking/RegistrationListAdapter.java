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
	private final Context context;


	public RegistrationListAdapter(final Context context,
			final List<CourseItem> items) {
		super(context, 0, items);
		this.context = context;
		this.items = items;
		
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ug_list_item_registration,
					parent, false);
		}			
		final TextView courseNameTextView = (TextView) convertView
					.findViewById(R.id.ug_regisration_item_course_name);
		final TextView courseNumberTextView = (TextView) convertView
					.findViewById(R.id.ug_regisration_item_course_number);
		final TextView groupNumberTextView = (TextView) convertView
					.findViewById(R.id.ug_regisration_item_group_number);

		final CourseItem coureItem = items.get(position);
		if (coureItem != null) {
			courseNameTextView.setText(coureItem.getCoursName());
			courseNumberTextView.setText(coureItem.getCourseId());
			// TODO: add group number member to CourseItem + add parsing to
			// group number
			groupNumberTextView.setText("00");
		}
		return convertView;
	}
}
