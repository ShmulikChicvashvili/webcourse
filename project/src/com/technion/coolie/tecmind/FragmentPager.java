package com.technion.coolie.tecmind;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPager extends FragmentPagerAdapter {

	final int pageCount = 2;
	public FragmentPager(FragmentManager fm) {
	    super(fm);
	    // TODO Auto-generated constructor stub
	}
	
	@Override
	public Fragment getItem(int position) {
	
		   switch (position) {
		    case 0:
		        // Your current main fragment showing how to send arguments to fragment
		        return new CountersFragment();
		    case 1:
		        // Calling a Fragment without sending arguments
		        return new GraphFragment();
		    default:
		        return null;
		    }
	
	}
	
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return pageCount;
	}
	
	

}