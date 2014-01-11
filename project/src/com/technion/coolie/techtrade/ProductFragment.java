package com.technion.coolie.techtrade;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import com.technion.coolie.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProductFragment extends Fragment{

	private TextView productName;
	private TextView sellerName;
	private TextView price;
	private TextView description;
	private ImageView picture;
	private Product myProduct = null;
	
	public productCallback listener;
	
	public interface productCallback{
		public void sms();
		public void call();
		public void buy();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.get_product_page_fragment,
		        container, false);
		
		//important to retain, make sure it's in all fragments
		setRetainInstance(true);
		
		productName = (TextView) view.findViewById(R.id.get_product_page_fragment_name);
		sellerName= (TextView) view.findViewById(R.id.get_product_page_fragment_seller_name);
		price = (TextView) view.findViewById(R.id.get_product_page_fragment_price);
		picture = (ImageView) view.findViewById(R.id.get_product_page_fragment_image);
		description = (TextView) view.findViewById(R.id.get_product_page_fragment_description_screen);
		
		//define button pressing function
		Button button = (Button) view.findViewById(R.id.get_product_page_fragment_call_button);
	    button.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	  listener.call();
	      }
	    });
	    
	    button = (Button) view.findViewById(R.id.get_product_page_fragment_buy_button);
	    button.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	  listener.buy();
	      }
	    });
		
	    button = (Button) view.findViewById(R.id.get_product_page_fragment_sms_button);
	    button.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	 listener.sms(); 
	      }
	    });
	    
	    return view;
	}
	
	public void setProduct(Product thisProduct){
		//set it's stuff into this place		
		productName.setText(thisProduct.getName());
		sellerName.setText("Seller Name: "+thisProduct.getSellerId());
		price.setText("Price: " + thisProduct.getPrice().toString());
		if(thisProduct.getImage() != null) picture.setImageBitmap(thisProduct.getImage());
		description.setText("Description\r\n"+thisProduct.getDescripstion());
		
		//save the product
		myProduct = thisProduct;
	}

	public void wakeUp(){
		setProduct(myProduct);
	}

	public boolean hasProduct(){
		return myProduct != null;
	}

	public Product getProduct(){
		return myProduct;
	}
	
	@Override
	  public void onAttach(Activity activity){
	    super.onAttach(activity);
	    if (activity instanceof productCallback){
	      listener = (productCallback) activity;
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
