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
	private EditText techoins;
	private EditText productName;
	private Button subBtn;
	private Product newProduct;

	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_transfer_funds_activity);

		productName = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_product_name);
		buyerName = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_buyer_name);
		sellerName = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_seller_name);
		buyerPhoneNumber = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_buyer_phone_number);
		techoins = (EditText) findViewById(R.id.get_transfer_funds_activity_enter_amount);
		subBtn = (Button) findViewById(R.id.get_transfer_funds_activity_btn);

		
		 newProduct = (Product)getIntent().getSerializableExtra("product");
		
		if (newProduct == null)
		{
			Toast toast1 = Toast
					.makeText(getApplicationContext(),
							"Error",
							Toast.LENGTH_SHORT);
			toast1.show();
		}
		
		subBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (buyerName.getText().toString().length() > 0

				&& buyerPhoneNumber.getText().toString().length() > 0) {
					Intent goBackHome = new Intent(TransferFundsActivity.this,
							MainActivity.class);
					startActivityForResult(goBackHome, 0);
				} else {
					Toast toast = Toast
							.makeText(getApplicationContext(),
									"Mandatory fields are required",
									Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

	}
	@ Override 
	protected void onResume()
	{
		if (UserOperations.isUserConnected() == true)
		{
			super.onResume();
			newProduct.setBuyerId(UserOperations.getUserId()); // TODO: get real ID
			newProduct.setBuyerName(UserOperations.getUserName());
			newProduct.setBuyerPhoneNumber(buyerPhoneNumber.getText().toString());
			newProduct.setSold();
			// Set Constant fields:
			productName.setText(newProduct.getName());
			productName.setEnabled(false);
			buyerName.setText(UserOperations.getUserName());
			buyerName.setEnabled(false);
			buyerPhoneNumber.setText(buyerPhoneNumber.getText().toString());
			sellerName.setText(newProduct.getSellerName());
			sellerName.setEnabled(false);
			techoins.setText(newProduct.getPriceString());
			techoins.setEnabled(false);

			// Update Product Class - buyerName && setAsBuy &&....
			class BuyProductAsyncTask extends AsyncTask<Void, Void, ReturnCode>{

				@Override
				protected ReturnCode doInBackground(Void... params) {
					//TODO second iteration handle errors
					TechTradeServer ttServer = new TechTradeServer();
					ReturnCode rc = ttServer.removeProduct(newProduct);
					if(rc != ReturnCode.SUCCESS){
						return rc;
					}
					rc = ttServer.addProduct(newProduct);
					return rc;
				}
				
			}
			new BuyProductAsyncTask().execute();

		}
		else
		{
			Toast.makeText(getApplicationContext(), "Please register to view content", Toast.LENGTH_SHORT);
//			Intent myIntent = new Intent(getBaseContext(), .class);//TODO change to the login activity
//			startActivity(myIntent);
		}
	}
}
