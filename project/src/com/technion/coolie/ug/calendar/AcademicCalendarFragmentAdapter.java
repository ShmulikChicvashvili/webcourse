package com.technion.coolie.ug.calendar;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarFragmentAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	private LayoutInflater vi;

	public AcademicCalendarFragmentAdapter(Context context,
			ArrayList<Item> items) {
		super(context, 0, items);
		this.context = context;
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		final Item i = items.get(position);
		if (i != null) {
			if (i.isSection()) {
				CalendarSectionItem si = (CalendarSectionItem) i;
				v = vi.inflate(R.layout.ug_calendar_list_item_section, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				final TextView sectionView = (TextView) v
						.findViewById(R.id.list_item_section_text);
				sectionView.setText(si.getMonth());
				sectionView.setBackgroundColor(Color.parseColor("#0099b3"));

			} else {
				AcademicCalendarEvent ei = (AcademicCalendarEvent) i;
				v = vi.inflate(R.layout.ug_calendar_list_item_entry, null);
				final TextView event = (TextView) v
						.findViewById(R.id.list_item_entry_event);
				final TextView date = (TextView) v
						.findViewById(R.id.list_item_entry_date);
				final TextView day = (TextView) v
						.findViewById(R.id.list_item_entry_day);

				if (event != null)
					event.setText(ei.getEvent());
				if (date != null)
					date.setText(ei.getStartingDay().toString());
				if (day != null)
					day.setText(ei.getDay());
			}
		}
		return v;
	}
}
