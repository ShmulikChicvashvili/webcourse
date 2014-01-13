package com.technion.coolie.techtrade;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.JsonSyntaxException;
import com.technion.coolie.CooliePriority;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieAsyncRequest;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionsActivity extends TechTradeActivity {
	private Vector<Product> mySalesVector;
	private Vector<Product> myPurchasesVector;
	private Vector<Product> myProductsVector;
	private RelativeLayout rootView;
	private LinearLayout tabStrip;
	private Vector<View> tabs;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_loading_screen);
	
		mySalesVector = new Vector<Product>();
		myPurchasesVector = new Vector<Product>();
		myProductsVector = new Vector<Product>();
		
		rootView = (RelativeLayout) getLayoutInflater().inflate(R.layout.get_transactions, null);
		tabStrip = (LinearLayout) rootView.findViewById(R.id.get_transactions_tabstrip);
		tabs = new Vector<View>();
		viewPager = (ViewPager) rootView.findViewById(R.id.get_transactions_pager);
		
		initializeTabs();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(handleConnectivity() == false){
			return;
		}
		
		loadProductVectorsFromInternetIntoFragmentsAndDismissLoadingScreen();
	}
	
	private void loadProductVectorsFromInternetIntoFragmentsAndDismissLoadingScreen() {
		new CoolieAsyncRequest(getApplicationContext(), CooliePriority.IMMEDIATELY) {
			
			@Override
			public Void actionOnServer(Void... params) {
				TechTradeServer ttServer = new TechTradeServer();
				fillMySalesVector(ttServer);
				fillMyPurchasesVector(ttServer);
				fillMyProductsVector(ttServer);
				return null;
			}
			
			@Override
			public Void onResult(Void result) {
				initializeViewPager();
				setContentView(rootView);
				return null;
			}

			private void fillMyProductsVector(TechTradeServer ttServer) {
				Log.v("Eran", "fillMyProductsVector");
				Product myProductsProduct = new Product(null, UserOperations.getUserId(), null, null, null, null, (byte[])null, null);
				List<Product> myProductsList = null;
				try {
					myProductsList = (List<Product>) ttServer.getPublishedProductsBySellerID(myProductsProduct);
					//myProductsList = Utils.getProductVectorStub(getApplicationContext(), 20);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				Log.v("Eran", "fillMyProductsVector return");
				if(myProductsList == null){
					myProductsList = new Vector<Product>();
				}
				for (Product product : myProductsList) {
					myProductsVector.add(product);
				}
			}

			private void fillMyPurchasesVector(TechTradeServer ttServer) {
				Log.v("Eran", "fillMyPurchasesVector");
				Product myPurchasesProduct = new Product(null, null, null, null, null, null, (byte[])null, null);
				myPurchasesProduct.setBuyerId(UserOperations.getUserId());
				List<Product> myPurchasesList = null;
				try {
					myPurchasesList = (List<Product>) ttServer.getPurchasedProductsByBuyerID(myPurchasesProduct);
					//myPurchasesList = Utils.getProductVectorStub(getApplicationContext(), 10);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				Log.v("Eran", "fillMyPurchasesVector return");
				if(myPurchasesList == null){
					myPurchasesList = new Vector<Product>();
				}
				for (Product product : myPurchasesList) {
					myPurchasesVector.add(product);
				}
			}

			private void fillMySalesVector(TechTradeServer ttServer) {
				Log.v("Eran", "fillMySalesVector");
				Product mySalesProduct = new Product(null, UserOperations.getUserId(), null, null, null, null, (byte[])null, null);
				List<Product> mySalesList = null;
				try {
					mySalesList = (List<Product>) ttServer.getSoldProductsBySellerID(mySalesProduct);
					//mySalesList = Utils.getProductVectorStub(getApplicationContext(), 0);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				Log.v("Eran", "fillMySalesVector return");
				if(mySalesList == null){
					mySalesList = new Vector<Product>();
				}
				for (Product product : mySalesList) {
					mySalesVector.add(product);
				}
			}
		}.run(); 
	}

	private void initializeViewPager() {
		TransactionsTabsPagerAdapter transactionsFragmentsAdapter = 
				new TransactionsTabsPagerAdapter(getSupportFragmentManager(), 
													mySalesVector, myPurchasesVector, myProductsVector);
		viewPager.setAdapter(transactionsFragmentsAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				changeActiveTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private boolean handleConnectivity() {
		if(NetworkOperations.isNetworkAvailable(getApplicationContext()) == false) {
			Toast.makeText(getApplicationContext(), R.string.get_connection_error, Toast.LENGTH_LONG).show();
			return false;
		}
		if(UserOperations.isUserConnected() == false){
			//TODO open connection dialog
			findViewById(R.id.get_loading_pbar).setVisibility(View.GONE);
			((TextView) findViewById(R.id.get_loading_text)).setText(R.string.get_not_logged_in);
			return false;
		}
		return true;
	}

	private void initializeTabs() {
		tabs = new Vector<View>();
		
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem((Integer) v.getTag());
			}
		};

		tabs.add(tabStrip.findViewById(R.id.get_transactions_my_sales_tab));
		tabs.add(tabStrip.findViewById(R.id.get_transactions_my_purchases_tab));
		tabs.add(tabStrip.findViewById(R.id.get_transactions_my_products_tab));
		
		for (int i = 0; i < tabs.size(); ++i) {
			tabs.elementAt(i).setClickable(true);
			tabs.elementAt(i).setTag(i);
			tabs.elementAt(i).setOnClickListener(listener);
		}
		
		changeActiveTab(0);
	}
	
	private void changeActiveTab(int position) {
		changeTabsBackground(tabs.elementAt(position));
	}
	
	private void changeTabsBackground(View newActiveTab) {
		int activeColor = Color.parseColor("#5066CCFF");
		int inactiveColor = Color.parseColor("#00000000");
		
		for (View v : tabs) {
			v.setBackgroundColor(inactiveColor);
		}
		newActiveTab.setBackgroundColor(activeColor);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
