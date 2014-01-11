package com.technion.coolie.techtrade;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.technion.coolie.R;

public class TransactionsMyProductsDialogFragment extends TransactionsDialogFragment {
	
	@Override
	protected int getLayoutResourceId(){
		return R.layout.get_transactions_my_products_dialog_fragment;
	}
	
	@Override
	protected void initDialogFields(View view, Product product) {
		((TextView) view.findViewById(R.id.get_transactions_my_products_fragment_product_name)).setText(product.getName());
		((TextView) view.findViewById(R.id.get_transactions_my_products_fragment_price)).setText(product.getPriceString());
		((TextView) view.findViewById(R.id.get_transactions_my_products_fragment_category)).setText(product.getCategoryName());
		((TextView) view.findViewById(R.id.get_transactions_my_products_fragment_publish_date)).setText(product.getPublishDate());
		((TextView) view.findViewById(R.id.get_transactions_my_products_fragment_contact_phone)).setText(product.getSellerPhoneNumber());
		((TextView) view.findViewById(R.id.get_transactions_my_products_fragment_description)).setText(product.getDescripstion());
		
		((Button) view.findViewById(R.id.get_transactions_my_products_fragment_edit_button)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO add functionality
			}
		});
		((Button) view.findViewById(R.id.get_transactions_my_products_fragment_remove_button)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO add functionality
			}
		});
	}
}
