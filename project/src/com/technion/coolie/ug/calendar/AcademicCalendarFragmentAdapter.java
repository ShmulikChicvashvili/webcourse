package com.technion.coolie.ug.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.gradessheet.SectionedListItem;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarFragmentAdapter extends ArrayAdapter<SectionedListItem> {

	private final ArrayList<SectionedListItem> items;
	private final LayoutInflater vi;

	public AcademicCalendarFragmentAdapter(final Context context,
			final ArrayList<SectionedListItem> items) {
		super(context, 0, items);
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = convertView;

		final SectionedListItem i = items.get(position);
		if (i != null)
			if (i.isSection()) {
				final CalendarSectionItem si = (CalendarSectionItem) i;
				v = vi.inflate(R.layout.ug_calendar_list_item_section, null);
				final TextView sectionView = (TextView) v
						.findViewById(R.id.list_item_section_text);
				sectionView.setText(si.getMonth());
				sectionView.setBackgroundColor(Color.parseColor("#0099b3"));
				sectionView.setTextColor(Color.parseColor("#FFCC00"));

			} else {
				final AcademicCalendarEvent ei = (AcademicCalendarEvent) i;
				v = vi.inflate(R.layout.ug_calendar_list_item_entry, null);
				final TextView event = (TextView) v
						.findViewById(R.id.list_item_entry_event);
				final TextView date = (TextView) v
						.findViewById(R.id.list_item_entry_date);
				final TextView day = (TextView) v
						.findViewById(R.id.list_item_entry_day);

				if (event != null)
					event.setText(ei.getEvent());
				if (date != null) {
					final SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy", Locale.getDefault());
					final Calendar cal = ei.getStartingDay();
					date.setText(formatter.format(cal.getTime()));
				}
				if (day != null)
					day.setText(ei.getDay());
			}
		return v;
	}
}
