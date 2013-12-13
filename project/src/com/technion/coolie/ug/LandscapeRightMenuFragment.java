package com.technion.coolie.ug;



import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LandscapeRightMenuFragment extends ListFragment {

	String[] menuItems = new String[] { 
			"׳’׳�׳™׳•׳� ׳¦׳™׳•׳ ׳™׳�",
			"׳§׳•׳¨׳¡׳™׳� ׳•׳�׳‘׳—׳ ׳™׳�",
			"׳�׳•׳— ׳©׳ ׳” ׳�׳§׳“׳�׳™",
			"׳×׳©׳�׳•׳�׳™׳�",
			"׳�׳¢׳§׳‘ ׳§׳•׳¨׳¡׳™׳�",
			"׳—׳™׳₪׳•׳© ׳§׳•׳¨׳¡׳™׳�",
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//final ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, menuItems);
		final LandscapeRightMenuAdapter adapter = new LandscapeRightMenuAdapter(inflater.getContext(),menuItems);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	OnRightMenuItemSelected _clickListener;
	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    try {
	    	_clickListener = (OnRightMenuItemSelected) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString() + " must implement onViewSelected");
	    }
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Log.i("FragmentList", "Item clicked: " + id);
		_clickListener.onLeftMenuItemSelected(LandscapeLeftMenuItems.values()[position]);
    }
	
	//
	
}

