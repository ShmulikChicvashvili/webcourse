package com.technion.coolie.tecmind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.technion.coolie.CoolieActivity;

public class MyAccountActivity extends CoolieActivity {

	
	public void myAccountNav(View view) {
	    Intent intent = new Intent(MyAccountActivity.this, MyAccountActivity.class);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	    finish();
	}
	
	public void mineNav(View view) {
	    Intent intent = new Intent(MyAccountActivity.this, MineActivity.class);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	    finish();
	}
	public void myTitleNav(View view) {
	    Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	    finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.account_tab);
		
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab totTab = bar.newTab().setText("Last Mine");
		totTab.setTabListener(new TabListnerActivity(new RecentAccountActivity()));
		Tab recTab = bar.newTab().setText("Total");
		recTab.setTabListener(new TabListnerActivity(new TotalAccountActivity()));
		bar.addTab(totTab);
		bar.addTab(recTab);
	}


	
	
}
