package com.technion.coolie.ug.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarFragmentListAdapter extends BaseAdapter {

	private final Context context;
	private final List<AcademicCalendarEvent> values = new ArrayList<AcademicCalendarEvent>();

	public AcademicCalendarFragmentListAdapter(final Context context,
			final List<AcademicCalendarEvent> list) {
		this.context = context;
		values.addAll(list);
		Collections.sort(values);
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
					R.layout.ug_list_item_academic_calendar_fragment, parent,
					false);
		}
		final AcademicCalendarEvent event = (AcademicCalendarEvent) getItem(position);

		final TextView eventTextView = (TextView) convertView
				.findViewById(R.id.ug_academic_calendar_fragment_event);
		eventTextView.setText(event.getEvent());

		final TextView dateTextView = (TextView) convertView
				.findViewById(R.id.ug_academic_calendar_fragment_date);
		final SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MM/yyyy", Locale.getDefault());
		final Calendar cal = event.getStartingDay();
		if (cal==null)
		{
			dateTextView.setText("null");
		}
		else
		{
			dateTextView.setText(formatter.format(cal.getTime()));
		}

		return convertView;
	}

}
