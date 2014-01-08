package com.technion.coolie.techlibrary.maps;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.LibrariesData;
import com.technion.coolie.techlibrary.LibrariesData.Library;

public class LibraryMapLocationActivity extends CoolieActivity implements MySupportMapFragment.MapCreatedListener {

	// Google Map
	private GoogleMap googleMap;
	private MySupportMapFragment mMapFragment;
	private Integer libraryId;
	private Library chosenLibrary = null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_activity_library_map_location);
		mMapFragment = new MySupportMapFragment();
	    FragmentTransaction fragmentTransaction =
	             getSupportFragmentManager().beginTransaction();
	     fragmentTransaction.add(R.id.map_frame, mMapFragment);
	     fragmentTransaction.commit(); 
	     
	     //get library id
	     libraryId = getIntent().getIntExtra("libraryId", -1);
		//? check if google services available?
		//GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
	
	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		LatLng INICIAL_SPOT = new LatLng(32.77749675, 35.02425313);
		
		if (googleMap == null) {
			googleMap = mMapFragment.getMap();
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
		BitmapDescriptor bitmapDescriptor 
		   = BitmapDescriptorFactory.defaultMarker(
		     BitmapDescriptorFactory.HUE_AZURE);
		BitmapDescriptor bitmapDescriptorChosen = BitmapDescriptorFactory.defaultMarker(
			     BitmapDescriptorFactory.HUE_RED);
		//adding markers
		ArrayList<Library> librariesList = LibrariesData.getLibrariesList();
		for(Library library : librariesList) {
			Marker marker = googleMap.addMarker(new MarkerOptions().position(library.location)
			          .title(library.name).icon(bitmapDescriptor));
			if(library.id == libraryId) {
				marker.setIcon(bitmapDescriptorChosen);
				marker.showInfoWindow();
				chosenLibrary = library;
			}
		}
	    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIAL_SPOT, 15));
	
	    // Zoom in, animating the camera.
	    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chosenLibrary.location, 17), 2000, null); 
	}

	@Override
	public void onMapCreated() {
		try {
			// Loading map
			initilizeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
