package com.technion.coolie.joinin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.EventDate;
import com.technion.coolie.joinin.directions.MapDirections;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.facebook.FacebookLogin.OnLoginDone;
import com.technion.coolie.joinin.gui.ExpandableListAdapter;
import com.technion.coolie.joinin.map.EventType;
import com.technion.coolie.joinin.subactivities.LoginActivity;
import com.technion.coolie.joinin.data.SerializableSparseBooleanArrayContainer;
import com.technion.coolie.joinin.gui.ExpandableListAdapter;
import com.technion.coolie.joinin.map.EventType;
import com.technion.coolie.joinin.places.SearchDialog;
import com.technion.coolie.joinin.subactivities.CreateEventActivity;
import com.technion.coolie.joinin.subactivities.EventFilterActivity;
import com.technion.coolie.joinin.subactivities.LoginActivity;
import com.technion.coolie.joinin.subactivities.MyEventsActivity;
import com.technion.coolie.joinin.subactivities.SettingsActivity;



public class MainActivity extends CoolieActivity {	
	  final Activity mContext = this;
	  public static ClientAccount mLoggedAccount = null;
	  final HashMap<Marker, ClientEvent> markerToEvent = new HashMap<Marker, ClientEvent>();
	  int cameraChangedCounter = 0;
	  private final double TO_E6 = 1000000.0;
	  private final int CAMERA_CHANGE_SENSETIVITY = 4;
	  private final int MIN_ICON_DIVISION = 1;
	  private final int MAX_ICON_DIVISION = 6;
	  public static final String SENDER_ID = "951960160603";
	  public static final int RESULT_REFRESH = RESULT_CANCELED + 1;
	  public static final int RESULT_REFRESH_DIRECTIONS = RESULT_REFRESH + 1;
	  public static final int RESULT_LOGIN_ACCOUNT = RESULT_REFRESH_DIRECTIONS + 1;
	  public static final int RESULT_FINISH = RESULT_LOGIN_ACCOUNT + 1;
	  public static final int RESULT_DO_NOTHING = RESULT_FINISH + 1;
	  public static final int RESULT_FILTER = RESULT_DO_NOTHING + 1;
	  public static final int RESULT_FAVORITE = RESULT_FILTER + 1;
	  public static final int RESULT_DELETE = RESULT_FAVORITE + 1;
	  public static String PACKAGE = "com.technion.coolie.joinin";
	  SharedPreferences mJoinInPref;
	  public static final String PREFS_NAME = PACKAGE; // SharedPreferences file	  
	  private Uri shareURI;
	  private String mRegId = null;
	  private SparseBooleanArray catFilter;
	  private ArrayList<String> usersFilter;
	  MapDirections md;
	  Location location;
	  private MenuItem addEventButon;
	  private LoginDialog mLoginDialog = null;

	     
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         //Set GCM
         GCMRegistrar.checkDevice(this);
         GCMRegistrar.checkManifest(this);
         
         mLoginDialog = new LoginDialog(this, afterLogin);
         HandleLogIn();
         
         setContentView(R.layout.ji__expandable_view);  
         // get the list view
         ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
  
