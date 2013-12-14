/**
 * 
 */
package com.technion.coolie.teletech;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.technion.coolie.teletech.serverApi.ITeletechAPI;
import com.technion.coolie.teletech.serverApi.TeletechAPI;

/**
 * @author Argaman
 * 
 */
public class ContactSummaryFragment extends SherlockListFragment {

	// List<ContactInformation> contacts;

	OnContactSelectedListener mCallback;
	// TODO : remove this!!
	public static ContactsAdapter adapter;

	public interface OnContactSelectedListener {
		public void onContactSelected(int position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final int layout = com.technion.coolie.R.layout.teletech_contact_list;
		MainActivity.contacts = new ContactsTest().contactList;

		// new ClientAsyncContacts() {
		// @Override
		// protected void onPostExecute(final String result) {

		adapter = new ContactsAdapter(getSherlockActivity(), layout,
				MainActivity.contacts);
		setListAdapter(adapter);
		// }

		// }.execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockListFragment#onAttach(android.app.Activity
	 * )
	 */
	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnContactSelectedListener) activity;
		} catch (final Exception e) {
			Log.d("Interface not implemented", e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		if (getFragmentManager().findFragmentById(
				com.technion.coolie.R.id.summary_fragment) != null)
			getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView
	 * , android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		mCallback.onContactSelected(position);
		Log.d("item clicked", "the item that was clicked is: " + position);
		getListView().setItemChecked(position, true);
		ContactsAdapter.indexSelected = position;
	}

	private class ClientAsyncContacts extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(final Void... params) {
			final ITeletechAPI teletechAPI = new TeletechAPI();

			MainActivity.contacts = teletechAPI.getAllContacts();
			return null;
		}

	}

}
