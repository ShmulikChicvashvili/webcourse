package com.technion.coolie.techlibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LoanElement;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class LibraryCardFragment extends SherlockFragment {

	private ArrayList<LoanElement> globalLoansList = null;
	private ArrayList<HoldElement> globalHoldsList = null;
	private FragmentManager mChildFragmentManager;
	private LoansFragment mLoansFragment = null;
	protected HoldsFragment mHoldsFragment = null;
	private WishListFragment mWishListFragment = null;
	private ViewPager mViewPager;
	protected ActionBar mActionBar;
	private static final String SHARED_PREF = "lib_pref";
	private SharedPreferences mSharedPref;
	private TitlePageIndicator mIndicator;

	private static final String NA = "n/a";
	private static final String USER_ID = "user_id";
	private static final String userLoansUrl = "https://aleph2.technion.ac.il/X?op=bor-info&bor_id=";
	private static final int LOGGIN_CODE = 7;

	private boolean isLoggedFirst = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mChildFragmentManager = getChildFragmentManager();
		mLoansFragment = new LoansFragment();
		mHoldsFragment = new HoldsFragment();
		mWishListFragment = new WishListFragment();
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivityForResult(intent, LOGGIN_CODE);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (MainActivity.currPosition == 0 && isLoggedFirst) {
			SharedPreferences mSharedPref = getActivity().getSharedPreferences(
					SHARED_PREF, 0);
			if (mSharedPref.getBoolean("is_logged", false) == false) {
				mHoldsFragment
						.notifyListChange(new ArrayList<BookItems.HoldElement>());
				mLoansFragment
						.notifyListChange(new ArrayList<BookItems.LoanElement>());
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (LOGGIN_CODE): {
			if (resultCode != LoginActivity.RESULT_OK) {
				Log.v("LibraryCard:", "got login result BAD!");
			} else {
				isLoggedFirst = true;
				Log.v("LibraryCard:", "got login result OK!");
				sendInfoToFragments();
			}
			break;
		}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.lib_fragment_library_card,
				container, false);

		mViewPager = (ViewPager) v.findViewById(R.id.lib_pager);
		if (mViewPager == null) {
			FragmentTransaction transaction = mChildFragmentManager
					.beginTransaction();
			transaction.add(R.id.lib_holds_frame, mHoldsFragment);
			transaction.add(R.id.lib_loans_frame, mLoansFragment);
			transaction.add(R.id.lib_wishes_frame, mWishListFragment);
			transaction.commit();
		} else {
			mViewPager.setOnPageChangeListener(onPageChangeListener);
			mViewPager.setAdapter(new ViewPagerAdapter(mChildFragmentManager));
			mViewPager.setOffscreenPageLimit(3);
			mIndicator = (TitlePageIndicator) v
					.findViewById(R.id.lib_indicator);
			mIndicator.setViewPager(mViewPager);
			//
			// addActionBarTabs();
		}
		return v;
	}

	void sendInfoToFragments() {
		mSharedPref = getSherlockActivity()
				.getSharedPreferences(SHARED_PREF, 0);
		String id = mSharedPref.getString(USER_ID, NA);
		String userAuthUrl = userLoansUrl + id;
		if (!isOnline()) {
			toastConnectionError();
		} else {
			HtmlGrabber hg = new HtmlGrabber(getSherlockActivity()) {
				@Override
				public void handleResult(String result, CoolieStatus status) {

					if (status == CoolieStatus.RESULT_OK) {
						// Log.v("LibraryCard:", "graber result-" + result);
						mHoldsFragment.onXMLRecieved(result);
						mLoansFragment.onXMLRecieved(result);
					} else {
						// TODO: generate error
					}

				}
			};
			hg.getHtmlSource(userAuthUrl, HtmlGrabber.Account.NONE);
		}
	}

	/**
	 * getting loans list for fragment
	 */
	protected ArrayList<LoanElement> getLoansList() {
		return (globalLoansList == null) ? new ArrayList<BookItems.LoanElement>()
				: globalLoansList;
	}

	/**
	 * getting holds list for fragment
	 */
	protected ArrayList<HoldElement> getHoldsList() {
		return (globalHoldsList == null) ? new ArrayList<BookItems.HoldElement>()
				: globalHoldsList;
	}

	/***********************************************************
	 * action bar tabs, viewpager stuff
	 ***********************************************************/

	private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			// mActionBar.setSelectedNavigationItem(position);
		}
	};

	// private void addActionBarTabs() {
	// mActionBar = ((MainActivity)
	// getSherlockActivity()).getSupportActionBar();
	// String[] tabs = { "Loans", "Requests", "Wish List" };
	// for (String tabTitle : tabs) {
	// ActionBar.Tab tab = mActionBar.newTab().setText(tabTitle)
	// .setTabListener(tabListener);
	// mActionBar.addTab(tab);
	// }
	// mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	// }
	//
	// private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	// @Override
	// public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	// mViewPager.setCurrentItem(tab.getPosition());
	// }
	//
	// @Override
	// public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	// }
	//
	// @Override
	// public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	// }
	// };

	private class ViewPagerAdapter extends FragmentStatePagerAdapter implements
			IconPagerAdapter {

		private final int PAGES = 3;

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			// TODO: read fragment in develope.android, new instance all the
			// time?
			case 0:
				if (mLoansFragment == null) {
					mLoansFragment = new LoansFragment();
				}
				return mLoansFragment;
			case 1:
				if (mHoldsFragment == null) {
					mHoldsFragment = new HoldsFragment();
				}
				return mHoldsFragment;
			case 2:
				if (mWishListFragment == null) {

					mWishListFragment = new WishListFragment();
				}
				return mWishListFragment;
			default:
				throw new IllegalArgumentException(
						"The item position should be less or equal to:" + PAGES);
			}
		}

		@Override
		public int getCount() {
			return PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String title = "";
			switch (position) {
			case 0:
				title = "Loans";
				break;
			case 1:
				title = "Requests";
				break;
			case 2:
				title = "Wish List";
				break;
			}
			return title;
		}

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	/***********************************************************
	 * END OF action bar tabs, viewpager stuff
	 ***********************************************************/
	
	private void toastConnectionError() {
		Toast toast = Toast.makeText(getSherlockActivity(), "Connection error, try again later.",
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSherlockActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
