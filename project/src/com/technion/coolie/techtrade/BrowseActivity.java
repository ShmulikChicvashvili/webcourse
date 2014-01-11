package com.technion.coolie.techtrade;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.technion.coolie.CooliePriority;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieAsyncRequest;
import com.technion.coolie.techtrade.ProductFragment.productCallback;
import com.technion.coolie.techtrade.ProductListFragment.ProductListCallback;
import com.technion.coolie.techtrade.SearchBoxFragment.SearchBoxCallback;

public class BrowseActivity extends TechTradeActivity implements productCallback, ProductListCallback, SearchBoxCallback{
	ProductListFragment plFragment;
	SearchBoxFragment sbFragment;
	ProductFragment pFragment;

	Product searchProduct = new Product();

	private View emptyStateView;
	private View normalStateView;
	private View pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_browse_by_catagory_activity);

		plFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagoryListViewFragment);
		sbFragment = (SearchBoxFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagorySearchBoxFragment);
		pFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.browseByCatagoryProductFragment);

		searchProduct.setCategory(Category.getValueOf(getIntent().getStringExtra("category")));
		searchProduct.setName(getIntent().getStringExtra("searchTerm"));		

		emptyStateView = (View) findViewById(R.id.get_browse_by_category_no_result_messege);
		normalStateView = (View) findViewById(R.id.get_browse_by_catagory_activity_layout);
		pb = (View) findViewById(R.id.get_browse_screen_pbar);

		if(needToAccessServer()){
			AccessServer();
		}else{
			showCorrectFragments();
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

		CoolieAsyncRequest GetProductsByCategoryAndName = new CoolieAsyncRequest(this,CooliePriority.IMMEDIATELY) {
			List<Product> searchedItems = null;

			@Override
			public Void actionOnServer(Void... params) {
				TechTradeServer ttServer = new TechTradeServer();
				List<Product> sameCategory = ttServer.getProductsByCategory(searchProduct);

				if(searchProduct.getName()!=null){
					for(int i=0; i<sameCategory.size(); ++i){
						if(sameCategory.get(i).getName().contains(searchProduct.getName())){
							searchedItems.add(sameCategory.get(i));
						}
					}
				}else{
					searchedItems = sameCategory;
				}				
				return null;
			}

			@Override
			public Void onResult(Void result){
				if(searchedItems!= null && searchedItems.size()>0){
					if(plFragment != null && plFragment.isInLayout() && !plFragment.hasAdapter()){
						plFragment.setProductList(searchedItems);
					}
					if (pFragment != null && pFragment.isInLayout() && !pFragment.hasProduct()){
						if(searchedItems!=null && !searchedItems.isEmpty()){
							pFragment.setProduct(searchedItems.get(0));
						}else{
							pFragment.setProduct(null);
						}
					}
				}else{
					//TODO remove
					//TODO remove
					//TODO remove
					Toast.makeText(BrowseActivity.this, "found nothing, making fake", Toast.LENGTH_SHORT).show();
					makeStubs();
					//TODO remove
				}
				pb.setVisibility(View.GONE);
				showCorrectFragments();
				return null;
			}

			@Override
			public Void onProgress(Void... values){
				pb.setVisibility(View.VISIBLE);
				return null;
			}
		};
		
		pb.setVisibility(View.VISIBLE);
		GetProductsByCategoryAndName.run();
	}

	private void showCorrectFragments(){
		if(plFragment.isEmpty()){
			emptyStateView.setVisibility(View.VISIBLE);
			normalStateView.setVisibility(View.GONE);
			return;
		}
		//not empty state
		emptyStateView.setVisibility(View.GONE);
		normalStateView.setVisibility(View.VISIBLE);

		if(pFragment != null && pFragment.isInLayout()){
			//for a big screen
			pFragment.wakeUp();
			//showing list by oriantation
			if(BrowseActivity.this.getResources().getConfiguration().orientation 
					== Configuration.ORIENTATION_LANDSCAPE){
				plFragment.showVertical();
			}else{
				plFragment.showHorizontal();
			}
		}else{
			//for a small screen
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
		intent.putExtra("category", searchProduct.getCategoryName());
		startActivity(intent);
	}

	//TODO remove
	private void makeStubs(){
		//TODO change to real handeling
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
		if (pFragment != null && pFragment.isInLayout()){
			pFragment.setProduct(category.get(0));
		}
	}
}