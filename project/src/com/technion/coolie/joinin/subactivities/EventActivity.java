package com.technion.coolie.joinin.subactivities;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.joinin.MainActivity;
import com.technion.coolie.joinin.calander.CalendarHandler;
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
public class EventActivity extends CoolieActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener,
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
  
  MenuItem mItemEdit;
  MenuItem mItemDiscard;
  MenuItem mItemRefresh;
  MenuItem mItemSerch;
  MenuItem mItemConnect;
  
  @Override public boolean onCreateOptionsMenu(final Menu menu) {
	  super.onCreateOptionsMenu(menu); 
	  if(mViewPager.getCurrentItem()== TAB_EVENT_INFO){
		  
		  if(mEvent.getOwner().equals(mAccount.getUsername())){
			 
			  mItemEdit = menu.add("Edit");
			  mItemEdit.setIcon(R.drawable.ji_content_edit);
			  mItemEdit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			  
			  mItemDiscard = menu.add("Discard");
			  mItemDiscard.setIcon(R.drawable.ji_content_discard);
			  mItemDiscard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		  }
		  
	  }else if (mViewPager.getCurrentItem()== TAB_EVENT_ATTENDING){
		 
		  mItemRefresh = menu.add("Refresh");
		  mItemRefresh.setIcon(R.drawable.ji_refresh);
		  mItemRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		  
		  mItemSerch = menu.add("Serch");
		  mItemSerch.setIcon(R.drawable.ji_search_white);
		  mItemSerch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		  
		  mItemConnect = menu.add("Connect");
		  mItemConnect.setIcon(R.drawable.ji_social_share);
		  mItemConnect.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		  
	  }else if ((mViewPager.getCurrentItem()== TAB_EVENT_MESSAGING)){
		  // currently no menu items
	  }

	  return true;
  }

  // check if "edit" was successful
  @Override 
  protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	  if(requestCode == EDITED && resultCode == MainActivity.RESULT_EDIT_EVENT){
		  setResult(MainActivity.RESULT_EDIT_EVENT , 
				  new Intent().putExtra("event", data.getExtras().getParcelable("event")));		  
	  }
  }
  
  
  @Override public boolean onOptionsItemSelected(final MenuItem item) {

	   if(item == mItemEdit) {
		   Intent intent = new Intent(thisOne, CreateEventActivity.class);
		   intent.putExtra("account", getAccount());
		   intent.putExtra("event", new ClientEvent(getEvent()));
		   startActivityForResult(intent, EDITED);
		   // not here
		   //setResult(MainActivity.RESULT_EDIT_EVENT , new Intent().putExtra("event", getEvent()));
 
	   } else if (item == mItemDiscard){
		   
		    //showProgressBar(eventO2);
		    ClientProxy.deleteEvent(getEvent().getId(), new OnDone<Void>() {
		      @Override public void onDone(final Void t) {
		        //hideProgressBar(eventO2);
		    	  setResult(MainActivity.RESULT_REMOVE_EVENT , new Intent().putExtra("event", getEvent()));

		    	  new CalendarHandler(thisOne).deleteEvent(thisOne, getEvent());
		        //((Activity)thisOne).setResult(MainMapActivity.RESULT_DELETE, new Intent().putExtra("event", getEvent()));
		        ((Activity)thisOne).finish();
		      }
		    }, new OnError(((Activity)thisOne)) {
		      @Override public void beforeHandlingError() {
		        //hideProgressBar(eventO2);
		      }
		    });
		   
	   }else if (item == mItemRefresh){
		   
	   }else if (item == mItemSerch){
		   
	   }else if (item == mItemConnect){
		   
	   }else{ return false; }
	
	   return true;
  }
  
  
 //  @Override protected void invalidate
  
  
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

    tab = inflater.inflate(R.layout.ji_tab_layout, null);
    ((ImageView) tab.findViewById(R.id.tabImage)).setImageDrawable(getResources().getDrawable(R.drawable.ji_tab_friends));
    EventActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab2").setIndicator(tab));

    tab = inflater.inflate(R.layout.ji_tab_layout, null);
    ((ImageView) tab.findViewById(R.id.tabImage)).setImageDrawable(getResources().getDrawable(R.drawable.ji_tab_message));
    EventActivity.AddTab(this, mTabHost, mTabHost.newTabSpec("Tab3").setIndicator(tab));

    mTabHost.getTabWidget().setStripEnabled(true);
    mTabHost.setOnTabChangedListener(this);
  }
  
  private static void AddTab(final EventActivity activity, final TabHost tabHost, final TabHost.TabSpec tabSpec) {
    tabSpec.setContent(activity.new TabFactory(activity));
    tabHost.addTab(tabSpec);
  }
  
  @Override public void onTabChanged(final String tag) {
    mViewPager.setCurrentItem(mTabHost.getCurrentTab());
    invalidateOptionsMenu();
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
  
  // older TeamApp version - won't work
//  @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    if (resultCode != MainMapActivity.RESULT_REFRESH)
//      return;
//    mEvent = (ClientEvent) data.getExtras().get("event");
//    onRefresh(TAB_EVENT_INFO);
//  }
  
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
