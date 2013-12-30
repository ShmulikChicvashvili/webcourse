package com.technion.coolie.techpark;

import com.google.android.gms.location.Geofence;

public final class ClientSideUtils {
	
/******************************* COMMON DEFINITIONS  ***********************************/

	/* The log tag for the application */
    public static final String APPTAG = "Client Side";
    
    public static final long MILLISECONDS_PER_SECOND = 1000;
    
    public static final long MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND*60;

    public static final long DETECTION_INTERVAL_SECONDS = 3;

    public static final long DETECTION_INTERVAL_MILLISECONDS =
            MILLISECONDS_PER_SECOND * DETECTION_INTERVAL_SECONDS;
    
    public static final long DELAY_AFTER_ENTERING_WITH_VEHICLE = MILLISECONDS_PER_MINUTE*5;
    
    /* Define a request code to send to Google Play services ,This code is returned in Activity.onActivityResult */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    public static final CharSequence GEOFENCE_ID_DELIMITER = ",";
    
    
    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "il.ac.technion.coolie.parkion.clientside.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "il.ac.technion.coolie.parkion.clientside.EXTRA_CONNECTION_ERROR_MESSAGE";
    
    public static final String ACTION_CONNECTION_ERROR =
            "il.ac.technion.coolie.parkion.clientside.ACTION_CONNECTION_ERROR";  
    
    // The Intent category used by all Location Services sample apps
    public static final String CATEGORY_LOCATION_SERVICES =
                    "il.ac.technion.coolie.parkion.clientside.CATEGORY_LOCATION_SERVICES";
    
    public static final int NEVER_ENTERED_WITH_A_CAR = -1;
    
/****************************************************************************************/
/****************************** ACTIVITY RECOGNITION BLOCK ******************************/
    
    // Shared Preferences repository name
    public static final String SHARED_PREFERENCES =
            "il.ac.technion.coolie.parkion.clientside.SHARED_PREFERENCES";
    
    public static final String EXTRA_ACTIVITY_NAME =
            "il.ac.technion.coolie.parkion.clientside.EXTRA_ACTIVITY_NAME";
   
    public static final String ACTION_REFRESH_STATUS_LIST =
            "il.ac.technion.coolie.parkion.clientside.ACTION_REFRESH_STATUS_LIST";
    
    // Key in the repository for the previous activity
    public static final String KEY_PREVIOUS_ACTIVITY_TYPE =
            "il.ac.technion.coolie.parkion.clientside.KEY_PREVIOUS_ACTIVITY_TYPE";

    // Used to track what type of request is in process
    public enum ACTIVITY_REQUEST_TYPE {ADD, REMOVE}
    
    public static final int UNRECOGNIZED_ACTIVITY = -1;

/****************************************************************************************/
/************************************ GEOFENCE BLOCK ************************************/
    
    // Used to track what type of geofence removal request was made.
    public enum GEOFENCE_REMOVE_TYPE {INTENT, LIST}

    // Used to track what type of request is in process
    public enum GEOFENCE_REQUEST_TYPE {ADD, REMOVE}
        
    // Intent actions
    public static final String ACTION_CONNECTION_SUCCESS =
            "il.ac.technion.coolie.parkion.clientside.ACTION_CONNECTION_SUCCESS";

    public static final String ACTION_GEOFENCES_ADDED =
            "il.ac.technion.coolie.parkion.clientside.ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED =
            "il.ac.technion.coolie.parkion.clientside.ACTION_GEOFENCES_DELETED";

    public static final String ACTION_GEOFENCE_ERROR =
            "il.ac.technion.coolie.parkion.clientside.ACTION_GEOFENCES_ERROR";

    public static final String ACTION_GEOFENCES_TRANSITION =
            "il.ac.technion.coolie.parkion.clientside.ACTION_GEOFENCES_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
                    "il.ac.technion.coolie.parkion.clientside.ACTION_GEOFENCE_TRANSITION_ERROR";

    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE =
                    "com.example.android.EXTRA_CONNECTION_CODE";
    
    public static final String EXTRA_GEOFENCES_TRANSITION_TYPE =
            "il.ac.technion.coolie.parkion.clientside.EXTRA_GEOFENCES_TRANSITION_TYPE";    
    
    public static final String EXTRA_GEOFENCES_TRANSITION_IDS =
            "il.ac.technion.coolie.parkion.clientside.EXTRA_GEOFENCES_TRANSITION_IDS";        

    public static final String EXTRA_GEOFENCE_STATUS =
            "il.ac.technion.coolie.parkion.clientside.EXTRA_GEOFENCE_STATUS";
    
//  /*
//   * Constants used in verifying the correctness of input values
//   */
//  public static final double MAX_LATITUDE = 90.d;
//
//  public static final double MIN_LATITUDE = -90.d;
//
//  public static final double MAX_LONGITUDE = 180.d;
//
//  public static final double MIN_LONGITUDE = -180.d;
//
//  public static final float MIN_RADIUS = 1f;
  
    
/****************************************************************************************/
/**********************  DEFINES ALL ACTUALL GEOFENCE PARAMETERS  ***********************/
       
    /* Common geofence parameters */
    public static final int NEVER_EXPIRES = -1;
    public static final int TRANSITION_ENTER_AND_EXIT = Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT;
    public static final long GeofenceExpirationDuration = NEVER_EXPIRES;
    public static final int GeofenceTransitionType = TRANSITION_ENTER_AND_EXIT;
    
    /* Root geofence parameters */
    public static final String RootGeofenceId = "Technion";
    public static final double RootGeofenceLatitude = 32.77544003526612;
    public static final double RootGeofenceLongitude = 35.021817684173584;
    public static final float RootGeofenceRadius = 670;

    /* Sons geofence parameters */
    public static final LotGeofence[] sonsGeofenceDB = {
//    		new LotGeofence("Taub",
//					32.77782450,
//					35.02130230,
//					20,
//		    		ClientSideUtils.GeofenceExpirationDuration,
//		    		ClientSideUtils.GeofenceTransitionType),

    		new LotGeofence("Mizrah Hadash - Upper",
    				32.77269767232348,
    				35.02660810947418,
    				120,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),				    		

			new LotGeofence("Handesaim",
					32.77553926391995,
					35.0288772583007,
					92,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),
		    		
    		new LotGeofence("Senat",
    				32.77599932259651,
    				35.02007156610489,
					69,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),		

    		new LotGeofence("Taub Bottom",
    				32.77850705160302,
    				35.02154141664505,
					92,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),	

			new LotGeofence("Pool",
					32.78004503029185,
					35.020152032375336,
					31,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),	

			new LotGeofence("Neve America",
					32.778037985363675,
					35.015951693058014,
					25,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),		

			new LotGeofence("Kfar Mishtalmim",
					32.77396061312344,
					35.01624941825867,
					192,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),
		    		
			new LotGeofence("Main Library",
					32.775268640057995,
					35.02320975065231,
					45,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),		
		    		
			new LotGeofence("Taub Upper",
					32.777185541348636,
					35.02099424600601,
					80,
		    		ClientSideUtils.GeofenceExpirationDuration,
		    		ClientSideUtils.GeofenceTransitionType),			    		
				    		
    };
}
