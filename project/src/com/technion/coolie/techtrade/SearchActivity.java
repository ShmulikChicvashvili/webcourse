package com.technion.coolie.techtrade;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.technion.coolie.CooliePriority;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieAsyncRequest;
import com.technion.coolie.techtrade.ProductFragment.productCallback;
import com.technion.coolie.techtrade.ProductListFragment.ProductListCallback;

public class SearchActivity extends TechTradeActivity implements productCallback, ProductListCallback{
	private ProductListFragment plFragment;
	private ProductFragment pFragment;

	private Product searchProduct = new Product();

	private View emptyStateView;
	private View normalStateView;
	private View pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_search_activity);

		plFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.searchProductListFragment);
		pFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.searchProductFragment);

		searchProduct.setName( getIntent().getStringExtra("searchTerm"));

		emptyStateView = (View) findViewById(R.id.get_search_no_result_messege);
		normalStateView = (View) findViewById(R.id.get_search_activity_layout);
		pb = (View) findViewById(R.id.get_search_screen_pbar);

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
		CoolieAsyncRequest GetProductsBySearchTerm = new CoolieAsyncRequest(this,CooliePriority.IMMEDIATELY) {
			List<Product> searchedItems = null;

			@Override
			public Void actionOnServer(Void... params) {
				TechTradeServer ttServer = new TechTradeServer();
				try {
					searchedItems = (List<Product>) ttServer.getProductsByName(searchProduct);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Void onResult(Void result) { 
				if(searchedItems != null && searchedItems.size()>0){
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
					Toast.makeText(SearchActivity.this, "found nothing, making fake", Toast.LENGTH_SHORT).show();
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
		GetProductsBySearchTerm.run();
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
			if(SearchActivity.this.getResources().getConfiguration().orientation 
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

	//TODO remove
	private void makeStubs(){
		Vector<Product> search = new Vector<Product>();
		for(int i = 0; i<20; ++i){
			Product p = new Product();
			p.setDescripstion(i + searchProduct.getName() + "  description");
			p.setName(searchProduct.getName() + " product " + i);
			p.setSellerName(searchProduct.getName() + " seller " + i);
			p.setId((long) i);
			p.setPrice((double) (i));
			p.setSellerId(""+i);
			p.setSellerPhoneNumber(""+i);
			search.add(p);
		}
		plFragment.setProductList(search);
		if (pFragment != null && pFragment.isInLayout()){
				pFragment.setProduct(search.get(0));
		}
	}
}