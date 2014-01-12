package com.technion.coolie.techtrade;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TransactionsTabsPagerAdapter extends FragmentPagerAdapter {
	
	public TransactionsTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch(index){
		case 0:
			return new TransactionsMySalesFragment();
		case 1:
			return new TransactionsMyPurchasesFragment();
		case 2:
			return new TransactionsMyProductsFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

}
