package com.technion.coolie.techtrade;

import java.util.List;
import java.util.Vector;

import com.technion.coolie.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BrowseFragment extends Fragment{

	private AdapterView<ProductListAdapter> browseListView;
	private EditText searchBox;

	public BrowseCallback listener;

	public interface BrowseCallback{
		public void listWasPressed(Product product);
		public void searchWasActivated(String searchTerm);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.get_browse_by_catagory_fragment,
				container, false);

		//saving the list view
		browseListView = (AdapterView<ProductListAdapter>) view.findViewById(R.id.get_browse_by_catagory_fragment_list_view);
		searchBox = (EditText) view.findViewById(R.id.get_browse_by_catagory_fragment_search_field);

		searchBox.setOnEditorActionListener(
				new TextView.OnEditorActionListener(){

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event){
						listener.searchWasActivated(v.getText().toString());
						return false;
					}
				}
				);

		browseListView.setOnItemClickListener(new OnItemClickListener(){
			@Override 
			public void onItemClick(AdapterView<?> list, View view,int position, long id){
				listener.listWasPressed((Product) list.getItemAtPosition(position));
			}
		});

		Button searchButton = (Button) view.findViewById(R.id.get_browse_by_catagory_fragment_search_Button);

		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				listener.searchWasActivated(searchBox.getText().toString());
			}
		}
				);

		return view;
	}


	public void setProductList(List<Product> result){

		if(browseListView.getAdapter() == null){
			//starting adapter
			ProductListAdapter browseListAdapater = new ProductListAdapter(getActivity(), R.layout.get_product_list_layout, result);

			//seting adapter
			browseListView.setAdapter(browseListAdapater);
		}
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if (activity instanceof BrowseCallback) {
			listener = (BrowseCallback) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet ProductFragment.productCallback");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
}
