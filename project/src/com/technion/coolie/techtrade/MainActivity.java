package com.technion.coolie.techtrade;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonSyntaxException;
import com.technion.coolie.CooliePriority;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieAsyncRequest;
import com.technion.coolie.techtrade.ProductListFragment.ProductListCallback;

public class MainActivity extends TechTradeActivity implements ProductListCallback{

	private ProductListFragment randomListFragment;
	private ProductListFragment recentListFragment;
	private View emptyStateView;
	private View normalStateView;
	private View pb;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);

		setContentView(R.layout.get_main_activity);  

		randomListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.randomProductListFragment);
		recentListFragment= (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.recentProductListFragment);

		emptyStateView = (View) findViewById(R.id.get_welcome_activity_empty_lists_messege);
		normalStateView = (View) findViewById(R.id.get_welcome_activity_relative_layout);
		pb = (View) findViewById(R.id.get_welcome_screen_pbar);

		if(needToAccessServer()){
			AccessServer();
		}else{
			showCorrectFragments();
		}
		
	}

	private boolean needToAccessServer(){
		if(randomListFragment != null && randomListFragment.isInLayout()){
			if(!randomListFragment.hasAdapter()) return true;
		}
		if (recentListFragment != null && recentListFragment.isInLayout()){
			if(!recentListFragment.hasAdapter()) return true;
		}
		return false;
	}

	private void AccessServer(){
		
		CoolieAsyncRequest GetMainScreenProducts = new CoolieAsyncRequest(this,CooliePriority.IMMEDIATELY) {
			List<Product> random = null;
			List<Product> recent = null;

			@Override
			public Void actionOnServer(Void... params) {
				TechTradeServer ttServer = new TechTradeServer();
				//TODO change instead of magic number to enum
				try {
					random = ttServer.getXRandomProducts(20);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					recent = ttServer.getXRecentProducts(20);
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
				if(randomListFragment != null && randomListFragment.isInLayout() && !randomListFragment.hasAdapter()){
					randomListFragment.setProductList(random);
				}
				if (recentListFragment != null && recentListFragment.isInLayout() && !recentListFragment.hasAdapter()){
					recentListFragment.setProductList(recent);
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
		GetMainScreenProducts.run();
	}

	private void showCorrectFragments(){
		//handeling empty state
		//TODO do this better
		if(recentListFragment.isEmpty() || randomListFragment.isEmpty()){
			emptyStateView.setVisibility(View.VISIBLE);
			normalStateView.setVisibility(View.GONE);
			return;
		}
		//not empty state
		emptyStateView.setVisibility(View.GONE);
		normalStateView.setVisibility(View.VISIBLE);
		
		if(randomListFragment != null && randomListFragment.isInLayout() && randomListFragment.hasAdapter()){
			randomListFragment.showHorizontal();
		}
		if (recentListFragment != null && recentListFragment.isInLayout() && recentListFragment.hasAdapter()){
			recentListFragment.showHorizontal();
		}
	}

	@Override
	public void listWasPressed(Product product) {
		//    	Toast.makeText(this, "item's name is: "+ product.getName(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, ProductActivity.class);
		intent.putExtra("product",(Serializable) product);
		startActivity(intent);
	}
}