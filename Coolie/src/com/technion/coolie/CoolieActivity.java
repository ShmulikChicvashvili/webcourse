package com.technion.coolie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.technion.coolie.skeleton.MyPreferencesScreen;

public abstract class CoolieActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	View mainLayout;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		if (mDrawerToggle.onOptionsItemSelected(item))
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

	@Override
	public View findViewById(final int id) {
		if (mainLayout != null) {
			if (mainLayout.findViewById(id) == null)
				return super.findViewById(id);
			return mainLayout.findViewById(id);
		}
		return null;
	}

	private boolean handleActionBar(final Menu menu) {
		final MenuItem settings = menu.add("Settings");
		settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				final Intent intent = new Intent(CoolieActivity.this, MyPreferencesScreen.class);
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

	private void createNavBar() {
		final List<Map<String, String>> data = GetSampleData();
		mDrawerLayout = (DrawerLayout) super.findViewById(R.id.skel_drawer_layout);
		mDrawerList = (ListView) super.findViewById(R.id.skel_left_drawer);

		final SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.skel_listitem_row,
				new String[] { "moduleIcon", "moduleName" }, new int[] { R.id.skel_moduleIcon,
						R.id.skel_moduleName });
		mDrawerList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.skel_ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	List<Map<String, String>> GetSampleData() {
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// Map map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module1);
		// map.put("moduleName", "module 1");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module2);
		// map.put("moduleName", "module 2");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module3);
		// map.put("moduleName", "module 3");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module4);
		// map.put("moduleName", "module 4");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module5);
		// map.put("moduleName", "module 5");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module6);
		// map.put("moduleName", "module 6");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module7);
		// map.put("moduleName", "module 7");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module8);
		// map.put("moduleName", "module 8");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module9);
		// map.put("moduleName", "module 9");
		// list.add(map);
		// map = new HashMap();
		// map.put("moduleIcon", R.drawable.skel_module10);
		// map.put("moduleName", "module 10");
		// list.add(map);

		return list;
	}
}
