package com.technion.coolie.techtrade;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.devsmart.android.ui.HorizontalListView;
import com.technion.coolie.R;
import com.technion.coolie.techtrade.ProductListFragment.ProductListCallback;

public class MainActivity extends TechTradeActivity implements ProductListCallback{

	private ProgressDialog pd;
	private ProductListFragment randomListFragment;
	private ProductListFragment recentListFragment;
	private View emptyStateView;
	private View normalStateView;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  

		setContentView(R.layout.get_main_activity);  

		randomListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.randomProductListFragment);
		recentListFragment= (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.recentProductListFragment);

		emptyStateView = (View) findViewById(R.id.get_welcome_activity_empty_lists_messege);
		normalStateView = (View) findViewById(R.id.get_welcome_activity_relative_layout);
		
		if(needToAccessServer()){
			AccessServer();
		}
		showCorrectFragments();
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
		class GetMainScreenProducts extends AsyncTask<Void, Void, Vector<List<Product>>>{
			@Override
			protected void onPreExecute() {
				pd = ProgressDialog.show(MainActivity.this, null, "Please wait...");
			}

			@Override
			protected Vector<List<Product>> doInBackground(Void... products) {
				TechTradeServer ttServer = new TechTradeServer();
				Vector<List<Product>> returnVector = new Vector<List<Product>>();
				//TODO change instead of magic number to enum
				returnVector.add(ttServer.getXRandomProducts(20));
				returnVector.add(ttServer.getXRecentProducts(20));				
				return returnVector;
			}

			@Override
			protected void onPostExecute(Vector<List<Product>> result){
				if(randomListFragment != null && randomListFragment.isInLayout() && !randomListFragment.hasAdapter()){
					randomListFragment.setProductList(result.elementAt(0));
				}
				if (recentListFragment != null && recentListFragment.isInLayout() && !recentListFragment.hasAdapter()){
					recentListFragment.setProductList(result.elementAt(1));
				}
				pd.dismiss();
			}
		}
		new GetMainScreenProducts().execute();
	}

	private void showCorrectFragments(){
		//handeling empty state
		//TODO do this better
		if(recentListFragment.isEmpty() && randomListFragment.isEmpty()){
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