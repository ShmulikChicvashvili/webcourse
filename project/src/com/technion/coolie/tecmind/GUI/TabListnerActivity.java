package com.technion.coolie.tecmind.GUI;

import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;


public class TabListnerActivity implements com.actionbarsherlock.app.ActionBar.TabListener  {
       private SherlockFragment mFragment;
    
       public TabListnerActivity(SherlockFragment fragment) {
    	   mFragment = fragment;
       }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		 ft.add(android.R.id.content, mFragment, null);
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
    	   	ft.remove(mFragment);
       }
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
