package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.technion.coolie.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Popup {
	 
	private PopupWindow popupWindow;
	private OnPopupItemClickListener onPopupItemClickListener;
	private LinearLayout rootView;
	private LayoutInflater lInf;
	private ListView listView;
	private Context context;
	
	public Popup(Context context) {
		super();
		this.context = context;
		this.popupWindow = new PopupWindow(context);
		lInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = (LinearLayout) lInf.inflate(R.layout.techmind_popup, null);
	    listView = (ListView) rootView.findViewById(R.id.popup_list);
	}
	

	public void addItems(final ArrayList<PopUpItem> items)	{
		
		final StableArrayAdapter adapter = new StableArrayAdapter(context,
				R.layout.techmind_popup_item, items);
		    listView.setAdapter(adapter);

		    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		        @Override
		        public void onItemClick(AdapterView<?> parent, final View view,
		            int position, long id) {
		        	PopUpItem item = (PopUpItem)parent.getItemAtPosition(position);
		        	onPopupItemClickListener.onItemClick(item.getActivityClassName(), item.getItemId());

					popupWindow.dismiss();
		        }});
	}
	
	public void show(View v)	{
		

		popupWindow.setFocusable(true);
		popupWindow.setWidth(650);
		popupWindow.setHeight(700);
		popupWindow.setContentView(rootView);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
		
	}
	
	public void setOnItemClickListener(OnPopupItemClickListener onPopupItemClickListener)	{
		this.onPopupItemClickListener = onPopupItemClickListener;
	}
	
	  private class StableArrayAdapter extends ArrayAdapter<PopUpItem> {

		    HashMap<PopUpItem, Integer> mIdMap = new HashMap<PopUpItem, Integer>();
		    List<PopUpItem> items = new ArrayList<PopUpItem>();

		    public StableArrayAdapter(Context context, int textViewResourceId,
			        List<PopUpItem> objects) {
			      super(context, textViewResourceId, objects);
			      items = objects;
			      for (int i = 0; i < objects.size(); ++i) {
			        mIdMap.put(objects.get(i), i);
			      }
		    }
		    
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		    	 if(convertView==null){
		    		 LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		    		 convertView = inflater.inflate(R.layout.techmind_popup_item, parent, false);
		    	 }
		    	 PopUpItem item = items.get(position);
		         TextView tv = (TextView) convertView.findViewById(R.id.tv_popup_item);
		         tv.setText(item.getTitleText());
		         tv.setTag(item.getItemId());
		         return convertView;

		    }


		    @Override
		    public long getItemId(int position) {
		      PopUpItem item = getItem(position);
		      return mIdMap.get(item);
		    }

		    @Override
		    public boolean hasStableIds() {
		      return true;
		    }

		  }
	
}