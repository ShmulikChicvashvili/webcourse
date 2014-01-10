package com.technion.coolie.techtrade;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.technion.coolie.R;

public class TransactionsMyPurchasesDialogFragment extends TransactionsDialogFragment {
	private String phoneNumber;
	
	@Override
	protected int getLayoutResourceId(){
		return R.layout.get_transactions_my_purchases_dialog_fragment;
	}
	
	@Override
	protected void initDialogFields(View view, Product product) {
		((TextView) view.findViewById(R.id.get_transactions_my_purchases_fragment_product_name)).setText(product.getName());
		((TextView) view.findViewById(R.id.get_transactions_my_purchases_fragment_sell_price)).setText(product.getPriceString());
		((TextView) view.findViewById(R.id.get_transactions_my_purchases_fragment_sell_date)).setText(product.getSellDate());
		((TextView) view.findViewById(R.id.get_transactions_my_purchases_fragment_seller_name)).setText(product.getBuyerName());
		this.phoneNumber = product.getBuyerPhoneNumber();
		((Button) view.findViewById(R.id.get_transactions_my_purchases_fragment_call_button)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContactOperations.call(getActivity(), phoneNumber);
			}
		});
		((Button) view.findViewById(R.id.get_transactions_my_purchases_fragment_sms_button)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContactOperations.sms(getActivity(), phoneNumber);
			}
		});
		((Button) view.findViewById(R.id.get_transactions_my_purchases_fragment_remove_button)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO add functionality
			}
		});
		
	}
}
