package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.ug.HtmlParser;
import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetListFragment extends ListFragment {

	List<AccomplishedCourse> coursesList = new ArrayList<AccomplishedCourse>();
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		List<Item> tempList =UGDatabase.INSTANCE.getGradesSheet();
		for (Item i : tempList)
		{
			if (i instanceof AccomplishedCourse)
				coursesList.add((AccomplishedCourse)i);
				
		}
		final GradesSheetFragmentListAdapter adapter = new GradesSheetFragmentListAdapter(
				inflater.getContext(), coursesList);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), TransparentActivity.class);
		Bundle b = new Bundle();
		b.putString("key", "gradesSheetLayout");
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}