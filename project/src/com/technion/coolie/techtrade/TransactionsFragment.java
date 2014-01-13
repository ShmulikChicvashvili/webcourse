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
	protected TransactionsFragmentAdapter adapter;
	
	public TransactionsFragment(Vector<Product> productVector) {
		this.productVector = productVector;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.get_transactions_fragment, container, false);
		
		this.adapter = this.getGridAdapter();
		GridView gridView = (GridView) rootView.findViewById(R.id.get_transactions_grid_view);
		gridView.setEmptyView(rootView.findViewById(R.id.get_transactions_grid_view_empty));
		gridView.setAdapter(this.adapter);
	    gridView.setOnItemClickListener(this.getGridOnItemClickListener());
	    
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	protected void showDialogFragment(Product product){
		FragmentManager fm = getActivity().getSupportFragmentManager();
		TransactionsDialogFragment mySalesDialog = this.getDialogFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("product", product);
		mySalesDialog.setArguments(args);
        mySalesDialog.show(fm, this.getDialogName());
	}
	
	protected abstract TransactionsFragmentAdapter getGridAdapter();
	protected abstract OnItemClickListener getGridOnItemClickListener();
	protected abstract TransactionsDialogFragment getDialogFragment();
	protected abstract String getDialogName();
	
}
