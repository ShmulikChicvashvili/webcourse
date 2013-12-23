package com.technion.coolie.techpark;

import com.technion.coolie.R;
import com.technion.coolie.techpark.ClientSideUtils.ACTIVITY_REQUEST_TYPE;
import com.technion.coolie.techpark.ClientSideUtils.GEOFENCE_REMOVE_TYPE;
import com.technion.coolie.techpark.ClientSideUtils.GEOFENCE_REQUEST_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

// TODO Fix the interval of the geofencing
// TODO clean all string.xml
// TODO disable all logging when publishing (was in lynda)

public class MainActivity extends FragmentActivity {
	
	/***************************** Enable/Disable MOCKUP MODE  ****************************/
	/******/ 																		/******/	
	/******/ 		public static final boolean MOCKUP_MODE = true;					/******/												
	/******/ 																		/******/	
	/**************************************************************************************/
	
	/********************************* Common Members  ************************************/
	
	List<String> mListItems			   	   				= new ArrayList<String>();  // TODO - temp UI stuff - remove for 2nd iteration
	private ListView mUILogger;	   													// TODO - temp UI stuff - remove for 2nd iteration
	private ArrayAdapter<String> mAdapter; 											// TODO - temp UI stuff - remove for 2nd iteration
	private LotsDB mLotsDb 								= new LotsDB();
    private IntentFilter mIntentFilter					= new IntentFilter();
    
	/********************************* Geofence Members  **********************************/
	
	private List<Geofence> mCurrentGeofenceList			= new ArrayList<Geofence>();
	private List<Geofence> mSonGeofencesList			= new ArrayList<Geofence>();
	private List<String> mGeofenceIdsToRemove			= new ArrayList<String>();
    private GeofenceReceiver mBroadcastReceiver 		= new GeofenceReceiver();
    private GeofenceRequester mGeofenceRequester		= new GeofenceRequester(this);
    private GeofenceRemover mGeofenceRemover			= new GeofenceRemover(this);
    private GEOFENCE_REQUEST_TYPE mGeofenceRequestType;
    private GEOFENCE_REMOVE_TYPE mGeofenceRemoveType;    

    /************************* Activity Recognition Members  ******************************/
    
    private ACTIVITY_REQUEST_TYPE mActivityRequestType;
    private DetectionRequester mDetectionRequester		= new DetectionRequester(this);
    private DetectionRemover mDetectionRemover			= new DetectionRemover(this);
    private boolean isActivityUpdatesStarted 			= false;
    
    /**************************** Event Recognition Logic  ********************************/
        
	private Timer mTimer 								= new Timer();
	private TimerTask mTimerTask;
	private int mLastRecognizedActivity 				= ClientSideUtils.UNRECOGNIZED_ACTIVITY;
	private boolean isTimerScheduled					= false;
    
