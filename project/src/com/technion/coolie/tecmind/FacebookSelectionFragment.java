package com.technion.coolie.tecmind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;

public class FacebookSelectionFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.techmind_activity_facebook_selection, 
	            container, false);
	    return view;
	}
}
