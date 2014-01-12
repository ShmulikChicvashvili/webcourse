package com.technion.coolie.techtrade;

import java.util.Vector;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TransactionsActivity extends TechTradeActivity {
	private ViewPager viewPager;
	private TransactionsTabsPagerAdapter mAdapter;
	private LinearLayout tabStrip;
	private Vector<View> tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_transactions);
		
		mAdapter = new TransactionsTabsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.get_transactions_pager);
		tabStrip = (LinearLayout) findViewById(R.id.get_transactions_tabstrip);
		tabs = new Vector<View>();
		initializeTabs();
		viewPager.setAdapter(mAdapter);
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

	private void initializeTabs() {
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
	protected void onResume() {
		super.onResume();
		// TODO Auto-generated method stub
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
