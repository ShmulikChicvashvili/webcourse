package com.technion.coolie.techlibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.OpenHoursScreens.LibraryDescriptionFragment;

public class OpenHoursFragment extends SherlockFragment {
	private ArrayList<String> mOpenHours;
	private String lastItemClicked = null;
	private Integer lastOrientation = null;
	private FrameLayout mDetailsFrame = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		LibrariesData.buildList(getSherlockActivity(),R.raw.libraries);
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.lib_fragment_open_hours, container,
				false);	
		mDetailsFrame  = (FrameLayout)v.findViewById(R.id.lib_library_details_frame);
		
		OpenHoursScreens.LibrariesListFragment libListFrag = new OpenHoursScreens.LibrariesListFragment();
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction trans = fm.beginTransaction();
		trans.add(R.id.lib_libraries_list_frame, libListFrag);
		trans.commit();
		/*Nastia tried to keep the last choice of the user in the new orientition*/
//		if(lastOrientation != null && lastOrientation != getResources().getConfiguration().orientation){
//			notifyClickedItem(lastItemClicked);
//		}
//		lastOrientation = getResources().getConfiguration().orientation;
		return v;
	}

	public void notifyClickedItem(String name) {
		lastItemClicked = name; //not neccesery..
		if (mDetailsFrame != null) {
			LibraryDescriptionFragment libDesc = new LibraryDescriptionFragment();
			libDesc.setArguments(name);
			FragmentManager f = getChildFragmentManager();
			f.beginTransaction()
					.replace(R.id.lib_library_details_frame, libDesc).commit();
		} else {
			Intent intent = new Intent(getSherlockActivity(),
					OpenHoursScreens.LibraryDescriptionActivity.class);
			intent.putExtra("name", name);
			startActivity(intent);
		}
	}
}
