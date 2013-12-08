package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.List;

import com.technion.coolie.ug.model.AccomplishedCourse;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GradesSheetListFragment extends ListFragment {

	List<AccomplishedCourse> coursesList = new ArrayList<AccomplishedCourse>() {
		{

			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "גנטיקה", "3.5", null, "98"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
			add(new AccomplishedCourse("234123", "מערכות הפעלה", "4.5", null,
					"95"));
			add(new AccomplishedCourse("234123", "אנליזה נומרית", "4.0", null,
					"85"));
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final GradesSheetFragmentListAdapter adapter = new GradesSheetFragmentListAdapter(
				inflater.getContext(), coursesList);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
}