package com.technion.coolie.techpark;

import com.google.android.gms.location.Geofence;

public class LotGeofence {
    private final String mId;
    private final double mLatitude;
    private final double mLongitude;
    private final float mRadius;
    private long mExpirationDuration;
    private int mTransitionType;

    public LotGeofence(String geofenceId, double latitude, double longitude, float radius, long expiration, int transition) {
    	// TODO add parameters validation, throw exception in case wrong (check checkInputFields in main sample)
        this.mId = geofenceId;
        this.mLatitude = latitude; 
        this.mLongitude = longitude;
        this.mRadius = radius; // - in meters
        this.mExpirationDuration = expiration; // - in miliseconds
        this.mTransitionType = transition;
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean same = false;

        if (object != null && object instanceof LotGeofence)
        {
        	same = this.mId.equals(((LotGeofence) object).mId);
        }

        return same;
    }
    
    // Getters
    public String getId() {
        return mId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public float getRadius() {
        return mRadius;
    }

    public long getExpirationDuration() {
        return mExpirationDuration;
    }

    public int getTransitionType() {
        return mTransitionType;
    }

    public Geofence toGeofenceObject() {
        // Build a new Geofence object
        return new Geofence.Builder()
                       .setRequestId(getId())
                       .setTransitionTypes(mTransitionType)
                       .setCircularRegion(getLatitude(), getLongitude(), getRadius())
                       .setExpirationDuration(mExpirationDuration)
                       .build();
    }
}
