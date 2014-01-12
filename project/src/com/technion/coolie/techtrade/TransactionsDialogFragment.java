package com.technion.coolie.techtrade;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public abstract class TransactionsDialogFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		View view = inflater.inflate(this.getLayoutResourceId(), container);
		Product product = (Product) this.getArguments().getSerializable("product");
		this.initDialogFields(view, product);
		
		return view;
	}

	protected abstract int getLayoutResourceId();
	protected abstract void initDialogFields(View view, Product product);
}