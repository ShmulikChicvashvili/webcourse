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

import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.AccomplishedCourse;

public class GradesSheetListFragment extends ListFragment {

	List<AccomplishedCourse> coursesList = new ArrayList<AccomplishedCourse>();

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final List<AccomplishedCourse> tempList = UGDatabase.getInstance(getActivity())
				.getGradesSheet();
		for (final AccomplishedCourse i : tempList)
			if (i instanceof AccomplishedCourse)
				coursesList.add((AccomplishedCourse) i);
		updateData();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void updateData()
	{
		final GradesSheetFragmentListAdapter adapter = new GradesSheetFragmentListAdapter(getActivity(),coursesList);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		final Intent intent = new Intent(getActivity(),
				TransparentActivity.class);
		final Bundle b = new Bundle();
		b.putString("key", GradesSheetFragment.class.toString());
		intent.putExtras(b);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}