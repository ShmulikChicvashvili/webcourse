package com.technion.coolie.tecmind.GUI;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.technion.coolie.CoolieActivity;

public class TopTenActivity extends CoolieActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.account_tab);
		
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab totTab = bar.newTab().setText("Top 10 Minners");
		totTab.setTabListener(new TabListnerActivity(new TotalAccountActivity()));
		Tab recTab = bar.newTab().setText("Top 10 Comments");
		recTab.setTabListener(new TabListnerActivity(new RecentAccountActivity()));
		bar.addTab(totTab);
		bar.addTab(recTab);
	}
}
