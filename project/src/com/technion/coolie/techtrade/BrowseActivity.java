package com.technion.coolie.techtrade;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.technion.coolie.R;
import com.technion.coolie.techtrade.BrowseFragment.BrowseCallback;
import com.technion.coolie.techtrade.ProductFragment.productCallback;
import com.technion.coolie.techtrade.TechTradeServer;;

public class BrowseActivity extends TechTradeActivity implements productCallback, BrowseCallback{
	Product currentProduct;
	String myCategory;
	BrowseFragment bFragment;
	BrowseFragment bHorizontalFragment;
	ProductFragment pFragment;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_browse_by_catagory_activity);

		bFragment = (BrowseFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagoryFragmet);
		bHorizontalFragment = (BrowseFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagoryHorizontalFragmet);
		pFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);

		myCategory = getIntent().getStringExtra("category");
		String searchTerm = getIntent().getStringExtra("searchTerm");
		Product product = new Product(searchTerm, null, Category.getValueOf(myCategory), null, null, null, (Bitmap)null, null);

		class GetProductsByCategoryAndName extends AsyncTask<Product, Void, List<Product>>{
			@Override
			protected void onPreExecute() {
				pd = ProgressDialog.show(BrowseActivity.this, null, "Please wait...");
			}

			@Override
			protected List<Product> doInBackground(Product... products) {
				TechTradeServer ttServer = new TechTradeServer();
				List<Product> browseProductsVector = ttServer.getProductsByCategory(products[0]);
				Vector<Product> resultList = new Vector<Product>();

				if(products[0].getName()!=null){
					for(int i=0; i<browseProductsVector.size(); ++i){
						if(browseProductsVector.get(i).getName().contains(products[0].getName())){
							resultList.add(browseProductsVector.get(i));
						}
					}
					return resultList;
				}
				return browseProductsVector;
			}

			@Override
			protected void onPostExecute(List<Product> result) {
				if(result.isEmpty()){
					currentProduct = new Product();
				}else{
					currentProduct = result.get(0);
				}
				if (bFragment != null && bFragment.isInLayout()){
					bFragment.setProductList(result);
				}
				if (bHorizontalFragment != null && bHorizontalFragment.isInLayout()){
					bHorizontalFragment.setProductList(result);
				}
				if (pFragment != null && pFragment.isInLayout()){
					if(!result.isEmpty()){
						pFragment.setProduct(currentProduct);
					}
				}
				pd.dismiss();
			}
		}		

		new GetProductsByCategoryAndName().execute(product); 
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

		currentProduct = product;

		if (pFragment != null && pFragment.isInLayout()) {
			pFragment.setProduct(currentProduct);
		}else{
			Intent intent = new Intent(this, ProductActivity.class);
			intent.putExtra("product",(Serializable) currentProduct);
			startActivity(intent);
		}
	}

	@Override
	public void searchWasActivated(String searchTerm) {
		if(searchTerm==null)return;
		Intent intent = new Intent(this, BrowseActivity.class);
		intent.putExtra("searchTerm", searchTerm);
		intent.putExtra("category", myCategory);
		startActivity(intent);
	}
}