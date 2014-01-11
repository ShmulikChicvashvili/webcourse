package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.technion.coolie.R;

public class CoursesAndExamsFragment extends Fragment {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(
				R.layout.ug_courses_and_exams_fragment, container, false);

		mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) view.findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(2);
		mTabsAdapter = new TabsAdapter(getActivity(), mTabHost, mViewPager);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("one").setIndicator(
						getString(R.string.previous_semester)),
				PageOneFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("two").setIndicator(
						getString(R.string.curren_semester)),
				PageTwoFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("three").setIndicator(
						getString(R.string.next_semester)),
				PageThreeFragment.class, null);

		if (savedInstanceState != null)
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		mTabHost.setCurrentTab(1);
		return view;
	}

	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentStatePagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(final Class<?> _class, final Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(final Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(final String tag) {
				final View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(final FragmentActivity activity,
				final TabHost tabHost, final ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(final TabHost.TabSpec tabSpec, final Class<?> clss,
				final Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			final TabInfo info = new TabInfo(clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(final int position) {
			final TabInfo info = mTabs.get(position);

			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);

		}

		@Override
		public void onTabChanged(final String tabId) {
			final int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(final int position,
				final float positionOffset, final int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(final int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			final TabWidget widget = mTabHost.getTabWidget();
			final int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(final int state) {
		}
	}

}
