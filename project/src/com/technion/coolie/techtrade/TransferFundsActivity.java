package com.technion.coolie.techtrade;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.technion.coolie.R;

public class TransferFundsActivity extends TechTradeActivity {
	private EditText buyerName;
	private EditText sellerName;
	private EditText buyerPhoneNumber;
	private EditText price;
	private EditText productName;
	private Product productToPurchase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_transfer_funds_activity);

		productName = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_product_name);
		buyerName = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_buyer_name);
		sellerName = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_seller_name);
		buyerPhoneNumber = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_buyer_phone_number);
		price = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_amount);

		productToPurchase = (Product) getIntent().getSerializableExtra("product");
		
		if (productToPurchase == null)
		{
			Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
		}

		productName.setText(productToPurchase.getName());
		sellerName.setText(productToPurchase.getSellerName());
		price.setText(productToPurchase.getPrice().toString());
		buyerName.setText(UserOperations.getUserName());
	}
	
	public void transferButtonWasPressed(View view){
		if(buyerPhoneNumber.getText().toString()==null || buyerPhoneNumber.getText().toString().length()==0){
			Toast.makeText(getApplicationContext(), "please supply your phone number", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "phone number inserted", Toast.LENGTH_SHORT).show();
		}
	}
}
