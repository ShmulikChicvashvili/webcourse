package com.technion.coolie.techlibrary;

import java.util.List;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WishListAdapter extends BaseAdapter {

	private final List<LibraryElement> wishItems;

	public WishListAdapter(final Context context, List<LibraryElement> wishList) {
		this.wishItems = wishList;
	}

	class viewHolder {
		public TextView name;
		public TextView author;
		public TextView library;
		public ImageView photo;
	}

	@Override
	public int getCount() {
		return wishItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// check-boundries?
		return wishItems.get(arg0);
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
			view = inflater.inflate(R.layout.lib_wish_item, null);

			holder = new viewHolder();
			holder.name = (TextView) view.findViewById(R.id.lib_book_name);
			holder.author = (TextView) view.findViewById(R.id.lib_book_author);
			holder.library = (TextView) view
					.findViewById(R.id.lib_book_library);
			holder.photo = (ImageView) view.findViewById(R.id.lib_book_image);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (viewHolder) view.getTag();
		}
		holder.name.setText(wishItems.get(position).name);
		holder.author.setText(wishItems.get(position).author);
		holder.library.setText(wishItems.get(position).library);

		return view;
	}
}

