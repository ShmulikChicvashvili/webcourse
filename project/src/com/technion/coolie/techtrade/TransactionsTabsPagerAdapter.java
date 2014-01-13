package com.technion.coolie.techtrade;

import java.util.Vector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TransactionsTabsPagerAdapter extends FragmentPagerAdapter {
	Fragment transactionsMySalesFragment;
	Fragment transactionsMyPurchasesFragment;
	Fragment transactionsMyProductsFragment;
	
	public TransactionsTabsPagerAdapter(FragmentManager fm, Vector<Product> mySalesVector, Vector<Product> myPurchasesVector, Vector<Product> myProductsVector) {
		super(fm);
		
		transactionsMySalesFragment = new TransactionsMySalesFragment(mySalesVector);
		transactionsMyPurchasesFragment = new TransactionsMyPurchasesFragment(myPurchasesVector);
		transactionsMyProductsFragment = new TransactionsMyProductsFragment(myProductsVector);
	}

	@Override
	public Fragment getItem(int index) {
		switch(index){
		case 0:
			return transactionsMySalesFragment;
		case 1:
			return transactionsMyPurchasesFragment;
		case 2:
			return transactionsMyProductsFragment;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

}
