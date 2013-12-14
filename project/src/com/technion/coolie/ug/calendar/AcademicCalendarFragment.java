package com.technion.coolie.ug.calendar;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

import com.technion.coolie.R;
import com.technion.coolie.ug.gradessheet.EntryAdapter;
import com.technion.coolie.ug.gradessheet.Item;
import com.technion.coolie.ug.gradessheet.GradesSheetFragment.parseGradesAsync;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
		
		listview = (ListView) getActivity().findViewById(R.id.academic_list);
		AcademicCalendarFragmentAdapter adapter = new AcademicCalendarFragmentAdapter(
				getActivity(), items);
		listview.setAdapter(adapter);

		return view;
	}
}
