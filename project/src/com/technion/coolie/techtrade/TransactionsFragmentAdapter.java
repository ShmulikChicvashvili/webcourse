package com.technion.coolie.techtrade;


import java.util.List;
import java.util.Vector;

import com.technion.coolie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class TransactionsFragmentAdapter extends BaseAdapter {
	 private Context context;
	 private LayoutInflater inflater;
	 private List<?> itemsList;
	 private int adapterItemResourceId;

	    public TransactionsFragmentAdapter(Context context, List<?> itemsList, int adapterItemResourceId) {
	    	this.context = context;
	    	this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	this.itemsList = itemsList;
	    	this.adapterItemResourceId = adapterItemResourceId;
	    }

	    public int getCount() {
	        return this.itemsList.size();
	    }

	    public Object getItem(int position) {
	        return this.itemsList.get(position);
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	    	View view = null;
	    	TransactionsViewHolder holder = null;
	    	
	        if (convertView == null) {
				view = inflater.inflate(adapterItemResourceId, null);
				holder = new TransactionsViewHolder();
				holder.image = (ImageView) view.findViewById(R.id.get_transactions_grid_item_image);
				holder.name = (TextView) view.findViewById(R.id.get_transactions_grid_item_name);
				holder.date = (TextView) view.findViewById(R.id.get_transactions_grid_item_date);
				view.setTag(holder);
	        } else {
	            view = convertView;
	            holder = (TransactionsViewHolder) view.getTag();
	        }
	        
	        Product current = (Product) itemsList.get(position);
			holder.name.setText(current.getName());
			holder.date.setText(this.getDate(current));
			if(current.getImage() != null){
				holder.image.setImageBitmap(current.getImage());
			}	
	        
	        return view;
	    }

		protected abstract String getDate(Product product);
	}
