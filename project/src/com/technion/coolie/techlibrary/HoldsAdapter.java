package com.technion.coolie.techlibrary;

import java.util.List;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HoldsAdapter extends BaseAdapter {

	private final List<HoldElement> holds;

	public HoldsAdapter(final Context context, List<HoldElement> holdsList) {
		this.holds = holdsList;
	}

	class viewHolder {
		public TextView name;
		public TextView author;
		public TextView library;
		public TextView created;
		public TextView position;
		public ImageView photo;
	}

	@Override
	public int getCount() {
		return holds.size();
	}

	@Override
	public Object getItem(int arg0) {
		// check-boundries?
		return holds.get(arg0);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		View view = null;
		if (convertView == null) {
			// LayoutInflater inflater =
			// LayoutInflater.from(MainActivity2.this);
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.lib_holds_item, null);

			holder = new viewHolder();
			holder.name = (TextView) view.findViewById(R.id.lib_book_name);
			holder.author = (TextView) view.findViewById(R.id.lib_book_author);
			holder.library = (TextView) view
					.findViewById(R.id.lib_book_library);
			holder.created = (TextView) view
					.findViewById(R.id.lib_hold_created);
			holder.position = (TextView) view
					.findViewById(R.id.lib_hold_position);
			holder.photo = (ImageView) view.findViewById(R.id.lib_book_image);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (viewHolder) view.getTag();
		}
		holder.name.setText(holds.get(position).name);
		holder.author.setText(holds.get(position).author);
		holder.library.setText(holds.get(position).library);
		holder.created.setText(holds.get(position).createDate);
		holder.position.setText(holds.get(position).queuePosition);
		
		return view;
	}
}