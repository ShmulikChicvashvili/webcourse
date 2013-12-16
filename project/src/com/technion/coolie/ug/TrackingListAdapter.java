package com.technion.coolie.ug;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.CourseKey;

public class TrackingListAdapter extends BaseAdapter {

	private final Context context;
	private final List<CourseKey> values;

	public TrackingListAdapter(final Context context, final List<CourseKey> list) {
		this.context = context;
		values = list;
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
			convertView = inflater.inflate(R.layout.ug_list_item_tracking_list,
					parent, false);
		}
		final TextView courseNumberTextView = (TextView) convertView
				.findViewById(R.id.ug_trackinglist_item_course_number);
		courseNumberTextView.setText(((CourseKey) getItem(position))
				.getNumber());

		final TextView courseNameTextView = (TextView) convertView
				.findViewById(R.id.ug_trackinglist_item_course_name);
		courseNameTextView.setText("׳�׳¢׳¨׳›׳•׳× ׳”׳₪׳¢׳�׳”"); // get course
																// name from
																// local
																// database

		final TextView vacantPlacesTextView = (TextView) convertView
				.findViewById(R.id.ug_trackinglist_item_available_places);
		vacantPlacesTextView.setText("50"); // get number of vacant places from
											// local database
		return convertView;
	}

}
