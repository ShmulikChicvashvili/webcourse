package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.CoolieNotification;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {

	private GridView mostUsedGrid;
	private ViewPager mViewPager;
	
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
		CoolieNotification c1;
		try {
			c1 = new CoolieNotification("Demo Notification 1", "This notification simulates Tech Library notification.", (Activity) CoolieModule.TECHLIBRARY.getActivity().newInstance(), CoolieNotification.Priority.IMMEDIATELY, true, this);
			CoolieNotification c2 = new CoolieNotification("Demo Notification 2", "This notification simulates StudyBuddy notification.",(Activity) CoolieModule.STUDYBUDDY.getActivity().newInstance(), CoolieNotification.Priority.IN_A_DAY, true, this);
			c1.sendNotification();
			c2.sendNotification();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mViewPager = (ViewPager) findViewById(R.id.skel_main_view_pager);

		ViewPagerAdapter mDemoCollectionPagerAdapter = new ViewPagerAdapter(
			getSupportFragmentManager(), MainActivity.this, getFragments());
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						getSupportActionBar().setSelectedNavigationItem(
								position);
					}
				});
	}
	
	@Override
	protected void onResume() {
		if(mostUsedGrid==null)
			addTabsToActionbar(mViewPager);
		else
			((MostUsedAdapter)mostUsedGrid.getAdapter()).sortAgain();
		super.onResume();
	}

	
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(new AlphabeticalModulesFragment());
		fList.add(new MostUsedModulesFragment());
		fList.add(new FeedsFragment());

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

	    
	    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_alphabetical).setTabListener(tabListener));
	    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_most_used).setTabListener(tabListener));
	    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_feeds).setTabListener(tabListener));
	}
	

	
	
	
	private class ViewPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> fragments;

		public ViewPagerAdapter(FragmentManager fm, Context c, List<Fragment> fragments) {
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
	
	private class AlphabeticalModulesAdapter extends MainScreenModulesAdapter {

		public AlphabeticalModulesAdapter(Context c) {
			super(c);
		}

		@Override
		int compareModules(CoolieModule m1, CoolieModule m2) {
			return m1.getName(mContext).compareTo(m2.getName(mContext));
		}

	}

	private class AlphabeticalModulesFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
		    View view = inflater.inflate(R.layout.skel_main_modules_grid, container, false);
	        GridView gridview = (GridView) view.findViewById(R.id.skel_main_modules_grid);
			gridview.setAdapter(new AlphabeticalModulesAdapter(getActivity()));

			return view;
		}
		
	}
	
	
	private class MostUsedAdapter extends MainScreenModulesAdapter {

		public MostUsedAdapter(Context c) {
			super(c);
		}

		@Override
		int compareModules(CoolieModule m1, CoolieModule m2) {
			if (m1.getUsageCounter()<m2.getUsageCounter())
				return 1;
			return -1;
		}
	}
	
	private class MostUsedModulesFragment  extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
		    View view = inflater.inflate(R.layout.skel_main_modules_grid, container, false);
	        mostUsedGrid = (GridView) view.findViewById(R.id.skel_main_modules_grid);
	        mostUsedGrid.setAdapter(new MostUsedAdapter(getActivity()));

			return view;
		}
	}
	
	private class FeedsFragment extends Fragment {
		FeedsAdapter adp;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = null;
			if(adp == null)
			{
		    view = inflater.inflate(R.layout.skel_feeds_screen, container, false);
	        ListView listView = (ListView) view.findViewById(R.id.skel_feeds_list);
	        
	        adp = new FeedsAdapter(getActivity());
	        listView.setAdapter(adp);
			}
			
			return view;
		}
		
		@Override
		public void onResume() {
			adp.notifyDataSetChanged();
			super.onResume();
		}
	}

}
