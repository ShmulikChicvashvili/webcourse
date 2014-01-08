package com.technion.coolie.techlibrary.maps;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.CopyOfMySupportMapFragment;

public class LibraryMapLocationActivity extends CoolieActivity implements CopyOfMySupportMapFragment.MapCreatedListener {

	// Google Map
	private GoogleMap googleMap;
	private CopyOfMySupportMapFragment mMapFragment;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_activity_library_map_location);
		mMapFragment = new CopyOfMySupportMapFragment();
	    FragmentTransaction fragmentTransaction =
	             getSupportFragmentManager().beginTransaction();
	     fragmentTransaction.add(R.id.map_frame, mMapFragment);
	     fragmentTransaction.commit(); 
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
		//TODO: change of course
		final LatLng taubLibrary = new LatLng(32.777739, 35.021724);
		final LatLng KIEL = new LatLng(53.551, 9.993);
		
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
		Marker hamburg = googleMap.addMarker(new MarkerOptions().position(taubLibrary)
		          .title("CS Library"));
	      Marker kiel = googleMap.addMarker(new MarkerOptions()
	          .position(KIEL)
	          .title("Kiel")
	          .snippet("Kiel is cool")
	          .icon(BitmapDescriptorFactory
	              .fromResource(R.drawable.ic_launcher)));
	    //Move the camera instantly to hamburg with a zoom of 255.
	      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taubLibrary, 17));

	      // Zoom in, animating the camera.
	      googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null); 
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
