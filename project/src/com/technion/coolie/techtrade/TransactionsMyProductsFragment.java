package com.technion.coolie.techtrade;

import java.util.List;
import java.util.Vector;

import com.technion.coolie.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class TransactionsMyProductsFragment extends TransactionsFragment {
	
	public TransactionsMyProductsFragment(Vector<Product> myProductsVector) {
		super(myProductsVector);
	}

	@Override
	protected TransactionsFragmentAdapter getGridAdapter() {
		return new TransactionsFragmentProductAdapter(getActivity(), this.productVector, R.layout.get_transactions_grid_item);
	}

	@Override
	protected OnItemClickListener getGridOnItemClickListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> gridView, View view,int pos, long id) {
				showDialogFragment(productVector.elementAt(pos));
			}
		};
	}

	@Override
	protected TransactionsDialogFragment getDialogFragment() {
		return new TransactionsMyProductsDialogFragment();
	}

	@Override
	protected String getDialogName() {
		return "my_products_dialog_fragment";
	}
}
