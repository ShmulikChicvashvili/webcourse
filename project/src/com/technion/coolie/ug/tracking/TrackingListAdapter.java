package com.technion.coolie.ug.tracking;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technion.coolie.R;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseItem;
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
		final ImageButton ib = (ImageButton) convertView.findViewById(R.id.ug_trackinglist_item_rishum_btn); 
		final TextView courseNumberTextView = (TextView) convertView
				.findViewById(R.id.ug_trackinglist_item_course_number);
		CourseKey ck = (CourseKey) getItem(position);
		courseNumberTextView.setText(ck.getNumber());

		final TextView courseNameTextView = (TextView) convertView
				.findViewById(R.id.ug_trackinglist_item_course_name);
		Course course = UGDatabase.getInstance(context).getCourseByKey(ck);
		String name = course.getName();
		courseNameTextView.setText(name); // get course
																// name from
																// local
																// database

		final TextView vacantPlacesTextView = (TextView) convertView
				.findViewById(R.id.ug_trackinglist_item_available_places);
		int vacantPlaces = course.getFreePlaces();
		vacantPlacesTextView.setText(String.valueOf(vacantPlaces)); // get number of vacant places from
											// local database
		
		final OnClickListener makeListener = new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Toast.makeText(context, "CLICKED", Toast.LENGTH_SHORT).show();
	        }
	    };
	    ib.setOnClickListener(makeListener);
		return convertView;
	}

	public void remove(int position) {
		values.remove(position);
		UGDatabase.getInstance(context).setTrackingCourses(values);
		notifyDataSetChanged();
	}

	public void insert(int position, CourseKey item) {
		values.add(position, item);
		UGDatabase.getInstance(context).setTrackingCourses(values);
		notifyDataSetChanged();
	}
	
	public void remove(CourseItem courseItem) {
		Log.i("2","name :"+courseItem.getCoursName());
		Log.i("2","id :"+courseItem.getCourseId());
		
		Log.i("2","values before :"+values.size());
		
		values.remove(courseItem);
		Log.i("2","values after :"+values.size());
		UGDatabase.getInstance(context).setTrackingCourses(values);
		notifyDataSetChanged();
	}
	
	public void insert(CourseKey item) {
		values.add(item);
		UGDatabase.getInstance(context).setTrackingCourses(values);
		notifyDataSetChanged();
	}

}
