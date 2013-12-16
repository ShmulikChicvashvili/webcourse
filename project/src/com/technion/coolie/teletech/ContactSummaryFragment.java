package com.technion.coolie.teletech;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class ContactSummaryFragment extends SherlockListFragment {

	// List<ContactInformation> contacts;

	OnContactSelectedListener mCallback;
	// TODO : remove this!!
	public static ContactsAdapter adapter;

	DBTools db;

	public interface OnContactSelectedListener {
		public void onContactSelected(int position);
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DBTools(getActivity());
		final int layout = com.technion.coolie.R.layout.teletech_contact_list;

		adapter = new ContactsAdapter(getSherlockActivity(), layout,
				MainActivity.contacts);
		setListAdapter(adapter);

	}

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnContactSelectedListener) activity;
		} catch (final Exception e) {
			Log.d("Interface not implemented", e.toString());
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getFragmentManager().findFragmentById(
				com.technion.coolie.R.id.summary_fragment) != null)
			getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		mCallback.onContactSelected(position);
		Log.d("item clicked", "the item that was clicked is: " + position);
		getListView().setItemChecked(position, true);
		ContactsAdapter.indexSelected = position;
	}

	// public static void refreshList(List<ContactInformation> newList){
	// MainActivity.contacts = new FavoriteTest().favoriteList;
	// ContactSummaryFragment.adapter.notifyDataSetChanged();
	// }

}
