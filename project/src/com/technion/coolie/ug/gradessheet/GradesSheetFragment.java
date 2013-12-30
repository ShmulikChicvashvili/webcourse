package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetFragment extends Fragment {
	List<AccomplishedCourse> items = new ArrayList<AccomplishedCourse>();
	ListView listview = null;
	Document doc;
	TextView avg, success, points;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ug_grades_fragment, container,
				false);
		avg = (TextView) view.findViewById(R.id.average_value);
		success = (TextView) view.findViewById(R.id.success_percentage_value);
		points = (TextView) view.findViewById(R.id.accumulated_points_value);

		// retrieves document with html content
		// new parseGradesAsync(getActivity()).execute();
		items = UGDatabase.getInstance(getActivity()).getGradesSheet();

		avg.setText(HtmlParser.avg);
		success.setText(HtmlParser.success);
		points.setText(HtmlParser.points);

		listview = (ListView) view.findViewById(R.id.listView_main);
		GradesSheetFragmentAdapter adapter = new GradesSheetFragmentAdapter(
				getActivity(), items);
		listview.setAdapter(adapter);
		return view;
	}

}
