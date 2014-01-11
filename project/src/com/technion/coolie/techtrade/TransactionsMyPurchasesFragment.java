package com.technion.coolie.techtrade;

import java.util.Vector;

import com.technion.coolie.R;

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

public class TransactionsMyPurchasesFragment extends TransactionsFragment {
	
	public TransactionsMyPurchasesFragment(Vector<Product> myPurchasesVector) {
		super(myPurchasesVector);
	}

	@Override
	protected TransactionsFragmentAdapter getGridAdapter() {
		return new TransactionsFragmentSellAdapter(getActivity(), this.productVector, R.layout.get_transactions_grid_item);
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
		return new TransactionsMyPurchasesDialogFragment();
	}

	@Override
	protected String getDialogName() {
		return "my_purchases_dialog_fragment";
	}
}
