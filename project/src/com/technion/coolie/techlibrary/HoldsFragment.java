package com.technion.coolie.techlibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;

public class HoldsFragment extends SherlockFragment {
	private ListView mListView;
	private TextView mEmptyView;

	private LibraryCardActivity mActivity;
	private ArrayList<HoldElement> mHoldsList;
	private HoldsAdapter mHoldsAdapter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (LibraryCardActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.lib_holds_list, container, false);
		mListView = (ListView) v.findViewById(R.id.list);
		mEmptyView = (TextView) v.findViewById(R.id.empty);
		mListView.setEmptyView(mEmptyView);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHoldsList = mActivity.getHoldsList();
		mListView.setAdapter(new HoldsAdapter(getActivity(), mHoldsList));
		Log.d("LoansFrg:", "adapter set, number of items is:"
				+ ((Integer) mHoldsList.size()).toString());
		mHoldsAdapter = (HoldsAdapter) mListView.getAdapter();
	}

	public void notifyListChange(ArrayList<HoldElement> newList) {
		if (mHoldsAdapter == null) {
			Log.e("ERRRRRRROR ADAPTER:", "WTF");
		}
		mHoldsList.clear();
		mHoldsList.addAll(newList);
		mHoldsAdapter.notifyDataSetChanged();
	}

}
