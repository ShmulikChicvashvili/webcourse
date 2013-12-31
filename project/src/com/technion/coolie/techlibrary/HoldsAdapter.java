package com.technion.coolie.techlibrary;

import java.util.List;

import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class HoldsAdapter extends BaseAdapter {

	private final List<HoldElement> holds;
	private Context context;
	private HoldsFragment mFragment = null;
	// protected boolean wasPressed = false;

	public HoldsAdapter(final Context context, List<HoldElement> holdsList, HoldsFragment fragment) {
		this.context = context;
		this.holds = holdsList;
		mFragment = fragment;
	}

	class viewHolder {
		public TextView name;
		public TextView author;
		public TextView library;
		public TextView created;
		public TextView position;
		public TextView arrival;
		public TextView endOfHold;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		View view = null;
		if (convertView == null) {
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
			holder.arrival = (TextView) view.findViewById(R.id.lib_hold_date);
			holder.endOfHold = (TextView) view
					.findViewById(R.id.lib_end_hold_date);
			holder.photo = (ImageView) view.findViewById(R.id.lib_book_image);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (viewHolder) view.getTag();
		}
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				BookDescription bD = new BookDescription(holds.get(position));
				Intent intent = new Intent(context,
							BookDescriptionActivity.class);
				HoldElement hE = holds.get(position);
				String[] extraData = {hE.name, hE.author, hE.library};
				intent.putExtra("description", extraData);
				((Activity)context).startActivity(intent);
				
			}
		});
		
		
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		
		ImageButton cancel = (ImageButton) view
				.findViewById(R.id.lib_cancel_button);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("cancel clicked", "the obj is: " + position);
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					// DialogInterface called while setting the AlertDialog
					// Buttons
					public void onClick(DialogInterface dialog, int which) {
						// Here you can perform functions of Alert Dialog
						// Buttons as shown
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							// Yes button clicked
							mFragment.notifyBookDelete(holds.get(position)); //doing nothing now
							holds.remove(position);
							notifyDataSetChanged();
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							// No button clicked
							break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setTitle("Deleting a book from holds list");
				// Set the Title of Alert Dialog
				builder.setMessage("Are you sure?")
						.setPositiveButton("Yes", dialogClickListener)
						.setNegativeButton("No", dialogClickListener)
						.show();

			}
		});
		
		holder.name.setText(holds.get(position).name);
		holder.author.setText(holds.get(position).author);
		holder.library.setText(holds.get(position).library);
		holder.created.setText("Created:  " +holds.get(position).createDate);
		holder.position.setText("Your position is:  "+holds.get(position).queuePosition);
		holder.arrival.setText("Arrival:  "+holds.get(position).arrivalDate);
		holder.endOfHold.setText("");//empty most of the time..

		return view;
	}
}