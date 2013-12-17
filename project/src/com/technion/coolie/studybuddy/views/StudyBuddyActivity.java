package com.technion.coolie.studybuddy.views;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.adapters.NavigationAdapter;
import com.technion.coolie.studybuddy.data.DataStore;

public abstract class StudyBuddyActivity extends CoolieActivity
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.technion.coolie.CoolieActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ExpandableListView view = (ExpandableListView) addInnerNavigationDrawer(R.layout.stb_view_navigation);
		NavigationAdapter navigationAdapter = new NavigationAdapter(this);
		view.setAdapter(navigationAdapter);
		view.expandGroup(1);
		DataStore.initHelper(this);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		DataStore.destroyHelper();
	}

}
