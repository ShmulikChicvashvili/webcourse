package com.technion.coolie.techlibrary;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LoanElement;

public class LoansAdapter extends BaseAdapter {

	private final List<LoanElement> loans;
//	private boolean wasPressed = false;
	private Context context;

	public LoansAdapter(final Context context, List<LoanElement> loansList) {
		this.loans = loansList;
		this.context = context;
	}

	class viewHolder {
		public TextView name;
		public TextView author;
		public TextView library;
		public TextView duedate;
		public ImageView photo;
	}

	@Override
	public int getCount() {
		return loans.size();
	}

	@Override
	public Object getItem(int arg0) {
		// check-boundries?
		return loans.get(arg0);
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
			// LayoutInflater inflater =
			// LayoutInflater.from(MainActivity2.this);
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.lib_loans_item, null);
			
			holder = new viewHolder();
			holder.name = (TextView) view.findViewById(R.id.lib_book_name);
			holder.author = (TextView) view.findViewById(R.id.lib_book_author);
			holder.library = (TextView) view
					.findViewById(R.id.lib_book_library);
			holder.duedate = (TextView) view
					.findViewById(R.id.lib_book_duedate);
			holder.photo = (ImageView) view.findViewById(R.id.lib_book_image);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (viewHolder) view.getTag();
		}
		
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				BookDescription bD = new BookDescription(holds.get(position));
				Intent intent = new Intent(context,
						BookDescriptionActivity.class);
				LoanElement hE = loans.get(position);
				String[] extraData = {hE.name, hE.author, hE.library, hE.id};
				intent.putExtra("description", extraData);
				((Activity)context).startActivityForResult(intent,0);
				
			}
		});
		holder.name.setText(loans.get(position).name);
		holder.author.setText(loans.get(position).author);
		holder.library.setText(loans.get(position).library);
		holder.duedate.setText(loans.get(position).dueDate);

		return view;
	}
}