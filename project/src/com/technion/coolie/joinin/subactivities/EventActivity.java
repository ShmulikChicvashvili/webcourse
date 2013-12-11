package com.technion.coolie.joinin.subactivities;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.OnTabRefresh;
import com.technion.coolie.joinin.gui.WrapperView;
import com.technion.coolie.joinin.map.MainMapActivity;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class EventActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener,
    OnTabRefresh {
  /**
   * This parameter should be given in an intent in case the activity should
   * start in a certain tab.
   */
  public static final String INTENT_TAB_POS = "tab_pos";
  /**
   * The position of the event information fragment.
   */
  public static final int TAB_EVENT_INFO = 0;
  /**
   * The position of the event attending list fragment
   */
  public static final int TAB_EVENT_ATTENDING = 1;
  /**
   * The position of the event messaging fragment.
   */
  public static final int TAB_EVENT_MESSAGING = 2;
  private TabHost mTabHost;
  private ViewPager mViewPager;
  private PagerAdapter mPagerAdapter;
  protected static final int EDITED = 69;
  Context thisOne = this;
  ClientEvent mEvent;
  ClientAccount mAccount;
  WrapperView mWrapper;
  
  @Override protected void onCreate(final android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mWrapper = new WrapperView(this, inflater.inflate(R.layout.ji_event, null));
    setContentView(mWrapper);
    mEvent = (ClientEvent) getIntent().getExtras().get("event");
    mAccount = (ClientAccount) getIntent().getExtras().get("account");
    final long eventId = getIntent().getExtras().getLong("eventId", -1);
    if (eventId > 0) {
      final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
      pb.setVisibility(View.VISIBLE);
      ClientProxy.getEventById(eventId, new OnDone<ClientEvent>() {
        @Override public void onDone(final ClientEvent e) {
          pb.setVisibility(View.GONE);
          mEvent = e;
          showTabs();
        }
      }, new OnError(this) {
        @Override public void beforeHandlingError() {
          pb.setVisibility(View.GONE);
        }
      });
    } else
      showTabs();
  }
  
  void showTabs() {
    initializeTabHost();
    intializeViewPager();
    mViewPager.setCurrentItem(getIntent().getExtras().getInt(INTENT_TAB_POS));
  }
  
  @Override protected void onNewIntent(final Intent intent) {
    mEvent = (ClientEvent) intent.getExtras().get("event");
    onRefresh(TAB_EVENT_INFO);
    onRefresh(TAB_EVENT_ATTENDING);
    onRefresh(TAB_EVENT_MESSAGING);
    mViewPager.setCurrentItem(intent.getExtras().getInt(INTENT_TAB_POS));
  }
  
  private void initializeTabHost() {
    mTabHost = (TabHost) findViewById(android.R.id.tabhost);
    mTabHost.setup();
    final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View tab = inflater.inflate(R.layout.ji_tab_layout, null);
    ((ImageView) tab.findViewById(R.id.tabImage)).setImageDrawable(getResources().getDrawable(R.drawable.ji_tab_info));
    EventActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab1").setIndicator(tab));
    mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.ji_tab_bg);
    tab = inflater.inflate(R.layout.ji_tab_layout, null);
    ((ImageView) tab.findViewById(R.id.tabImage)).setImageDrawable(getResources().getDrawable(R.drawable.ji_tab_friends));
    EventActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab2").setIndicator(tab));
    mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.ji_tab_bg);
    tab = inflater.inflate(R.layout.ji_tab_layout, null);
    ((ImageView) tab.findViewById(R.id.tabImage)).setImageDrawable(getResources().getDrawable(R.drawable.ji_tab_message));
    EventActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab3").setIndicator(tab));
    mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.ji_tab_bg);
    mTabHost.getTabWidget().setStripEnabled(true);
    mTabHost.setOnTabChangedListener(this);
  }
  
  private static void AddTab(final EventActivity activity, final TabHost tabHost, final TabHost.TabSpec tabSpec) {
    tabSpec.setContent(activity.new TabFactory(activity));
    tabHost.addTab(tabSpec);
  }
  
  @Override public void onTabChanged(final String tag) {
    mViewPager.setCurrentItem(mTabHost.getCurrentTab());
  }
  
  @Override public void onPageSelected(final int p) {
    mTabHost.setCurrentTab(p);
  }
  
  private void intializeViewPager() {
    final List<Fragment> fragments = new Vector<Fragment>();
    fragments.add(Fragment.instantiate(this, EventInfoFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, EventAttendFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, EventMessagesFragment.class.getName()));
    mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
    mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
    mViewPager.setOffscreenPageLimit(2);
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.setOnPageChangeListener(this);
  }
  
  @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != MainMapActivity.RESULT_REFRESH)
      return;
    mEvent = (ClientEvent) data.getExtras().get("event");
    onRefresh(TAB_EVENT_INFO);
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
      final View $ = new View(mContext);
      $.setMinimumWidth(0);
      $.setMinimumHeight(0);
      return $;
    }
  }
  
  @Override public void onPageScrollStateChanged(final int arg0) {
    // Comes with the interface...
  }
  
  @Override public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
    // Comes with the interface...
  }
  
  public ClientEvent getEvent() {
    return mEvent;
  }
  
  public ClientAccount getAccount() {
    return mAccount;
  }
  
  public void setEvent(final ClientEvent e) {
    mEvent = e;
  }
  
  @Override public void onRefresh(final int pos) {
    ((OnFragmentRefresh) mPagerAdapter.getItem(pos)).onRefresh();
  }
}
