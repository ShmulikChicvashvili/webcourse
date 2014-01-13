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
import com.technion.coolie.techlibrary.BookItems.LibraryElement;

public class WishListFragment extends SherlockFragment {
	private ListView mListView;
	private TextView mEmptyView;
//	private static ArrayList<LibraryElement> mWishList = new ArrayList<BookItems.LibraryElement>();
	private WishListAdapter mWishAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// Inflate the layout for this fragment
				View v = inflater.inflate(R.layout.lib_wish_list, container, false);
				mListView = (ListView) v.findViewById(R.id.list);
				mEmptyView = (TextView) v.findViewById(R.id.empty);
				mListView.setEmptyView(mEmptyView);
		
		
//		mWishList = new ArrayList<BookItems.LibraryElement>();  ////////  TODO the list is empty right now!!!!!
		//mWishList.add(get.getHoldsList().get(0));
		mListView.setAdapter(new WishListAdapter(getActivity(), new ArrayList<LibraryElement>()));
//		Log.d("LoansFrg:", "adapter set, number of items is:"
//				+ ((Integer) mWishList.size()).toString());
		mWishAdapter = (WishListAdapter) mListView.getAdapter();
		
		return v;
	}
	
	public void addItem(LibraryElement libElement){
		Log.d("add item to wishList", "the item is " + libElement.name);
		ArrayList<LibraryElement> mWishList = new ArrayList<BookItems.LibraryElement>();
		mWishList.addAll(mWishAdapter.wishItems);
		mWishList.add(libElement);
		mWishAdapter.wishItems.clear();
		mWishAdapter.wishItems.addAll(mWishList);
		mWishAdapter.notifyDataSetChanged();
	}
}

