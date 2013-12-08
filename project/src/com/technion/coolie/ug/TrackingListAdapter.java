package com.technion.coolie.ug;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.technion.coolie.R;

public class TrackingListAdapter extends BaseAdapter{
	
	private final Context context;
	private final ArrayList<String>  values;
	
	public TrackingListAdapter(Context context, ArrayList<String> list)
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
		TextView courseNameTextView = (TextView)convertView.findViewById(R.id.ug_trackinglist_item_course_name);
		courseNameTextView.setText("234123");
		TextView courseNumberTextView = (TextView)convertView.findViewById(R.id.ug_trackinglist_item_course_number);
		courseNumberTextView.setText("מערכות הפעלה");
		TextView vacantPlacesTextView = (TextView)convertView.findViewById(R.id.ug_trackinglist_item_available_places);
		vacantPlacesTextView.setText("50");
	    return convertView;
	}

}
