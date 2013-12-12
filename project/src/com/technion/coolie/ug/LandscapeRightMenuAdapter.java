package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LandscapeRightMenuAdapter extends BaseAdapter {
	
	private final Context context;
	private final List<String> values;

	public LandscapeRightMenuAdapter(Context context, String[] list) {
		this.context = context;
		values = Arrays.asList(list);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ug_list_item_landscape_right_menu, parent, false);
		}
		
		TextView itemTextView = (TextView) convertView.findViewById(R.id.ug_list_item_landscape_right_menu_text);
		String s = (String)getItem(position);
		itemTextView.setText(s);
		//ImageView image = (ImageView) convertView.findViewById(R.id.ug_list_item_landscape_right_menu_image);
		//image.setImageResource(R.drawable.abs__btn_cab_done_default_holo_dark);
		return convertView;
	}
}
