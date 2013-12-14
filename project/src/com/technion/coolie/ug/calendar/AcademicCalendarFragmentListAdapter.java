package com.technion.coolie.ug.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class AcademicCalendarFragmentListAdapter extends BaseAdapter
{
		
	private final Context context;
	private final List<AcademicCalendarEvent>  values = new ArrayList<AcademicCalendarEvent>();
	
	public AcademicCalendarFragmentListAdapter(Context context, List<AcademicCalendarEvent> list)
	{
		this.context = context;
		values.addAll(list);
		Collections.sort(values);
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
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ug_list_item_academic_calendar_fragment, parent, false);
		}
		AcademicCalendarEvent event = (AcademicCalendarEvent) getItem(position);
		
		TextView eventTextView = (TextView)convertView.findViewById(R.id.ug_academic_calendar_fragment_event);
		eventTextView.setText(event.getEvent());
		
		//exam.date.get(Calendar.DAY_OF_MONTH)
		String d = event.getStartingDay().get(Calendar.DAY_OF_MONTH)+"/" + event.getStartingDay().get(Calendar.MONTH) + "/" + event.getStartingDay().get(Calendar.YEAR);
		TextView dateTextView = (TextView)convertView.findViewById(R.id.ug_academic_calendar_fragment_date);
		dateTextView.setText(d);
		
	    return convertView;
	}
	

	}
