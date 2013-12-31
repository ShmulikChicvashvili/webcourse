package com.technion.coolie.ug.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarFragmentAdapter extends ArrayAdapter<AcademicCalendarEvent> {

	private final List<AcademicCalendarEvent> items;
	private final LayoutInflater vi;

	public AcademicCalendarFragmentAdapter(final Context context,
			final List<AcademicCalendarEvent> items) {
		super(context, 0, items);
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = convertView;

		final AcademicCalendarEvent ace = items.get(position);
		if (ace != null)
			if (ace.getMonth() != null) {
				v = vi.inflate(R.layout.ug_calendar_list_item_section, null);
				final TextView sectionView = (TextView) v
						.findViewById(R.id.list_item_section_text);
				sectionView.setText(ace.getMonth());
				sectionView.setBackgroundColor(Color.parseColor("#0099b3"));
				sectionView.setTextColor(Color.parseColor("#FFCC00"));

			} else {
				v = vi.inflate(R.layout.ug_calendar_list_item_entry, null);
				final TextView event = (TextView) v
						.findViewById(R.id.list_item_entry_event);
				final TextView date = (TextView) v
						.findViewById(R.id.list_item_entry_date);
				final TextView day = (TextView) v
						.findViewById(R.id.list_item_entry_day);

				if (event != null)
					event.setText(ace.getEvent());
				if (date != null) {
					final SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy", Locale.getDefault());
					final Calendar cal = ace.getStartingDay();
					date.setText(formatter.format(cal.getTime()));
				}
				if (day != null)
					day.setText(ace.getDay());
			}
		return v;
	}
}
