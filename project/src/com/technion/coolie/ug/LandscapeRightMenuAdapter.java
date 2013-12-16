package com.technion.coolie.ug;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;

public class LandscapeRightMenuAdapter extends BaseAdapter {

	private final Context context;
	private final List<String> values;

	public LandscapeRightMenuAdapter(final Context context, final String[] list) {
		this.context = context;
		values = Arrays.asList(list);
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
			convertView = inflater.inflate(
					R.layout.ug_list_item_landscape_right_menu, parent, false);
		}

		final TextView itemTextView = (TextView) convertView
				.findViewById(R.id.ug_list_item_landscape_right_menu_text);
		final String s = (String) getItem(position);
		itemTextView.setText(s);
		return convertView;
	}
}
