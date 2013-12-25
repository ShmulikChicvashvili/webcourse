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
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.model.AcademicCalendarEvent;

public class AcademicCalendarListFragment extends ListFragment {

	List<AcademicCalendarEvent> coursesList = new ArrayList<AcademicCalendarEvent>();

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final List<Item> tempList = UGDatabase.getInstance(getActivity())
				.getCalendar();
		for (final Item i : tempList)
			if (i instanceof AcademicCalendarEvent)
				coursesList.add((AcademicCalendarEvent) i);
		final AcademicCalendarFragmentListAdapter adapter = new AcademicCalendarFragmentListAdapter(
				inflater.getContext(), coursesList);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);

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