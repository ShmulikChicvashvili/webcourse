package com.technion.coolie.techlibrary;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.LibrariesData.Library;
import com.technion.coolie.techlibrary.maps.LibraryMapLocationActivity;
/**
 * 
 * This class includes in it the components of the OpenHours fragment,
 * which contain the : LibraryDescriptionFragment, LibrariesListFragment and LibraryDescriptionActivity
 *
 */
public class OpenHoursScreens {
	
	//TODO: change these (delete here and add same to library data?)
	

	/*
	 * LibraryDescriptionFragment
	 */
	static public class LibraryDescriptionFragment extends SherlockFragment {

		int libraryId;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			
			LibrariesData.Library libDetails = LibrariesData
					.getLibraryById(libraryId);
			// Inflate the layout for this fragment
			View v = inflater
					.inflate(R.layout.lib_activity_library_description,
							container, false);

			if (libDetails == null) {
				((TextView) (v.findViewById(R.id.lib_name)))
						.setText("No description available");

			} else {
				((TextView) (v.findViewById(R.id.lib_name)))
						.setText(libDetails.name);
				((TextView) (v.findViewById(R.id.lib_head_librarien_name)))
						.setText(libDetails.headLibrarian);
				((TextView) (v.findViewById(R.id.lib_phone_number)))
						.setText(libDetails.phone);
				((TextView) (v.findViewById(R.id.lib_email_data)))
						.setText(libDetails.email);
			}
			return v;
		}

		public void setArguments(int libraryId) {
			this.libraryId = libraryId;
		}

	}

	
	
	/*
	 * LibrariesListFragment
	 */
	static public class LibrariesListFragment extends SherlockFragment {
		private ListView mListView;
		private TextView mEmptyView;
		private ArrayList<Library> libraryList;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			
			libraryList = new ArrayList<Library>();
			libraryList.addAll(LibrariesData.getLibrariesList());

			// Inflate the layout for this fragment
			View v = inflater.inflate(R.layout.lib_open_hours_list, container,
					false);
			mListView = (ListView) v.findViewById(R.id.list);
			mEmptyView = (TextView) v.findViewById(R.id.empty);
			mListView.setEmptyView(mEmptyView);

			mListView.setAdapter(new OpenHoursAdapter(getSherlockActivity(),
					libraryList, this));
			Log.d("LoansFrg:", "adapter set, number of items is:"
					+ ((Integer) libraryList.size()).toString());

			return v;
		}

		public void notifyClickedLibrary(int libraryId) {
			Log.d(" NOTIFICATION : you pressed library : " ,"" + libraryId);
			if (getParentFragment() != null) {
				((OpenHoursFragment) getParentFragment())
						.notifyClickedItem(libraryId);
			}
		}
	}

	
	/*
	 * LibraryDescriptionActivity
	 */
	static public class LibraryDescriptionActivity extends CoolieActivity {
		int libraryId;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.lib_activity_library_description);
			libraryId = getIntent().getIntExtra("libraryId", -1);
			LibrariesData.Library tmp = LibrariesData
					.getLibraryById(libraryId);
			if (tmp == null) {
				((TextView) (findViewById(R.id.lib_name)))
						.setText("No description available");

			} else {
				Log.d("open hours",tmp.headLibrarian +"\n"+tmp.phone+"\n"+tmp.email+"\n");
				((TextView) (findViewById(R.id.lib_name))).setText(tmp.name);
				((TextView) (findViewById(R.id.lib_head_librarien_name)))
						.setText(tmp.headLibrarian);
				((TextView) (findViewById(R.id.lib_phone_number)))
						.setText(tmp.phone);
				((TextView) (findViewById(R.id.lib_email_data)))
						.setText(tmp.email);
			}
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			super.onCreateOptionsMenu(menu);

			// TODO: change order of menu items!
			// ~~~~~~~ MapLocation ~~~~~~~
			MenuItem map = (MenuItem) menu.add("Map");
			map.setIcon(R.drawable.lib_ic_action_map);
			map.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			map.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent intent = new Intent(OpenHoursScreens.LibraryDescriptionActivity.this,
												LibraryMapLocationActivity.class);
					//TODO: handle\add library code to make this function
					intent.putExtra("libraryId",libraryId); 
					startActivity(intent);
					return true;
				}
			});
			return true;
		}
	}
}