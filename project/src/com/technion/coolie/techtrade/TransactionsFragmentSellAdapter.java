package com.technion.coolie.techtrade;


import java.util.List;
import android.content.Context;

public class TransactionsFragmentSellAdapter extends TransactionsFragmentAdapter {

	public TransactionsFragmentSellAdapter(Context context,List<?> itemsList, int adapterItemResourceId) {
		super(context, itemsList, adapterItemResourceId);
	}

	@Override
	protected String getDate(Product product) {
		return product.getSellDate();
	}

}
