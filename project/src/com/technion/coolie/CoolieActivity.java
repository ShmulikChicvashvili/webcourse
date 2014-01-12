package com.technion.coolie;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStub;
import android.view.ViewStub.OnInflateListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.skeleton.CoolieModule;
import com.technion.coolie.skeleton.CoolieNotificationManager;
import com.technion.coolie.skeleton.NavigationModuleAdapter;
import com.technion.coolie.skeleton.PreferencesScreen;

public abstract class CoolieActivity extends SherlockFragmentActivity {
	public static boolean navbarIsOpen = false;
	
	protected DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerView;
	private ListView mDrawerModulesList;
	ActionBarDrawerToggle mDrawerToggle;
	View mainLayout;
	View innerNavBar;
	int mainLayoutRootId;
	static boolean serilizeRestored = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!serilizeRestored) {
			restoreModulesManager();
			serilizeRestored = true;
		}

		// Always add overflow button
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.setContentView(R.layout.skel_navigation_drawer);
		createNavBar();

	}

	@Override
	protected void onResume() {
		// if(!this.getClass().getPackage().getName().contains("skel"))
		// CoolieModuleManager.getMyModule(this.getClass()).setLastUsage();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return handleActionBar(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event

	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	if (!this.getClass().equals(com.technion.coolie.skeleton.MainActivity.class))
	    	{
	    		
	    		NavUtils.navigateUpFromSameTask(this);
	    	}
	    	else
	    	{
	    		if(navbarIsOpen==false)
	    		{
	    			mDrawerLayout.openDrawer(Gravity.LEFT);
	    		}
	    		else
	    		{
	    			mDrawerLayout.closeDrawers();
	    		}
	    	}
	        return true;
	    }

		
		if (mDrawerToggle.onOptionsItemSelected(getMenuItem(item))) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setContentView(int layoutResID) {
		
		final ViewStub vs = (ViewStub) super.findViewById(R.id.skel_layout_container);
		vs.setLayoutResource(layoutResID);
		vs.setInflatedId(ViewStub.NO_ID);
		vs.setOnInflateListener(new OnInflateListener() {
			
			@Override
			public void onInflate(ViewStub stub, View inflated) {
				mainLayoutRootId = inflated.getId();
			}
		});
		
		mainLayout = vs.inflate();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	}

	// Searches for the view in child views context and main context.
	@Override
	public View findViewById(int id) {
		if (id == mainLayoutRootId) {
			return mainLayout;
		}
		if (mainLayout != null) {
			if (mainLayout.findViewById(id) == null)
				return super.findViewById(id);
			return mainLayout.findViewById(id);
		}
		View v = null;
		if (mainLayout != null)
			v = mainLayout.findViewById(id);
		if (v != null)
			return v;
		if (innerNavBar != null) {
			v = innerNavBar.findViewById(id);
			if (v != null)
				return v;
		}
		return super.findViewById(id);
	}

	// Adds "About" and "Settings" buttons in the ActionBar
	private boolean handleActionBar(Menu menu) {
		MenuItem settings = menu.add("Settings");
		settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(CoolieActivity.this,
						PreferencesScreen.class);
				startActivity(intent);
				return false;
			}
		});

		MenuItem about = menu.add("About");
		about.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		about.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO open about screen
				return false;
			}
		});
		return true;
	}

	// Creates the NavigationDrawer and sets the modules list content.
	private void createNavBar() {
		int iconRes = R.drawable.abs__ic_ab_back_holo_light;
		if (this.getClass().equals(com.technion.coolie.skeleton.MainActivity.class))
		{
			iconRes = R.drawable.skel_ic_drawer;
		}
		mDrawerLayout = (DrawerLayout) super
				.findViewById(R.id.skel_drawer_layout);
		mDrawerView = (LinearLayout) super.findViewById(R.id.skel_left_drawer);

		mDrawerModulesList = ((ListView) mDrawerView
				.findViewById(R.id.skel_modules_left_list));
		mDrawerModulesList.setAdapter(new NavigationModuleAdapter(this));

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		iconRes, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {
			@Override
			public void onDrawerOpened(View drawerView) {
				navbarIsOpen= true;
				drawerView.requestFocus();
				super.onDrawerOpened(drawerView);
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				navbarIsOpen= false;
				super.onDrawerClosed(drawerView);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	/**
	 * Allow activities to add their own navigation inside the main
	 * NavigationDrawer. The layout will be added only to the activity calls
	 * this method. Different activities can add different navigation layouts. <br>
	 * Pay attention the width of your layout may will be changed.
	 * 
	 * @param layoutResID
	 *            - an id <u>from R.layout class</u> witch you want to add to
	 *            the NavigationDrawer.
	 * @return reference to your inner navigation view.
	 */
	protected View addInnerNavigationDrawer(int layoutResID) {
		ViewStub vs = (ViewStub) super
				.findViewById(R.id.skel_inner_module_nav_container);
		vs.setLayoutResource(layoutResID);
		innerNavBar = vs.inflate();
		return innerNavBar;
	}

	private android.view.MenuItem getMenuItem(final MenuItem item) {
		return new android.view.MenuItem() {
			@Override
			public int getItemId() {
				return item.getItemId();
			}

			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean collapseActionView() {
				return false;
			}

			@Override
			public boolean expandActionView() {
				return false;
			}

			@Override
			public ActionProvider getActionProvider() {
				return null;
			}

			@Override
			public View getActionView() {
				return null;
			}

			@Override
			public char getAlphabeticShortcut() {
				return 0;
			}

			@Override
			public int getGroupId() {
				return 0;
			}

			@Override
			public Drawable getIcon() {
				return null;
			}

			@Override
			public Intent getIntent() {
				return null;
			}

			@Override
			public ContextMenuInfo getMenuInfo() {
				return null;
			}

			@Override
			public char getNumericShortcut() {
				return 0;
			}

			@Override
			public int getOrder() {
				return 0;
			}

			@Override
			public SubMenu getSubMenu() {
				return null;
			}

			@Override
			public CharSequence getTitle() {
				return null;
			}

			@Override
			public CharSequence getTitleCondensed() {
				return null;
			}

			@Override
			public boolean hasSubMenu() {
				return false;
			}

			@Override
			public boolean isActionViewExpanded() {
				return false;
			}

			@Override
			public boolean isCheckable() {
				return false;
			}

			@Override
			public boolean isChecked() {
				return false;
			}

			@Override
			public boolean isVisible() {
				return false;
			}

			@Override
			public android.view.MenuItem setActionProvider(
					ActionProvider actionProvider) {
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(View view) {
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(int resId) {
				return null;
			}

			@Override
			public android.view.MenuItem setAlphabeticShortcut(char alphaChar) {
				return null;
			}

			@Override
			public android.view.MenuItem setCheckable(boolean checkable) {
				return null;
			}

			@Override
			public android.view.MenuItem setChecked(boolean checked) {
				return null;
			}

			@Override
			public android.view.MenuItem setEnabled(boolean enabled) {
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(Drawable icon) {
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(int iconRes) {
				return null;
			}

			@Override
			public android.view.MenuItem setIntent(Intent intent) {
				return null;
			}

			@Override
			public android.view.MenuItem setNumericShortcut(char numericChar) {
				return null;
			}

			@Override
			public android.view.MenuItem setOnActionExpandListener(
					OnActionExpandListener listener) {
				return null;
			}

			@Override
			public android.view.MenuItem setOnMenuItemClickListener(
					OnMenuItemClickListener menuItemClickListener) {
				return null;
			}

			@Override
			public android.view.MenuItem setShortcut(char numericChar,
					char alphaChar) {
				return null;
			}

			@Override
			public void setShowAsAction(int actionEnum) {

			}

			@Override
			public android.view.MenuItem setShowAsActionFlags(int actionEnum) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(CharSequence title) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(int title) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitleCondensed(CharSequence title) {
				return null;
			}

			@Override
			public android.view.MenuItem setVisible(boolean visible) {
				return null;
			}
		};
	}

	/*
	 * used to save all the data relevant for the module, so next run we get
	 * them again and display them. data saved includes name, feeds, usageCount,
	 * ... called in onDestroy.
	 */
	private void serializeModulesManager() {

		Gson gson = new Gson();

		CoolieModule[] c = CoolieModule.values();

		CoolieModule.serializeClass[] serializeArr = new CoolieModule.serializeClass[c.length];

		for (int i = 0; i < c.length; i++) {
			serializeArr[i] = new CoolieModule.serializeClass();
			serializeArr[i].isFavorite = c[i].isFavorite();
			serializeArr[i].activityString = c[i].getActivity().getName();
			serializeArr[i].lastUsed = c[i].getLastUsed();
			serializeArr[i].usageCounter = c[i].getUsageCounter();
		}

		Type type = new TypeToken<CoolieModule.serializeClass[]>() {
		}.getType();

		String json = gson.toJson(serializeArr, type);

		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putString("serilization", json);
		editor.commit();

	}

	/*
	 * used to restore all the data relevant for the module, so we display the
	 * updated data... data includes name, feeds, usageCount, ... called in
	 * onCreate.
	 */

	private void restoreModulesManager() {

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String restoredText = prefs.getString("serilization", null);
		if (restoredText != null) {
			CoolieModule[] c = CoolieModule.values();
			CoolieModule.serializeClass[] serializeArr = new Gson().fromJson(
					restoredText, CoolieModule.serializeClass[].class);
			for (int i = 0; i < serializeArr.length; i++) {
				if (serializeArr[i].isFavorite)
					CoolieModule.valueOf(c[i].name()).setFavorite();

				CoolieModule.valueOf(c[i].name()).setUsageCounter(
						serializeArr[i].usageCounter);
				if (serializeArr[i].lastUsed != null)
					CoolieModule.valueOf(c[i].name()).setLastUsage(
							serializeArr[i].lastUsed);
				if (serializeArr[i].activityString != null) {
					try {
						CoolieModule.valueOf(c[i].name()).setActivity(
								Class.forName(serializeArr[i].activityString));
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	@Override
	protected void onPause() {
		serializeModulesManager();
		super.onPause();
	}
	
	protected void checkIfStartFromNotification() {
		if(getIntent().getBooleanExtra(CoolieNotificationManager.CALLED_BY_SINGLE_NOTIFICATION, false))
		{
			CoolieNotificationManager.removeFromFeedList(
					getIntent().getIntExtra(CoolieNotificationManager.CALLER_SINGLE_NOTIFICATION_ID, -1));
		}
	}
}
