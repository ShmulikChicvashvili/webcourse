package com.technion.coolie.techlibrary.maps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

public class MySupportMapFragment extends SupportMapFragment {
	public MapCreatedListener mapCreatedListener;
	
	// Callback for results
	public interface MapCreatedListener {
	    public void onMapCreated();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	mapCreatedListener = (MapCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MapCreatedListener");
        }
    }
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = super.onCreateView(inflater, container, savedInstanceState);
	    // Notify the view has been created
	    if( mapCreatedListener != null ) {
	    	mapCreatedListener.onMapCreated();
	    } else {
	    	//possible?
	    	Log.e("MySupportMapFragment","listener is null :\\");
	    }
	    return view;
	}
}
