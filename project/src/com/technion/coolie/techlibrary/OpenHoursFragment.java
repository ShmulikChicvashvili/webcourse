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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.OpenHoursScreens.LibraryDescriptionFragment;
import com.technion.coolie.techlibrary.maps.LibraryMapLocationActivity;

public class OpenHoursFragment extends SherlockFragment {
	private static final int NOT_ACTIVE = -1;
	private ArrayList<String> mOpenHours;
	private String lastItemClicked = null;
	private Integer lastOrientation = null;
	private FrameLayout mDetailsFrame = null;
	private boolean fragLibraryDescriptionFlag = false;
	private int viewedLibraryId = NOT_ACTIVE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setHasOptionsMenu(true);
		LibrariesData.buildList(getSherlockActivity(),R.raw.lib_libraries);
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu, inflater);
		
		//only if dualpane and item is clicked
		if(fragLibraryDescriptionFlag) {
			// TODO: change order of menu items!
			// ~~~~~~~ MapLocation ~~~~~~~
			MenuItem map = (MenuItem) menu.add("Map");
			map.setIcon(R.drawable.lib_ic_action_map);
			map.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			map.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent intent = new Intent(getSherlockActivity(),
												LibraryMapLocationActivity.class);
					//TODO: handle\add library code to make this function
					intent.putExtra("libraryId", viewedLibraryId); 
					startActivity(intent);
					return true;
				}
			});
		}
	}

	public void notifyClickedItem(int libraryId) {
		if (mDetailsFrame != null) {
			//dual pane!
			LibraryDescriptionFragment libDesc = new LibraryDescriptionFragment();
			libDesc.setArguments(libraryId);
			FragmentManager f = getChildFragmentManager();
			f.beginTransaction()
					.replace(R.id.lib_library_details_frame, libDesc).commit();
			//adding menu items?
			fragLibraryDescriptionFlag = true;
			viewedLibraryId = libraryId;
			getSherlockActivity().invalidateOptionsMenu();
		} else {
			Intent intent = new Intent(getSherlockActivity(),
					OpenHoursScreens.LibraryDescriptionActivity.class);
			intent.putExtra("libraryId", libraryId);
			startActivity(intent);
		}
	}
}
