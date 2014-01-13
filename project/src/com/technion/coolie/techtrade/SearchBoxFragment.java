package com.technion.coolie.techtrade;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.technion.coolie.R;

public class SearchBoxFragment extends Fragment{

	private EditText searchBox;
	public SearchBoxCallback listener;

	public interface SearchBoxCallback{
		public void searchWasActivated(String searchTerm);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.get_search_box_fragment,
				container, false);

		setRetainInstance(true);

		//saving the searchBox
		searchBox = (EditText) view.findViewById(R.id.get_search_box_fragment_search_field);
		searchBox.setOnEditorActionListener(
				new TextView.OnEditorActionListener(){

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event){
						listener.searchWasActivated(v.getText().toString());
						return false;
					}
				});

		Button searchButton = (Button) view.findViewById(R.id.get_search_box_fragment_search_Button);
		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				listener.searchWasActivated(searchBox.getText().toString());
			}
		});

		return view;
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if (activity instanceof SearchBoxCallback) {
			listener = (SearchBoxCallback) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet SearchBoxFragment.SearchBoxCallback");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
}