         // preparing list data
         final ArrayList<String> listDataHeader = new ArrayList<String>();
         final HashMap<String, List<ClientEvent>> listDataChild = new HashMap<String, List<ClientEvent>>();
         prepareListData(listDataHeader,listDataChild);
  
         ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, this , listDataHeader, listDataChild);
  
         // setting list adapter
         expListView.setAdapter(listAdapter);
         
         expListView.setOnChildClickListener(new OnChildClickListener() {	 
             @Override
             public boolean onChildClick(ExpandableListView parent, View v,
                     int groupPosition, int childPosition, long id) {
            	 ExpandableListAdapter adp = (ExpandableListAdapter) parent.getExpandableListAdapter();
            	 ClientEvent eventDetails = (ClientEvent)adp.getChild(groupPosition, childPosition) ;
            	 //ClientEvent eventDetails = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
            	 
                 Toast.makeText(getApplicationContext(), eventDetails.getName(), Toast.LENGTH_SHORT).show();
                 return true;
             }
         });
     }    

     private void HandleLogIn(){
    	 mJoinInPref = getSharedPreferences(PREFS_NAME, 0);
    	 mJoinInPref.edit().commit();
    	 //mLoggedAccount = mJoinInPref.contains("account") ? ClientAccount.fromJson(mJoinInPref.getString("account", "")) : null;
    	 //Go here if there is no currently logged account
    	 if (mLoggedAccount == null){
    		 mLoginDialog.show();    		 
    	 }
     }
     
     //After login attempt callback 
     private final FacebookLogin.OnLoginDone afterLogin = new OnLoginDone() {
    	 @Override 
    	 public void loginCallback(final ClientAccount a) {
    		 //Login success
    		 if (a != null) {
    			 mLoggedAccount = a;
    			 mJoinInPref.edit().clear().putString("account", mLoggedAccount.toJson()).commit();
    		 } 
    		 //Login fail
    		 else{
    			 Toast.makeText(MainActivity.this, "Failed login please try again", Toast.LENGTH_LONG).show();    		 
    			 mLoginDialog.show();
    		 }    			 
    	 }
     };
     
     @Override 
     protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    	 if (mLoggedAccount == null)
    		 FacebookLogin.onResult(this, requestCode, resultCode, data);
     }

     
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
    	 super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.join_in_activity_itemlist, menu);
        addEventButon = menu.findItem(R.id.add_item);
        return true;
     
     }
     
     @Override public boolean onOptionsItemSelected(final MenuItem item) {
 	    switch (item.getItemId()) {
 	      case R.id.add_item:
// 	    	  startActivityForResult(new Intent(this, LoginActivity.class), RESULT_LOGIN_ACCOUNT);
// 	    	  startActivityForResult(new Intent(this, TmpCreateEventActivity.class), 1);
// 	          final LatLng gp = map.getCameraPosition().target;
// 	          startActivityForResult(new Intent(mContext, CreateEventActivity.class).putExtra("Latitude", (int) (gp.latitude * TO_E6))
// 	              .putExtra("Longtitude", (int) (gp.longitude * TO_E6)).putExtra("account", mLoggedAccount), 0);
 	    	  
 	          startActivityForResult(new Intent(mContext, CreateEventActivity.class).putExtra("account", mLoggedAccount), 1);
 	      default:
 	        return super.onOptionsItemSelected(item);
 	    }
 	  }
     
     private void prepareListData(ArrayList<String> listDataHeader,
 			HashMap<String, List<ClientEvent>> listDataChild) {
         
         // Adding child data
         listDataHeader.add("I'm Attending");
         listDataHeader.add("My Events");
  
         // Adding child data
         ArrayList<ClientEvent> attendingArr = new ArrayList<ClientEvent>();
         attendingArr.add(newEvent("Snooker Match"));
         attendingArr.add(newEvent("Bear Party"));
         attendingArr.add(newEvent("Hedva HW Solving"));
         attendingArr.add(newEvent("BBQ"));
         attendingArr.add(newEvent("Movie and Bear Night"));
         
         
      // Adding child data
         ArrayList<ClientEvent> myEventsArr = new ArrayList<ClientEvent>();
         myEventsArr.add(newEvent("Snooker Match"));
         myEventsArr.add(newEvent("Bear Party"));
         myEventsArr.add(newEvent("Hedva HW Solving"));
         myEventsArr.add(newEvent("BBQ"));
         myEventsArr.add(newEvent("Movie and Bear Night"));

         listDataChild.put(listDataHeader.get(0), attendingArr); // Header, Child data
         listDataChild.put(listDataHeader.get(1), myEventsArr); // Header, Child data

     }
     
     private ClientEvent newEvent(String name){
    	 EventDate when = new EventDate();
    	 final EventType type = EventType.SPORT;
    	 return new ClientEvent(1, name, "address", "description",
    			 				20,20, when, 5, type, "owner");
       }


}
