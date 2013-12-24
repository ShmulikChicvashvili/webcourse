package com.technion.coolie.ug.gui.searchCourses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.utils.NavigationUtils;

public class SearchBarFragment extends SearchFragment {
	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = inflater.inflate(R.layout.ug_search_screen_fragment,
				container, false);

		return view;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// clear the list and the text for showing only the search bar

		final ListView listCourses = (ListView) getActivity().findViewById(
				R.id.search_list_view);
		LayoutParams lp = listCourses.getLayoutParams();
		lp.height = 0;
		listCourses.setLayoutParams(lp);

		final TextView emptyResults = (TextView) getActivity().findViewById(
				R.id.empty_results);
		lp = emptyResults.getLayoutParams();
		lp.height = 0;
		emptyResults.setLayoutParams(lp);

	}

	@Override
	protected int onSearchPressed(final String query) {
		final int results = super.onSearchPressed(query);
		if (results != 1)
			NavigationUtils.goToSearchDisplay(query, filters, getActivity());
		return results;
	}

	@Override
	protected void onFiltersUpdate() {
		final AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.autocompletetextview);
		searchQueryAndUpdate(autocompletetextview.getText().toString());
	}

}
