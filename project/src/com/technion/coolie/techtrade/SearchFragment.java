package com.technion.coolie.techtrade;

import java.util.List;
import com.technion.coolie.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchFragment extends Fragment{

	private AdapterView<ProductListAdapter> searchListView;
	
	public searchCallback listener;

	public interface searchCallback{
		public void listWasPressed(Product product);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.get_search_result_fragmant,
		        container, false);		

		//saving the list view
		searchListView = (AdapterView<ProductListAdapter>) view.findViewById(R.id.get_search_screen_fragment_layout_list_view);
		
		searchListView.setOnItemClickListener(new OnItemClickListener(){
			@Override 
		    public void onItemClick(AdapterView<?> list, View view,int position, long id){
				listener.listWasPressed((Product) list.getItemAtPosition(position));
			}
		});
		
	    return view;
	}
	

	public void setProductList(List<Product> searchProductsVector){
		
		//starting adapter
		ProductListAdapter searchListAdapater = new ProductListAdapter(getActivity(), R.layout.get_product_list_layout, searchProductsVector);
	
		//seting adapter
		searchListView.setAdapter(searchListAdapater);
	}
	
	@Override
	  public void onAttach(Activity activity){
	    super.onAttach(activity);
	    if (activity instanceof searchCallback) {
	      listener = (searchCallback) activity;
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
