package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.CoolieNotification;
import com.technion.coolie.R;
import com.technion.coolie.server.gcm.GcmFactory;

@SuppressLint("ValidFragment")
public class MainActivity extends CoolieActivity {

  // // gcm vars

  private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

  // // end of gcm vars

  private GridView mostUsedGrid;
  private ViewPager mViewPager;
  int selectedTabIndex = 0; // used in onResume to restore the selected tab in
  // orientation change
  private static String KEY_VIEWPAGER_SAVE_STATE = "VIEW_PAGER_SELECTED_TAB";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.skel_activity_main);

    // gcm check (and registration if necessary)

    GcmFactory.getGcmAPI().registerDevice(getApplicationContext(),
        checkPlayServices());

    // end of gcm check

    /*
     * SignonDialog s = new SignonDialog(CoolieAccount.UG);
     * s.show(getSupportFragmentManager(), "dialog");
     */

    if (savedInstanceState == null) {
      // this means that its the first time we run the app
      // so its ok to display these demo notifications..
      CoolieNotification c1;
      try {
        c1 = new CoolieNotification("Demo Notification 1",
            "This notification simulates Tech Library notification.",
            (Activity) CoolieModule.TECHLIBRARY.getActivity().newInstance(),
            CoolieNotification.Priority.IMMEDIATELY, true, this);
        CoolieNotification c2 = new CoolieNotification("Demo Notification 2",
            "This notification simulates StudyBuddy notification.",
            (Activity) CoolieModule.STUDYBUDDY.getActivity().newInstance(),
            CoolieNotification.Priority.IN_A_DAY, true, this);
        c1.sendNotification();
        c2.sendNotification();
      } catch (InstantiationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    mViewPager = (ViewPager) findViewById(R.id.skel_main_view_pager);

    ViewPagerAdapter mDemoCollectionPagerAdapter = new ViewPagerAdapter(
        getSupportFragmentManager(), MainActivity.this, getFragments());
    mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    mViewPager
        .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
          @Override
          public void onPageSelected(int position) {
            // When swiping between pages, select the
            // corresponding tab.
            getSupportActionBar().setSelectedNavigationItem(position);
          }
        });

    if (savedInstanceState != null)
      selectedTabIndex = savedInstanceState.getInt(KEY_VIEWPAGER_SAVE_STATE);

  }

  @Override
  protected void onResume() {
    if (mostUsedGrid == null)
      addTabsToActionbar(mViewPager);
    else
      ((MostUsedAdapter) mostUsedGrid.getAdapter()).sortAgain();
    super.onResume();

    // gcm check
    checkPlayServices();
  }

  // /**
  // * Gets the current registration ID for application on GCM service.
  // * <p>
  // * If result is empty, the app needs to register.
  // *
  // * @return registration ID, or empty string if there is no existing
  // * registration ID.
  // */
  // private String getRegistrationId(Context context_) {
  // final SharedPreferences prefs_ = getGCMPreferences(context_);
  // String registrationId = prefs_.getString(PROPERTY_REG_ID, "");
  // if (registrationId.isEmpty()) {
  // Log.i(TAG, "Registration not found.");
  // return "";
  // }
  // // Check if app was updated; if so, it must clear the registration ID
  // // since the existing regID is not guaranteed to work with the new
  // // app version.
  // int registeredVersion = prefs_.getInt(PROPERTY_APP_VERSION,
  // Integer.MIN_VALUE);
  // int currentVersion = getAppVersion(context_);
  // if (registeredVersion != currentVersion) {
  // Log.i(TAG, "App version changed.");
  // return "";
  // }
  // return registrationId;
  // }
  //
  // /**
  // * @return Application's version code from the {@code PackageManager}.
  // */
  // private static int getAppVersion(Context context) {
  // try {
  // PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
  // context.getPackageName(), 0);
  // return packageInfo.versionCode;
  // } catch (NameNotFoundException e) {
  // // should never happen
  // throw new RuntimeException("Could not get package name: " + e);
  // }
  // }
  //
  // /**
  // * @return Application's {@code SharedPreferences}.
  // */
  // private SharedPreferences getGCMPreferences(Context context) {
  // // This sample app persists the registration ID in shared preferences, but
  // // how you store the regID in your app is up to you.
  // return context.getSharedPreferences("gcm regid", Context.MODE_PRIVATE);
  // }
  //
  // /**
  // * Check the device to make sure it has the Google Play Services APK. If it
  // * doesn't, display a dialog that allows users to download the APK from the
  // * Google Play Store or enable it in the device's system settings.
  // */
  // private boolean checkPlayServices() {
  // int resultCode =
  // GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
  // if (resultCode != ConnectionResult.SUCCESS) {
  // if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
  // GooglePlayServicesUtil.getErrorDialog(resultCode, this,
  // PLAY_SERVICES_RESOLUTION_REQUEST).show();
  // } else {
  // Log.i(TAG, "This device is not supported.");
  // Toast.makeText(context, "This device is not supported.",
  // Toast.LENGTH_SHORT).show();
  // finish();
  // }
  // return false;
  // }
  // return true;
  // }
  //
  // /**
  // * Registers the application with GCM servers asynchronously.
  // * <p>
  // * Stores the registration ID and app versionCode in the application's
  // shared
  // * preferences.
  // */
  // private void registerInBackground() {
  // new AsyncTask<Void, Void, String>() {
  // @Override
  // protected String doInBackground(Void... params) {
  // String msg = "";
  // try {
  // if (gcm == null) {
  // gcm = GoogleCloudMessaging.getInstance(context);
  // }
  // regid = gcm.register(SENDER_ID);
  // msg = "Device registered, registration ID=" + regid;
  //
  // // You should send the registration ID to your server over HTTP,
  // // so it can use GCM/HTTP or CCS to send messages to your app.
  // // The request to your server should be authenticated if your app
  // // is using accounts.
  // sendRegistrationIdToBackend();
  //
  // // For this demo: we don't need to send it because the device
  // // will send upstream messages to a server that echo back the
  // // message using the 'from' address in the message.
  //
  // // Persist the regID - no need to register again.
  // storeRegistrationId(context, regid);
  // } catch (IOException ex) {
  // msg = "Error: " + ex.getMessage();
  // // If there is an error, don't just keep trying to register.
  // // Require the user to click a button again, or perform
  // // exponential back-off.
  // }
  // return msg;
  // }
  //
  // @Override
  // protected void onPostExecute(String msg) {
  // Log.i(TAG, msg + "\n");
  // }
  // }.execute(null, null, null);
  //
  // }
  //
  // /**
  // * Sends the registration ID to your server over HTTP, so it can use
  // GCM/HTTP
  // * or CCS to send messages to your app. Not needed for this demo since the
  // * device sends upstream messages to a server that echoes back the message
  // * using the 'from' address in the message.
  // */
  // void sendRegistrationIdToBackend() {
  // Log.i(TAG, "sendRegistrationIdToBackend: " + regid);
  // GcmFactory.getGcmAPI().registerDevice(regid, "shalomShalom");
  // }
  //
  // /**
  // * Stores the registration ID and app versionCode in the application's
  // * {@code SharedPreferences}.
  // *
  // * @param context_
  // * application's context.
  // * @param regId
  // * registration ID
  // */
  // void storeRegistrationId(Context context_, String regId) {
  // final SharedPreferences prefs_ = getGCMPreferences(context_);
  // int appVersion = getAppVersion(context_);
  // Log.i(TAG, "Saving regId: " + regId);
  // Log.i(TAG, "Saving regId on app version " + appVersion);
  // SharedPreferences.Editor editor = prefs_.edit();
  // editor.putString(PROPERTY_REG_ID, regId);
  // editor.putInt(PROPERTY_APP_VERSION, appVersion);
  // editor.commit();
  // }

  private List<Fragment> getFragments() {
    List<Fragment> fList = new ArrayList<Fragment>();
    fList.add(new AlphabeticalModulesFragment());
    fList.add(new MostUsedModulesFragment());
    fList.add(new FeedsFragment());

    return fList;
  }

  private void addTabsToActionbar(final ViewPager mViewPager) {
    final ActionBar actionBar = getSupportActionBar();

    // Specify that tabs should be displayed in the action bar.
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    // Create a tab listener that is called when the user changes tabs.
    ActionBar.TabListener tabListener = new ActionBar.TabListener() {

      @Override
      public void onTabSelected(Tab tab, FragmentTransaction ft) {

        getSupportActionBar().setSelectedNavigationItem(tab.getPosition());
        mViewPager.setCurrentItem(tab.getPosition(), true);
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

    actionBar.addTab(actionBar.newTab()
        .setText(R.string.skel_tab_title_alphabetical)
        .setTabListener(tabListener));
    actionBar
        .addTab(actionBar.newTab().setText(R.string.skel_tab_title_most_used)
            .setTabListener(tabListener));
    actionBar.addTab(actionBar.newTab().setText(R.string.skel_tab_title_feeds)
        .setTabListener(tabListener));

    actionBar.setSelectedNavigationItem(selectedTabIndex);

  }

  private class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, Context c,
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

  private class AlphabeticalModulesAdapter extends MainScreenModulesAdapter {

    public AlphabeticalModulesAdapter(Context c) {
      super(c);
    }

    @SuppressLint("ValidFragment")
    @Override
    int compareModules(CoolieModule m1, CoolieModule m2) {
      return m1.getName(mContext).compareTo(m2.getName(mContext));
    }

  }

  private class AlphabeticalModulesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.skel_main_modules_grid, container,
          false);
      GridView gridview = (GridView) view
          .findViewById(R.id.skel_main_modules_grid);
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
      if (m1.getUsageCounter() < m2.getUsageCounter())
        return 1;
      return -1;
    }
  }

  private class MostUsedModulesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.skel_main_modules_grid, container,
          false);
      mostUsedGrid = (GridView) view.findViewById(R.id.skel_main_modules_grid);
      mostUsedGrid.setAdapter(new MostUsedAdapter(getActivity()));

      return view;
    }
  }

  @SuppressLint("ValidFragment")
  private class FeedsFragment extends Fragment {
    FeedsAdapter adp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View view = null;
      if (adp == null) {
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

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    if (((ViewPager) findViewById(R.id.skel_main_view_pager)) != null)
      savedInstanceState.putInt(KEY_VIEWPAGER_SAVE_STATE,
          ((ViewPager) findViewById(R.id.skel_main_view_pager))
              .getCurrentItem());
    super.onSaveInstanceState(savedInstanceState);
  }

  /**
   * Check the device to make sure it has the Google Play Services APK. If it
   * doesn't, display a dialog that allows users to download the APK from the
   * Google Play Store or enable it in the device's system settings.
   */
  public boolean checkPlayServices() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS) {
      if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
        GooglePlayServicesUtil.getErrorDialog(resultCode, this,
            PLAY_SERVICES_RESOLUTION_REQUEST).show();
      } else {
        Log.i("Gcm", "This device is not supported.");
        Toast.makeText(getApplicationContext(),
            "This device is not supported.", Toast.LENGTH_SHORT).show();
        finish();
      }
      return false;
    }
    return true;
  }

}
