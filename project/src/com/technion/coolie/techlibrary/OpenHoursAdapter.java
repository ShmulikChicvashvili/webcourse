package com.technion.coolie.techlibrary;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.BookItems.LoanElement;
import com.technion.coolie.techlibrary.LibrariesData.Library;
import com.technion.coolie.techlibrary.OpenHoursScreens.LibrariesListFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class OpenHoursAdapter extends BaseAdapter {

	private final ArrayList<Library> libraryList;
	private Context context;
	private LibrariesListFragment mLibListFrag;

	public OpenHoursAdapter(final Context context,
			ArrayList<Library> libraryList,
			OpenHoursScreens.LibrariesListFragment mLibListFrag) {
		this.libraryList = libraryList;
		this.context = context;
		this.mLibListFrag = mLibListFrag;
	}

	class viewHolder {
		public TextView libraryName;
		int id;
	}

	@Override
	public int getCount() {
		return libraryList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// check-boundries?
		return libraryList.get(arg0);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		View view = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.lib_open_hours_item, null);
			holder = new viewHolder();
			holder.libraryName = (TextView) view
					.findViewById(R.id.lib_book_name);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (viewHolder) view.getTag();
		}
		final String libName = libraryList.get(position).name;
		final int libId = libraryList.get(position).id;
		holder.libraryName.setText(libName);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLibListFrag.notifyClickedLibrary(libId);
			}
		});
		return view;
	}
}