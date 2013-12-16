package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skel_activity_main);

		/*HtmlRequestHandler hg = new HtmlRequestHandler(getApplicationContext())
		{
			@Override
			public void handleResult(String result, CoolieStatus status) {
				Log.v("RESULT",result);			
			}
		};
		hg.getHtmlSource("http://techmvs.technion.ac.il:80/cics/wmn/wmngrad?ORD=1", HtmlRequestHandler.Account.NONE);
*/
		
		ViewPager mViewPager = (ViewPager) findViewById(R.id.skel_main_view_pager);

		viewPagerAdapter mDemoCollectionPagerAdapter = new viewPagerAdapter(
				getSupportFragmentManager(), MainActivity.this, getFragments());

		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

		addTabsToActionbar(mViewPager);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						getSupportActionBar().setSelectedNavigationItem(
								position);
					}
				});
	}

	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(new AlphabeticalModulesFragment());
		fList.add(new RecentlyUsedModulesFragment());

		return fList;
	}
	

	private void addTabsToActionbar(final ViewPager mViewPager){
	    final ActionBar actionBar = getSupportActionBar();

	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				
                getSupportActionBar().setSelectedNavigationItem(tab.getPosition());
	            mViewPager.setCurrentItem(tab.getPosition(),true);
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
	    };

	   /* // Add 3 tabs, specifying the tab's text and TabListener
	    for (int i = 0; i < 2; i++) {
	        actionBar.addTab(
	                actionBar.newTab()
	                        .setText("Tab " + (i + 1))
	                        .setTabListener(tabListener));
	    }*/
	    
	    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_alphabetical).setTabListener(tabListener));
	    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_recently_used).setTabListener(tabListener));
	    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_feeds).setTabListener(tabListener));



	}
	
	
	private class viewPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> fragments;

		public viewPagerAdapter(FragmentManager fm, Context c,
				List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}


		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	
	public static class AlphabeticalModulesFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
		    View view = inflater.inflate(R.layout.skel_main_modules_grid, container, false);
	        GridView gridview = (GridView) view.findViewById(R.id.skel_main_modules_grid);
			gridview.setAdapter(new AlphabeticalModulesAdapter(getActivity()));

			return view;
		}
		
	}
	
	public static class RecentlyUsedModulesFragment  extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
		    View view = inflater.inflate(R.layout.skel_main_modules_grid, container, false);
	        GridView gridview = (GridView) view.findViewById(R.id.skel_main_modules_grid);
			gridview.setAdapter(new RecentlyUsedAdapter(getActivity()));

			return view;
		}
	}

}
