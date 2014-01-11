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
import com.technion.coolie.techtrade.ProductListFragment.ProductListCallback;

public class SearchActivity extends TechTradeActivity implements productCallback, ProductListCallback{
	private ProductListFragment plFragment;
	private ProductFragment pFragment;
	private ProgressDialog pd;
	private String searchTerm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_search_activity);

		plFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.productListFragment);
		pFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);
		searchTerm = getIntent().getStringExtra("searchTerm");

		if(needToAccessServer()){
			AccessServer();
		}else{
			wakeUpAllExistingFragments();
		}
	}

	private boolean needToAccessServer(){
		if(plFragment != null && plFragment.isInLayout()){
			if(!plFragment.hasAdapter()) return true;
		}
		if (pFragment != null && pFragment.isInLayout()){
			if(!pFragment.hasProduct()) return true;
		}
		return false;
	}

	private void AccessServer(){
		class GetProductsBySearchTerm extends AsyncTask<Product, Void, List<Product>>{
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
				if(plFragment != null && plFragment.isInLayout() && !plFragment.hasAdapter()){
					plFragment.setProductList(result);
				}
				if (pFragment != null && pFragment.isInLayout() && !pFragment.hasProduct()){
					if(result!=null && !result.isEmpty()) pFragment.setProduct(result.get(0));
				}
				pd.dismiss();
			}
		}

		Product searchProduct = new Product();
		searchProduct.setName(searchTerm);
		new GetProductsBySearchTerm().execute(searchProduct);
		showCorrectList();
	}

	private void wakeUpAllExistingFragments(){
		if(plFragment != null && plFragment.isInLayout() && plFragment.hasAdapter()){
			showCorrectList();
		}
		if (pFragment != null && pFragment.isInLayout() && pFragment.hasProduct()){
			pFragment.wakeUp();
		}
	}

	private void showCorrectList(){
		if(pFragment != null && pFragment.isInLayout()){
			//for a big screen
			if(SearchActivity.this.getResources().getConfiguration().orientation == 
					SearchActivity.this.getResources().getConfiguration().ORIENTATION_LANDSCAPE){
				plFragment.showVertical();
			}else{
				plFragment.showHorizontal();
			}
		}else{
			//for a small one
			plFragment.showVertical();			
		}
	}

	@Override
	public void sms() {
		/** use Product to get seller tell and call him*/
		try{
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address", pFragment.getProduct().getSellerPhoneNumber());
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
			callIntent.setData(Uri.parse("tel:"+pFragment.getProduct().getSellerPhoneNumber()));
			startActivity(callIntent);  
		}catch(ActivityNotFoundException  e){
			Toast.makeText(this, "no calls possible from this device.\r\n you can't even call home \r\n we have you now ET", Toast.LENGTH_SHORT).show();			
		}
	}

	@Override
	public void buy() {
		Intent intent = new Intent(this, TransferFundsActivity.class);
		intent.putExtra("product", pFragment.getProduct());
		startActivity(intent);
	}

	@Override
	public void listWasPressed(Product product) {
		// TODO Auto-generated method stub

		ProductFragment fragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.setProduct(product);
		}else {
			Intent intent = new Intent(this, ProductActivity.class);
			intent.putExtra("product",(Serializable) product);
			startActivity(intent);
		}
	}
}