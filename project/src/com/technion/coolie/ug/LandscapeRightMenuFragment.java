package com.technion.coolie.ug;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.technion.coolie.R;
import com.technion.coolie.ug.Enums.LandscapeLeftMenuItems;

public class LandscapeRightMenuFragment extends ListFragment {

	String[] menuItems;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final Resources res = getResources();
		menuItems = res.getStringArray(R.array.landscape_right_menu_buttons);
		final LandscapeRightMenuAdapter adapter = new LandscapeRightMenuAdapter(
				inflater.getContext(), menuItems);

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	OnRightMenuItemSelected _clickListener;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);
		try {
			_clickListener = (OnRightMenuItemSelected) activity;
		} catch (final ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onViewSelected");
		}
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		_clickListener
				.onLeftMenuItemSelected(LandscapeLeftMenuItems.values()[position]);
	}
}
