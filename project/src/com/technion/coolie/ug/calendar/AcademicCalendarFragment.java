package com.technion.coolie.ug.calendar;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.R;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.Item;

public class AcademicCalendarFragment extends Fragment {
	private ArrayList<Item> items = new ArrayList<Item>();
	private ListView listview = null;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.ug_calendar_fragment,
				container, false);
		items = UGDatabase.getInstance(getActivity()).getCalendar();
		listview = (ListView) view.findViewById(R.id.academic_list);
		final AcademicCalendarFragmentAdapter adapter = new AcademicCalendarFragmentAdapter(
				getActivity(), items);
		listview.setAdapter(adapter);
		return view;
	}
}