    /************************************ Methods  ****************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	setContentView(R.layout.park_activity_main);
    	
        mIntentFilter.addAction(ClientSideUtils.ACTION_GEOFENCES_ADDED);		// For Geofence
        mIntentFilter.addAction(ClientSideUtils.ACTION_GEOFENCES_REMOVED);		// For Geofence
        mIntentFilter.addAction(ClientSideUtils.ACTION_GEOFENCE_ERROR);			// For Geofence
        mIntentFilter.addAction(ClientSideUtils.ACTION_GEOFENCES_TRANSITION);	// For Geofence
        mIntentFilter.addAction(ClientSideUtils.ACTION_REFRESH_STATUS_LIST);	// For Activity Recognition
        mIntentFilter.addCategory(ClientSideUtils.CATEGORY_LOCATION_SERVICES);	// For Both
        
        /* Pay attention: as this app will be finally a service, registering is here and not in onResume,
           furthermore -no unregister at all */
        //TODO: move register/unregister to the nofitication toggle on/off 
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mIntentFilter);
        
		// TODO - remove the following block for 2nd iteration
		mUILogger = (ListView) findViewById(R.id.park_geofencing_log);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
		mUILogger.setAdapter(mAdapter);
		
		
		ToggleButton ToggleBtnOnOff = (ToggleButton) findViewById(R.id.park_toggle_button);
		ToggleBtnOnOff.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on){
					mGeofenceRequestType = ClientSideUtils.GEOFENCE_REQUEST_TYPE.ADD;
			        if (!servicesConnected()) {
			            return;
			        }
			        try {
			        	/* clear the current geofences list and add only the root geofence for tracing */
			        	mCurrentGeofenceList = new ArrayList<Geofence>();
			        	mCurrentGeofenceList.add(mLotsDb.mRootLotGeofence.toGeofenceObject());
			            mGeofenceRequester.addGeofences(mCurrentGeofenceList);
			        } catch (UnsupportedOperationException e) {
			            // Notify user that previous request hasn't finished.
			            //Toast.makeText(this, R.string.add_geofences_already_requested_error, Toast.LENGTH_LONG).show();
			        }
				}else{
					mGeofenceRemoveType = ClientSideUtils.GEOFENCE_REMOVE_TYPE.LIST;
			        if (!servicesConnected()) {
			            return;
			        }
			        try {
			        	stopActivityRecognition();
			        	// tracing is off - remove all of current geofences from tracing list
			        	removeCurrentGeofecesByID(mCurrentGeofenceList);
			        	mCurrentGeofenceList = new ArrayList<Geofence>();
			        } catch (IllegalArgumentException e) {
			            e.printStackTrace();
			        } catch (UnsupportedOperationException e) {
			            // Notify user that previous request hasn't finished.
			        	// Toast.makeText(this, R.string.remove_geofences_already_requested_error, Toast.LENGTH_LONG).show();
			        }
				}
			}
		});

		Button ButtonClearLog = (Button) findViewById(R.id.park_btn_clear_log);
		ButtonClearLog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListItems.clear();
	        	mAdapter.notifyDataSetChanged();
			}
		});
	}
    
    @Override
    protected void onStop() {
    	// TODO: will not be needed when the app will be a service
    	stopActivityRecognition(); // Stops activity recognition only if in any case it wasn't stopped before
        super.onStop();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.park_main, menu);
		return true;
	}	
	
    /* Handle results returned to this Activity by other Activities started with startActivityForResult(). */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case ClientSideUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:
                        // If the request was to add geofences
                        if (ClientSideUtils.GEOFENCE_REQUEST_TYPE.ADD == mGeofenceRequestType) {

                            // Toggle the request flag and send a new request
                            mGeofenceRequester.setInProgressFlag(false);

                            // Restart the process of adding the current geofences
                            mGeofenceRequester.addGeofences(mCurrentGeofenceList);

                        // If the request was to remove geofences
                        } else if (ClientSideUtils.GEOFENCE_REQUEST_TYPE.REMOVE == mGeofenceRequestType ){

                            // Toggle the removal flag and send a new removal request
                            mGeofenceRemover.setInProgressFlag(false);

                            // If the removal was by Intent
                            if (ClientSideUtils.GEOFENCE_REMOVE_TYPE.INTENT == mGeofenceRemoveType) {

                            	// TODO support this after fixing the remove by intent
                            	Log.d(ClientSideUtils.APPTAG, "REMOVE_TYPE.INTENT was found - unsupported in this app");
                                //mGeofenceRemover.removeGeofencesByIntent(
                                //mGeofenceRequester.getRequestPendingIntent());

                            } else {
                                // Restart the removal of the geofence list
                            	removeCurrentGeofecesByID(mCurrentGeofenceList);
                            }
                        } else if (ClientSideUtils.ACTIVITY_REQUEST_TYPE.ADD == mActivityRequestType) {
                        	
                            // Restart the process of requesting activity recognition updates
                            mDetectionRequester.requestUpdates();
                        	
                        } else if (ClientSideUtils.ACTIVITY_REQUEST_TYPE.REMOVE == mActivityRequestType) {
                        	
                        	// Restart the removal of all activity recognition updates for the PendingIntent
                            mDetectionRemover.removeUpdates(mDetectionRequester.getRequestPendingIntent());
                        	
                        }
                    break;

                    // If any other result was returned by Google Play services
                    default:
                        // Report that Google Play services was unable to resolve the problem.                    	
                        Log.d(ClientSideUtils.APPTAG, getString(R.string.no_resolution));
                }
            default:
               // Report that this Activity received an unknown requestCode
               Log.d(ClientSideUtils.APPTAG, getString(R.string.unknown_activity_request_code, requestCode));
               break;
        }
    }

    /* Verify that Google Play services is available before making a request. */
    private boolean servicesConnected() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(ClientSideUtils.APPTAG, getString(R.string.play_services_available));
            return true;
            
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), ClientSideUtils.APPTAG);
            }
            return false;
        }
    }	
	
	/*  Define a DialogFragment to display the error dialog generated in showErrorDialog. */
    public static class ErrorDialogFragment extends DialogFragment {

        private Dialog mDialog;

        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
    
    
    private void removeCurrentGeofecesByID(List<Geofence> geofenceListToRemove){
    	mGeofenceIdsToRemove = new ArrayList<String>();
    	for (Geofence currGeofence: geofenceListToRemove){
    		mGeofenceIdsToRemove.add(currGeofence.getRequestId());
    	}
        mGeofenceRemover.removeGeofencesById(mGeofenceIdsToRemove);
    }
    
    
    public void addItemToListView(String item){
    	if ((mListItems.size()== 0) ||  !mListItems.get(mListItems.size()-1).equals(item)){
    		mListItems.add(item);
    	}
    	mAdapter.notifyDataSetChanged(); 
    }
    
    private void startActivityRecognition(){
    	if (!isActivityUpdatesStarted){
		    // Check for Google Play services
		    if (!servicesConnected()) {
		        return;
		    }
		    /* Set the request type. If a connection error occurs, and Google Play services can
		     * handle it, then onActivityResult will use the request type to retry the request  */
		    mActivityRequestType = ClientSideUtils.ACTIVITY_REQUEST_TYPE.ADD;
		
		    // Pass the update request to the requester object
		    mDetectionRequester.requestUpdates();
		    isActivityUpdatesStarted = true;
		}else{
			Log.d(ClientSideUtils.APPTAG, "Tried to start activity when it was already started!!");
		}
    }
    
    private void stopActivityRecognition(){
    	if (isActivityUpdatesStarted){
	        // Check for Google Play services
	        if (!servicesConnected()) {
	            return;
	        }
	
	        /* Set the request type. If a connection error occurs, and Google Play services can
	         * handle it, then onActivityResult will use the request type to retry the request */
	        mActivityRequestType = ClientSideUtils.ACTIVITY_REQUEST_TYPE.REMOVE;
	
	        // Pass the remove request to the remover object
	        mDetectionRemover.removeUpdates(mDetectionRequester.getRequestPendingIntent());
	
	        /* Cancel the PendingIntent. Even if the removal request fails, canceling the PendingIntent will stop the updates. */
	        mDetectionRequester.getRequestPendingIntent().cancel();
	        mDetectionRequester.setRequestPendingIntent(null); // TODO ask RAN if it is a coorect fix
	        isActivityUpdatesStarted = false;
    	}else{
    		Log.d(ClientSideUtils.APPTAG, "Tried to stop activity without Starting it beforehead");
    	}
    }
    
    /** Define a Broadcast receiver that receives updates from connection listeners and the geofence transition service. */
    public class GeofenceReceiver extends BroadcastReceiver {
        /* Define the required method for broadcast receivers This method is invoked when a broadcast Intent triggers the receiver  */
        @Override
        public void onReceive(Context context, Intent intent) {

            // Check the action code and determine what to do
            String action = intent.getAction();

            // Intent contains information about errors in adding or removing geofences
            if (TextUtils.equals(action, ClientSideUtils.ACTION_GEOFENCE_ERROR)) {

                handleGeofenceError(context, intent);

            } else if (TextUtils.equals(action, ClientSideUtils.ACTION_GEOFENCES_ADDED) || TextUtils.equals(action, ClientSideUtils.ACTION_GEOFENCES_REMOVED)) {

                handleGeofenceStatus(context, intent);

            } else if (TextUtils.equals(action, ClientSideUtils.ACTION_GEOFENCES_TRANSITION)) {

            	handleGeofenceTransition(context, intent);
            	
            } else if (TextUtils.equals(action, ClientSideUtils.ACTION_REFRESH_STATUS_LIST)) {

            	handleActivityStatus(context, intent);
            	            	
            } else {
            	
                Log.e(ClientSideUtils.APPTAG, getString(R.string.invalid_action_detail, action));
                Toast.makeText(context, R.string.invalid_action, Toast.LENGTH_LONG).show();
            }
        }

        private void handleGeofenceStatus(Context context, Intent intent) {
        	// Nothing to do here meanwhile
        }

        // Contains all the adding/removing son geofences logic
        private void handleGeofenceTransition(Context context, Intent intent) {
        	String[] ids = (String[])intent.getExtras().get(ClientSideUtils.EXTRA_GEOFENCES_TRANSITION_IDS);
        	int transitionType = intent.getIntExtra(ClientSideUtils.EXTRA_GEOFENCES_TRANSITION_TYPE, -1);
        	
        	boolean hasRootGeofenceTransition = false;
        	for (String id : ids){
        		if (id.equals(ClientSideUtils.RootGeofenceId)){
        			hasRootGeofenceTransition = true;
        		}
        	}
        	
        	// TODO if both root and sons in ids DO SOMETHING
        	
        	if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
        		if (hasRootGeofenceTransition){
        			addItemToListView("Entered: " + ClientSideUtils.RootGeofenceId);
        			mSonGeofencesList = new ArrayList<Geofence>();
        			for (LotGeofence lotGeofence : mLotsDb.mSonLotGeofencesList){
            			mSonGeofencesList.add(lotGeofence.toGeofenceObject());
            			mCurrentGeofenceList.add(lotGeofence.toGeofenceObject());
        			}
		        	mGeofenceRequestType = ClientSideUtils.GEOFENCE_REQUEST_TYPE.ADD;
		            try{
		        		mGeofenceRequester.addGeofences(mSonGeofencesList);
		        	} catch(UnsupportedOperationException e){
		        		Log.d(ClientSideUtils.APPTAG, "In progress on adding geofences again");
		        	}
		            if (!MOCKUP_MODE){
		            	startActivityRecognition();// <----- Starting Activity Recognition Here
		            }
        		}else{
        			for (String id : ids){
        				enteredParkingLot(id);
        			}
        		}

        	}else if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
        		if (hasRootGeofenceTransition){
        			addItemToListView("Exited: " + ClientSideUtils.RootGeofenceId);
        			mGeofenceRemoveType = ClientSideUtils.GEOFENCE_REMOVE_TYPE.LIST;
        			removeCurrentGeofecesByID(mSonGeofencesList);
        			
        			mCurrentGeofenceList = new ArrayList<Geofence>();
        			mCurrentGeofenceList.add(mLotsDb.mRootLotGeofence.toGeofenceObject());
        			
        			stopActivityRecognition(); // <----- Stopping Activity Recognition Here
        		}else{
        			for (String id : ids){
        				exitedParkingLot(id);
        			}
        		}
        	}else{
        		Log.e(ClientSideUtils.APPTAG, "Entered Geofence action handling, the type was not ENTERED nor EXITED");
        	}
        }

        private void handleGeofenceError(Context context, Intent intent) {
            String msg = intent.getStringExtra(ClientSideUtils.EXTRA_GEOFENCE_STATUS);
            Log.e(ClientSideUtils.APPTAG, msg);
        }
        
        private void handleActivityStatus(Context context, Intent intent) {
        	int currActivityType = intent.getIntExtra(ClientSideUtils.EXTRA_ACTIVITY_NAME, ClientSideUtils.UNRECOGNIZED_ACTIVITY);
        	
        	mLastRecognizedActivity = currActivityType;
    		
    		EditText editTextLastActivity = (EditText) findViewById(R.id.park_edit_text_last_activity);
    		editTextLastActivity.setText(getNameFromType(currActivityType));
    		
    		if (isTimerScheduled && mLastRecognizedActivity == DetectedActivity.ON_FOOT){
    			addItemToListView("			Parked!");
    			resetTimerTask();
    		}
        }
    }
    
    
    /***********************************************************************/
    /************************* Parking Logic  ******************************/
    
    private void resetTimerTask(){
    	if (isTimerScheduled){
			mTimerTask.cancel();
			mTimer.purge();
			isTimerScheduled = false;
    	}else{
    		Log.d(ClientSideUtils.APPTAG, "Tried to reset timer and it was even scheduled");
    	}
    }

	public void enteredParkingLot(String lotId) {
		
		addItemToListView("		Entered: " + lotId);
		
		if (mLastRecognizedActivity == DetectedActivity.IN_VEHICLE){
			isTimerScheduled = true;
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					addItemToListView("			Parked!");
					resetTimerTask();
				}
			};
			
			mTimer.schedule(mTimerTask, ClientSideUtils.DELAY_AFTER_ENTERING_WITH_VEHICLE);
		}
	}
	
	public void exitedParkingLot(String lotId) {
		
		addItemToListView("		Exited: " + lotId);
		
		if (mLastRecognizedActivity == DetectedActivity.IN_VEHICLE){
			if (isTimerScheduled){
				addItemToListView("			No parking here!");
				resetTimerTask();
			}else{
				addItemToListView("			Left the lot!");
			}
		}
	}
	
    /* Map detected activity types to strings */
    private String getNameFromType(int activityType) {
        switch(activityType) {
            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on_bicycle";
            case DetectedActivity.ON_FOOT:
                return "on_foot";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.UNKNOWN:
                return "unknown";
            case DetectedActivity.TILTING:
                return "tilting";
        }
        return "unrecognized";
    }
}
