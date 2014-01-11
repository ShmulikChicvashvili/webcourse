package com.technion.coolie.techtrade;


import java.util.List;
import java.util.Vector;

import android.content.Context;

public class TransactionsFragmentProductAdapter extends TransactionsFragmentAdapter {

	public TransactionsFragmentProductAdapter(Context context,List<?> itemsVector, int adapterItemResourceId) {
		super(context, itemsVector, adapterItemResourceId);
	}

	@Override
	protected String getDate(Product product) {
		return product.getPublishDate();
	}

}
