/**
 * 
 */
package com.technion.coolie.teletech;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

/**
 * @author Argaman
 * 
 */
public class ContactSummaryFragment extends SherlockListFragment {

	private List<ContactInformation> contacts;

	OnContactSelectedListener mCallback;

	public interface OnContactSelectedListener {
		public void onContactSelected(int position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockListFragment#onAttach(android.app.Activity
	 * )
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnContactSelectedListener) activity;
		} catch (Exception e) {
			Log.d("Interface not implemented", e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int layout = com.technion.coolie.R.layout.contacts_list;
		contacts = TeletechManager.getContacts();
		setListAdapter(new ContactsAdapter(getSherlockActivity(), layout,
				contacts));
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
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		mCallback.onContactSelected(position);
		getListView().setItemChecked(position, true);
	}

}
