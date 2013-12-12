package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.technion.coolie.R;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;

public class TrackingListAdapter extends BaseAdapter{
	
	private final Context context;
	private final List<CourseKey>  values;
	
	public TrackingListAdapter(Context context, List<CourseKey> list)
	{
		this.context = context;
		this.values = list;
	}
	
	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ug_list_item_tracking_list, parent, false);
		}
		TextView courseNumberTextView = (TextView)convertView.findViewById(R.id.ug_trackinglist_item_course_number);
		courseNumberTextView.setText(((CourseKey)getItem(position)).getNumber());
		
		TextView courseNameTextView = (TextView)convertView.findViewById(R.id.ug_trackinglist_item_course_name);
		courseNameTextView.setText("מערכות הפעלה"); // get course name from local database
		
		TextView vacantPlacesTextView = (TextView)convertView.findViewById(R.id.ug_trackinglist_item_available_places);
		vacantPlacesTextView.setText("50"); // get number of vacant places from local database
	    return convertView;
	}

}
