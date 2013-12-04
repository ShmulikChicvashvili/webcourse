package com.technion.coolie;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.skeleton.PreferencesScreen;

public abstract class CoolieActivity extends SherlockFragmentActivity {
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerView;
	private ListView mDrawerModulesList;
	ActionBarDrawerToggle mDrawerToggle;
	View mainLayout;
	View innerNavBar;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			final ViewConfiguration config = ViewConfiguration.get(this);
			final Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		super.setContentView(R.layout.skel_navigation_drawer);
		createNavBar();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		return handleActionBar(menu);
	}

	@Override
	protected void onPostCreate(final Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(final Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event

		if (mDrawerToggle.onOptionsItemSelected(getMenuItem(item)))
			return true;

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setContentView(final int layoutResID) {
		final ViewStub vs = (ViewStub) super.findViewById(R.id.skel_layout_container);
		vs.setLayoutResource(layoutResID);
		mainLayout = vs.inflate();
	}

	@Override
	public void onSaveInstanceState(final Bundle savedInstanceState) {
	}

	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
	}

	// Searches for the view in child views context and main context.
	@Override
	public View findViewById(final int id) {
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
	private boolean handleActionBar(final Menu menu) {
		final MenuItem settings = menu.add("Settings");
		settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				final Intent intent = new Intent(CoolieActivity.this, PreferencesScreen.class);
				startActivity(intent);
				return false;
			}
		});

		final MenuItem about = menu.add("About");
		about.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		about.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				// TODO open about screen
				return false;
			}
		});
		return true;
	}

	// Creates the NavigationDrawer and sets the modules list content.
	private void createNavBar() {
		final List<Map<String, String>> data = GetSampleData();
		mDrawerLayout = (DrawerLayout) super.findViewById(R.id.skel_drawer_layout);
		mDrawerView = (LinearLayout) super.findViewById(R.id.skel_left_drawer);

		final SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.skel_listitem_row,
				new String[] { "moduleIcon", "moduleName" }, new int[] { R.id.skel_moduleIcon,
						R.id.skel_moduleName });
		mDrawerModulesList = ((ListView) mDrawerView.findViewById(R.id.skel_modules_left_list));
		mDrawerModulesList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.skel_ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {
			@Override
			public void onDrawerOpened(final View drawerView) {
				drawerView.requestFocus();
				super.onDrawerOpened(drawerView);
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
	protected View addInnerNavigationDrawer(final int layoutResID) {
		final ViewStub vs = (ViewStub) super.findViewById(R.id.skel_inner_module_nav_container);
		vs.setLayoutResource(layoutResID);
		innerNavBar = vs.inflate();
		return innerNavBar;
	}

	List<Map<String, String>> GetSampleData() {
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module1));
		map.put("moduleName", "module 1");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module2));
		map.put("moduleName", "module 2");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module3));
		map.put("moduleName", "module 3");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module4));
		map.put("moduleName", "module 4");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module5));
		map.put("moduleName", "module 5");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module6));
		map.put("moduleName", "module 6");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module7));
		map.put("moduleName", "module 7");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module8));
		map.put("moduleName", "module 8");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module9));
		map.put("moduleName", "module 9");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("moduleIcon", String.valueOf(R.drawable.skel_module10));
		map.put("moduleName", "module 10");
		list.add(map);

		return list;
	}

	private android.view.MenuItem getMenuItem(final MenuItem item) {
		return new android.view.MenuItem() {
			@Override
			public int getItemId() {
				return item.getItemId();
			}

			@Override
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
			public android.view.MenuItem setActionProvider(final ActionProvider actionProvider) {
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(final View view) {
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(final int resId) {
				return null;
			}

			@Override
			public android.view.MenuItem setAlphabeticShortcut(final char alphaChar) {
				return null;
			}

			@Override
			public android.view.MenuItem setCheckable(final boolean checkable) {
				return null;
			}

			@Override
			public android.view.MenuItem setChecked(final boolean checked) {
				return null;
			}

			@Override
			public android.view.MenuItem setEnabled(final boolean enabled) {
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(final Drawable icon) {
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(final int iconRes) {
				return null;
			}

			@Override
			public android.view.MenuItem setIntent(final Intent intent) {
				return null;
			}

			@Override
			public android.view.MenuItem setNumericShortcut(final char numericChar) {
				return null;
			}

			@Override
			public android.view.MenuItem setOnActionExpandListener(
					final OnActionExpandListener listener) {
				return null;
			}

			@Override
			public android.view.MenuItem setOnMenuItemClickListener(
					final OnMenuItemClickListener menuItemClickListener) {
				return null;
			}

			@Override
			public android.view.MenuItem setShortcut(final char numericChar, final char alphaChar) {
				return null;
			}

			@Override
			public void setShowAsAction(final int actionEnum) {

			}

			@Override
			public android.view.MenuItem setShowAsActionFlags(final int actionEnum) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(final CharSequence title) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(final int title) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitleCondensed(final CharSequence title) {
				return null;
			}

			@Override
			public android.view.MenuItem setVisible(final boolean visible) {
				return null;
			}
		};
	}
}
