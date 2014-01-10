package com.technion.coolie.techtrade;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technion.coolie.R;

public class ProductListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<?> itemsVector;
	private int adapterItemResourceId;
	
	public ProductListAdapter(Context context,int adapterItemResourceId,List<?> itemsVector){
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.itemsVector = itemsVector;
		this.adapterItemResourceId = adapterItemResourceId;
	}
	
	@Override
	public int getCount() {
		if(this.itemsVector==null) return 0;
		return this.itemsVector.size();
	}

	@Override
	public Object getItem(int position) {
		return ((List<?>) this.itemsVector).get(position);
	}

	@Override
	public long getItemId(int position) {
		//TODO implement
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ProductListViewHolder holder = null;
		
		if(convertView == null){
			//create a new view using existing layout
			view = inflater.inflate(adapterItemResourceId, null);
			//create viewHolder for faster access to fields
			holder = new ProductListViewHolder();
			holder.listItem = (ViewGroup) view.findViewById(R.id.get_product_list_layout);
			holder.name = (TextView) view.findViewById(R.id.get_prodcut_list_layout_product_name);
			holder.price = (TextView) view.findViewById(R.id.get_prodcut_list_layout_product_price);
			holder.picture = (ImageView) view.findViewById(R.id.get_product_list_layout_image);
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ProductListViewHolder) view.getTag();
		}
		
		Product current = (Product) itemsVector.get(position);
		if(current.getImage() != null) holder.picture.setImageBitmap(current.getImage());
		holder.name.setText(current.getName());
		holder.price.setText(current.getPrice().toString());
		
		return view;
	}
}