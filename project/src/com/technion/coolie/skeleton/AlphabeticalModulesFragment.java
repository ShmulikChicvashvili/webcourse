package com.technion.coolie.skeleton;

import com.technion.coolie.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class AlphabeticalModulesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.skel_alphabetical, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.skel_alphabetical_grid);
		gridview.setAdapter(new AlphabeticalModulesAdapter(getActivity()));

		return view;
	}
	
}
