package com.technion.coolie.techlibrary;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * This class includes in it the components of the OpenHours fragment,
 * which contain the : LibraryDescriptionFragment, LibrariesListFragment and LibraryDescriptionActivity
 *
 */
public class OpenHoursScreens {

	/*
	 * LibraryDescriptionFragment
	 */
	static public class LibraryDescriptionFragment extends SherlockFragment {

		String name = null;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			
			LibrariesData.Library libDetails = LibrariesData
					.getLibraryByName(name);
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

		public void setArguments(String name) {
			this.name = name;
		}

	}

	
	
	/*
	 * LibrariesListFragment
	 */
	static public class LibrariesListFragment extends SherlockFragment {
		private ListView mListView;
		private TextView mEmptyView;
		private ArrayList<String> mOpenHours;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			mOpenHours = new ArrayList<String>();
			mOpenHours.addAll(LibrariesData.names);

			// Inflate the layout for this fragment
			View v = inflater.inflate(R.layout.lib_open_hours_list, container,
					false);
			mListView = (ListView) v.findViewById(R.id.list);
			mEmptyView = (TextView) v.findViewById(R.id.empty);
			mListView.setEmptyView(mEmptyView);

			mListView.setAdapter(new OpenHoursAdapter(getActivity(),
					mOpenHours, this));
			Log.d("LoansFrg:", "adapter set, number of items is:"
					+ ((Integer) mOpenHours.size()).toString());

			return v;
		}

		public void notifyClickedItem(String libraryName) {
			Log.d(" NOTIFICATION : you pressed library : " ,"" + libraryName);
			if (getParentFragment() != null) {
				((OpenHoursFragment) getParentFragment())
						.notifyClickedItem(libraryName);
			}
		}
	}

	
	/*
	 * LibraryDescriptionActivity
	 */
	static public class LibraryDescriptionActivity extends CoolieActivity {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			 if (getResources().getConfiguration().orientation ==
			 Configuration.ORIENTATION_LANDSCAPE) {
			 // If the screen is now in landscape mode, we can show the
			 // dialog in-line with the list so we don't need this activity.
			 finish();
			 return;
			 }
			setContentView(R.layout.lib_activity_library_description);
			String lib_Name = getIntent().getExtras().getString("name");
			LibrariesData.Library tmp = LibrariesData
					.getLibraryByName(lib_Name);
			if (tmp == null) {
				((TextView) (findViewById(R.id.lib_name)))
						.setText("No description available");

			} else {
				((TextView) (findViewById(R.id.lib_name))).setText(tmp.name);
				((TextView) (findViewById(R.id.lib_head_librarien_name)))
						.setText(tmp.headLibrarian);
				((TextView) (findViewById(R.id.lib_phone_number)))
						.setText(tmp.phone);
				((TextView) (findViewById(R.id.lib_email_data)))
						.setText(tmp.email);
			}

		}

	}
}