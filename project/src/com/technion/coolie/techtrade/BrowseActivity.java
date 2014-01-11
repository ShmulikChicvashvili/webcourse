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
import com.technion.coolie.techtrade.SearchBoxFragment.SearchBoxCallback;

public class BrowseActivity extends TechTradeActivity implements productCallback, ProductListCallback, SearchBoxCallback{
	String myCategory;
	ProductListFragment plFragment;
	SearchBoxFragment sbFragment;
	ProductFragment pFragment;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_browse_by_catagory_activity);

		plFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagoryListViewFragment);
		sbFragment = (SearchBoxFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagorySearchBoxFragment);
		pFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.productFragment);

		Product product = new Product();
		myCategory = getIntent().getStringExtra("category");
		product.setCategory(Category.getValueOf(getIntent().getStringExtra("category")));
		product.setName(getIntent().getStringExtra("searchTerm"));		

		if(needToAccessServer()){
			AccessServer(product);
		}
		//TODO merge to one thing
		handleEmptyState();
		showCorrectFragments();
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

	private void AccessServer(Product dummyProduct){
		
		//TODO old
		class GetProductsByCategoryAndName extends AsyncTask<Product, Void, List<Product>>{
			@Override
			protected void onPreExecute(){
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
				if (plFragment != null && plFragment.isInLayout()){
					plFragment.setProductList(result);
				}
				if (pFragment != null && pFragment.isInLayout()){
					if(!result.isEmpty()){
						pFragment.setProduct(result.get(0));
					}
				}
				pd.dismiss();
			}
		}
		new GetProductsByCategoryAndName().execute(dummyProduct);
	}

	private void handleEmptyState(){
		//TODO change to real handeling
		if(plFragment.isEmpty()){

			Vector<Product> category = new Vector<Product>();
			for(int i = 0; i<20; ++i){
				Product p = new Product();
				p.setDescripstion(i + " category description");
				p.setName("category product " + i);
				p.setSellerName("category seller " + i);
				p.setId((long) i);
				p.setPrice((double) (i));
				p.setSellerId(""+i);
				p.setSellerPhoneNumber(""+i);
				category.add(p);
			}
			
			plFragment.setProductList(category);
		}
	}
	
	private void showCorrectFragments(){
		if(pFragment != null && pFragment.isInLayout()){
			//for a big screen
			if(BrowseActivity.this.getResources().getConfiguration().orientation == 
					BrowseActivity.this.getResources().getConfiguration().ORIENTATION_LANDSCAPE){
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
		if (pFragment != null && pFragment.isInLayout()) {
			pFragment.setProduct(product);
		}else{
			Intent intent = new Intent(this, ProductActivity.class);
			intent.putExtra("product",(Serializable) product);
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