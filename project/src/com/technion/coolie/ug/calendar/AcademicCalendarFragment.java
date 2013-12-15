package com.technion.coolie.ug.calendar;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gradessheet.Item;

public class AcademicCalendarFragment extends Fragment {
	ArrayList<Item> items = new ArrayList<Item>();
	ListView listview = null;
	Document doc;
	TextView avg, success, points;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ug_calendar_fragment, container,
				false);
		items = UGDatabase.INSTANCE.getCalendar();
		listview = (ListView) view.findViewById(R.id.academic_list);
		AcademicCalendarFragmentAdapter adapter = new AcademicCalendarFragmentAdapter(
				getActivity(), items);
		listview.setAdapter(adapter);

		return view;
	}
}
