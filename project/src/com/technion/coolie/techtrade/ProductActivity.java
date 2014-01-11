package com.technion.coolie.techtrade;

import java.io.Serializable;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.technion.coolie.R;
import com.technion.coolie.techtrade.ProductFragment.productCallback;

public class ProductActivity extends TechTradeActivity implements productCallback{

	Product thisProduct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_product_page_activity);

		thisProduct = (Product) getIntent().getSerializableExtra("product");

		ProductFragment fragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);
		fragment.setProduct(thisProduct);
	}

	@Override
	public void sms() {		
		/** use Product to get seller tell and call him*/
		try{
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address", thisProduct.getSellerPhoneNumber());
			startActivity(smsIntent);
		}catch(ActivityNotFoundException  e){
			Toast.makeText(this, "no SMS possible from this device.\r\n to bad, so sad", Toast.LENGTH_SHORT).show();			
		}
	}

	@Override
	public void call() {			
		/** use Product to get seller tell and call him*/
		try{
			Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse("tel:"+thisProduct.getSellerPhoneNumber()));
			startActivity(callIntent);  
		}catch(ActivityNotFoundException  e){
			Toast.makeText(this, "no calls possible from this device.\r\n you can't even call home \r\n we have you now ET", Toast.LENGTH_SHORT).show();			
		}
	}

	@Override
	public void buy() {
		try{
	  	  	Intent intent = new Intent(this, TransferFundsActivity.class);
	  	  	intent.putExtra("product", (Serializable) thisProduct);
	  	  	startActivity(intent);
		}catch(ActivityNotFoundException  e){
			Toast.makeText(this, "tivan if you are seeing this the intent did not find your activity, make you you annonced it.", Toast.LENGTH_SHORT).show();			
		}
	}
}