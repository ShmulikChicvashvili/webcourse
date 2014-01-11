package com.technion.coolie.joinin.subactivities;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.technion.coolie.FBClientAccount;
import com.technion.coolie.R;
import com.technion.coolie.joinin.gui.WrapperView;
import com.technion.coolie.joinin.map.MainMapActivity;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class MyEventsActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
  public static final String INTENT_TAB_POS = "tab_pos";
  public static final int TAB_OWNED = 0;
  public static final int TAB_ATTENDING = 1;
  private WrapperView mWrapper;
  private TabHost mTabHost;
  private ViewPager mViewPager;
  private PagerAdapter mPagerAdapter;
  private FBClientAccount mAccount;
  
  /**
   * the logged account
   * 
   * @return
   */
  public FBClientAccount getAccount() {
    return mAccount;
  }
  
  @Override protected void onCreate(final android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View myEventsView = inflater.inflate(R.layout.ji_event, null);
    mWrapper = new WrapperView(this, myEventsView);
    setContentView(mWrapper);
    initializeTabHost();
    intializeViewPager();
    mAccount = (FBClientAccount) getIntent().getExtras().get("account");
    mViewPager.setCurrentItem(getIntent().getExtras().getInt(INTENT_TAB_POS));
  }
  
  /**
   * Initialize the Tab Host
   */
  private void initializeTabHost() {
    mTabHost = (TabHost) findViewById(android.R.id.tabhost);
    mTabHost.setup();
    final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View tab = inflater.inflate(R.layout.ji_my_events_tab_layout, null);
    ((TextView) tab.findViewById(R.id.textView1)).setText("My Events");
    MyEventsActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab1").setIndicator(tab));
    mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#00000000"));
    tab = inflater.inflate(R.layout.ji_my_events_tab_layout, null);
    ((TextView) tab.findViewById(R.id.textView1)).setText("I'm Attending");
    MyEventsActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab2").setIndicator(tab));
    mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#00000000"));
    mTabHost.getTabWidget().setStripEnabled(true);
    mTabHost.setOnTabChangedListener(this);
  }
  
  private void intializeViewPager() {
    final List<Fragment> fragments = new Vector<Fragment>();
    fragments.add(Fragment.instantiate(this, MyEventsOwnedFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, MyEventsAttendingFragment.class.getName()));
    mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
    mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
    mViewPager.setOffscreenPageLimit(2);
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.setOnPageChangeListener(this);
  }
  
  private static void AddTab(final MyEventsActivity activity, final TabHost tabHost, final TabHost.TabSpec tabSpec) {
    tabSpec.setContent(activity.new TabFactory(activity));
    tabHost.addTab(tabSpec);
  }
  
  @Override public void onTabChanged(final String tag) {
    mViewPager.setCurrentItem(mTabHost.getCurrentTab());
  }
  
  @Override public void onPageSelected(final int position) {
    mTabHost.setCurrentTab(position);
  }
  
  /**
   * A simple factory that returns dummy views to the Tabhost
   * 
   */
  class TabFactory implements TabContentFactory {
    private final Context mContext;
    
    /**
     * @param context
     */
    public TabFactory(final Context context) {
      mContext = context;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
     */
    @Override public View createTabContent(final String tag) {
      final View v = new View(mContext);
      v.setMinimumWidth(0);
      v.setMinimumHeight(0);
      return v;
    }
  }
  
  @Override public void onPageScrollStateChanged(final int arg0) {
    // Comes with the interface...
  }
  
  @Override public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
    // Comes with the interface...
  }
  
  /**
   * Handles the back key for the activity.
   */
  @Override public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      setResult(MainMapActivity.RESULT_REFRESH);
      finish();
    }
    return super.onKeyDown(keyCode, event);
  }
  
  @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (resultCode) {
      case MainMapActivity.RESULT_REFRESH:
        ((OnFragmentRefresh) mPagerAdapter.getItem(TAB_OWNED)).onRefresh();
        ((OnFragmentRefresh) mPagerAdapter.getItem(TAB_ATTENDING)).onRefresh();
        break;
      case MainMapActivity.RESULT_REFRESH_DIRECTIONS:
        setResult(MainMapActivity.RESULT_REFRESH_DIRECTIONS, data);
        finish();
        break;
      default:
        break;
    }
  }
}
