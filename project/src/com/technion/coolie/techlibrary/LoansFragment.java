package com.technion.coolie.techlibrary;

import java.io.IOException;
import java.util.ArrayList;

import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.LoanElement;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class LoansFragment extends Fragment {
	private ListView mListView;
	private TextView mEmptyView;
	
	private LibraryCardActivity mActivity; //change to callback somelie?? like in exampleFragment?
	private ArrayList<LoanElement> mLoansList;
	private LoansAdapter mLoansAdapter;

	
	
	/*******
	public interface Callback {
		public void onItemClicked(String itemName);
	}
	
	********/
	
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
		View v = inflater.inflate(R.layout.lib_loans_list, container, false);
		mListView = (ListView) v.findViewById(R.id.list);
		mEmptyView = (TextView) v.findViewById(R.id.empty);
		mListView.setEmptyView(mEmptyView);
		
		/******** onclick
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index,
					long id) {
				//TextView tv = (TextView)view.findViewById(R.id.country);
				mListener.onItemClicked((String)((Countries)parent.getAdapter()).getItem(index));
			}
		});
		************/
		return v;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
		mLoansList = mActivity.getLoansList();
		
		mListView.setAdapter(new LoansAdapter(getActivity(), mLoansList));
		Log.d("LoansFrg:","adapter set, number of items is:"+((Integer)mLoansList.size()).toString());
		mLoansAdapter = (LoansAdapter) mListView.getAdapter();
	}
	
	public void notifyListChange(ArrayList<LoanElement> newList) {
		if(mLoansAdapter == null) {
			Log.e("ERRRRRRROR ADAPTER:","WTF");
		}
		
		Log.d("LoansFrg:","notifying change of list and number of items in list is now:"+((Integer)mLoansList.size()).toString()+","+((Integer)newList.size()).toString());
		//WTF??? not same object? delete me!
		mLoansList.clear();
		mLoansList.addAll(newList);
		mLoansAdapter.notifyDataSetChanged();
	}
	
}







