package com.technion.coolie.techtrade;

import java.util.Vector;

import com.technion.coolie.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class TransactionsFragment extends Fragment {
	protected LayoutInflater inflater;
	protected ViewGroup container;
	protected Vector<Product> productVector;
	protected RelativeLayout rootView;
	protected RelativeLayout emptyView;
	protected TransactionsFragmentAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		this.productVector = new Vector<Product>();
		
		this.initialize();
		
		emptyView = (RelativeLayout) inflater.inflate(R.layout.get_message_screen, container, false);
		((TextView)emptyView.findViewById(R.id.get_message_text)).setText("No items to show");
		rootView = (RelativeLayout) inflater.inflate(R.layout.get_transactions_fragment, container, false);
		
		GridView gridView = (GridView) rootView.findViewById(R.id.get_transactions_grid_view);
		this.adapter = this.getGridAdapter();
		gridView.setAdapter(adapter);
	    gridView.setOnItemClickListener(this.getGridOnItemClickListener());
	    gridView.setEmptyView(emptyView);
	    
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if(NetworkOperations.isNetworkAvailable(container.getContext()) == false) {
			Toast.makeText(container.getContext(), "connectivity problem.\r\ncheck internet connection and try again", Toast.LENGTH_LONG).show();
			return;
		}
		if(UserOperations.isUserConnected() == false){
			Toast.makeText(container.getContext(), "Please login and try again.", Toast.LENGTH_LONG).show();
			return;
		}
	}
	protected void showDialogFragment(Product product){
		FragmentManager fm = getActivity().getSupportFragmentManager();
		TransactionsDialogFragment mySalesDialog = this.getDialogFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("product", product);
		mySalesDialog.setArguments(args);
        mySalesDialog.show(fm, this.getDialogName());
	}
	


	protected abstract void initialize();
	protected abstract TransactionsFragmentAdapter getGridAdapter();
	protected abstract OnItemClickListener getGridOnItemClickListener();
	protected abstract TransactionsDialogFragment getDialogFragment();
	protected abstract String getDialogName();
	
}
