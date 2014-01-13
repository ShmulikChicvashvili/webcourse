package com.technion.coolie.ug.calendar;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarListFragment extends ListFragment {

		
	@Override
	public View onCreateView(final LayoutInflater inflater,	final ViewGroup container, final Bundle savedInstanceState) 
	{
		updateData();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	 
	public void updateData()
	{
		AcademicCalendarFragmentListAdapter adapter = new AcademicCalendarFragmentListAdapter(getActivity(), UGDatabase.getInstance(getActivity()).getCalendar());
		setListAdapter(adapter);
	}
	

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		final Intent intent = new Intent(getActivity(),
				TransparentActivity.class);
		final Bundle b = new Bundle();
		b.putString("key", AcademicCalendarFragment.class.toString());
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

}