package com.technion.coolie.techtrade;

import java.util.List;

import com.technion.coolie.R;

import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProductListFragment extends Fragment{

	private AdapterView<ProductListAdapter> productListView;
	private AdapterView<ProductListAdapter> productListViewHorizontal;
	private ProductListAdapter myAdapater = null;
	private ProductListAdapter myAdapaterHorizontal = null;
	
	public ProductListCallback listener;

	public interface ProductListCallback{
		public void listWasPressed(Product product);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.get_product_list_fragment,
		        container, false);		
		
		//important to retain, make sure it's in all fragments
		setRetainInstance(true);
		
		//saving the list views
		productListView = (AdapterView<ProductListAdapter>) view.findViewById(R.id.get_product_list_fragment_list_view);
		productListViewHorizontal = (AdapterView<ProductListAdapter>) view.findViewById(R.id.get_product_list_fragment_horizontal_list_view);
		
		productListView.setOnItemClickListener(new OnItemClickListener(){
			@Override 
		    public void onItemClick(AdapterView<?> list, View view,int position, long id){
				listener.listWasPressed((Product) list.getItemAtPosition(position));
			}
		});
		
		productListViewHorizontal.setOnItemClickListener(new OnItemClickListener(){
			@Override
		    public void onItemClick(AdapterView<?> list, View view,int position, long id){
				listener.listWasPressed((Product) list.getItemAtPosition(position));
			}
		});
		
	    return view;
	}

	public void setProductList(List<Product> searchProductsVector){
		//starting adapter
		myAdapater = new ProductListAdapter(getActivity(), R.layout.get_product_list_layout, searchProductsVector);
		myAdapaterHorizontal = new ProductListAdapter(getActivity(), R.layout.get_product_list_horizontal_layout, searchProductsVector);

		//seting adapter
		productListView.setAdapter(myAdapater);
		productListViewHorizontal.setAdapter(myAdapaterHorizontal);
	}
	
	public boolean hasAdapter(){
		return myAdapater!=null;
	}
	
	public boolean isEmpty(){
		return (myAdapater == null || (myAdapater.getCount()==0));
	}

	public void showVertical(){
		productListView.setVisibility(View.VISIBLE);
		productListViewHorizontal.setVisibility(View.GONE);
		productListView.setAdapter(myAdapater);
	}
	
	public void showHorizontal(){
		productListView.setVisibility(View.GONE);
		productListViewHorizontal.setVisibility(View.VISIBLE);
		productListViewHorizontal.setAdapter(myAdapaterHorizontal);
	}
	
	@Override
	  public void onAttach(Activity activity){
	    super.onAttach(activity);
	    if (activity instanceof ProductListCallback) {
	      listener = (ProductListCallback) activity;
	    }else{
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
