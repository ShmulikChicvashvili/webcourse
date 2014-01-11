package com.technion.coolie.techpark;

import java.util.ArrayList;

public class LotsDB {
	// When the userenters this geofence it can trace all the geofences in mSonLotGeofencesList
	LotGeofence mRootLotGeofence;
	ArrayList<LotGeofence> mSonLotGeofencesList;
	
    public LotsDB() {
    	/* Populate Root geofence */
    	mRootLotGeofence = new LotGeofence(
    			 ClientSideUtils.RootGeofenceId,
				 ClientSideUtils.RootGeofenceLatitude,
				 ClientSideUtils.RootGeofenceLongitude,
				 ClientSideUtils.RootGeofenceRadius,
			 	 ClientSideUtils.GeofenceExpirationDuration,
			 	 ClientSideUtils.GeofenceTransitionType);
    	/* Populate Sons geofences */
    	mSonLotGeofencesList = new ArrayList<LotGeofence>();
    	for (int i=0 ; i< ClientSideUtils.sonsGeofenceDB.length ; i++){
    		mSonLotGeofencesList.add(ClientSideUtils.sonsGeofenceDB[i]);
    	}
    	
    }

    public LotGeofence getGeofence(String id) {
    	for (LotGeofence lot : mSonLotGeofencesList){
    		if (lot.equals(new LotGeofence(id, 0, 0, 0, 0, 0))){
    			return lot;
    		}
    	}
    	return null;
    }
    
    public void addGeofence(LotGeofence geofence) {
    	mSonLotGeofencesList.add(geofence);
    }

    public void removeGeofence(LotGeofence geofence) {
    	mSonLotGeofencesList.remove(geofence);
    }    
    
    public void clearGeofence(String id) {
    	mSonLotGeofencesList.clear();
    }
}
