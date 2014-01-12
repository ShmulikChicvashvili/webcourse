package com.technion.coolie.techtrade;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.technion.coolie.R;
import com.technion.coolie.techtrade.ProductFragment.productCallback;
import com.technion.coolie.techtrade.SearchFragment.searchCallback;

public class SearchActivity extends TechTradeActivity implements productCallback, searchCallback{
	Product currentProduct;
	SearchFragment sFragment;
	SearchFragment sHorizontalFragment;
	ProductFragment pFragment;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_search_activity);
		sFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.searchFragment);
		pFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);
		sHorizontalFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.searchHorizontalFragment);
				
		class GetProductsByName extends AsyncTask<Product, Void, List<Product>> {
			@Override
			protected void onPreExecute() {
				pd = ProgressDialog.show(SearchActivity.this, null, "Please wait...");
			}

			@Override
			protected List<Product> doInBackground(Product... products) {
				TechTradeServer ttServer = new TechTradeServer();
				List<Product> searchProductVector = (List<Product>) ttServer.getProductsByName(products[0]);
				if(searchProductVector==null){
					Vector<Product> searchProductVectorTemp = new Vector<Product>();
					return searchProductVectorTemp;
				}
				return searchProductVector;
			}

			@Override
			protected void onPostExecute(List<Product> result) {
				if(result.size()>0){
					currentProduct = result.get(0);
				}else{
					currentProduct = new Product();
				}
				if(sFragment != null && sFragment.isInLayout()){
					sFragment.setProductList(result);
				}
				if(sHorizontalFragment != null && sHorizontalFragment.isInLayout()){
					sHorizontalFragment.setProductList(result);
				}
				if (pFragment != null && pFragment.isInLayout()){
					pFragment.setProduct(currentProduct);
				}
				pd.dismiss();
			}
		}

		String searchTerm = getIntent().getStringExtra("searchTerm");
		Product product = new Product(searchTerm, null, null, null, null, null, (byte[])null, null);
		new GetProductsByName().execute(product);
	}

	@Override
	public void sms() {		
		/** use Product to get seller tell and call him*/
		try{
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address", currentProduct.getSellerPhoneNumber());
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
			callIntent.setData(Uri.parse("tel:"+currentProduct.getSellerPhoneNumber()));
			startActivity(callIntent);  
		}catch(ActivityNotFoundException  e){
			Toast.makeText(this, "no calls possible from this device.\r\n you can't even call home \r\n we have you now ET", Toast.LENGTH_SHORT).show();			
		}
	}


	@Override
	public void buy() {

		Intent intent = new Intent(this, TransferFundsActivity.class);
		intent.putExtra("product", currentProduct);
		startActivity(intent);
	}

	@Override
	public void listWasPressed(Product product) {
		// TODO Auto-generated method stub
		//    	Toast.makeText(this, "item's name is: "+ product.getName(), Toast.LENGTH_SHORT).show();
		//
		currentProduct = product;

		ProductFragment fragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.setProduct(product);
		}else {
			Intent intent = new Intent(this, ProductActivity.class);
			intent.putExtra("product",(Serializable) currentProduct);
			startActivity(intent);
		}
	}
}