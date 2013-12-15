package com.technion.coolie.joinin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Switch;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.model.Marker;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.directions.MapDirections;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.facebook.FacebookLogin.OnLoginDone;
import com.technion.coolie.joinin.gui.ExpandableListAdapter;
import com.technion.coolie.joinin.subactivities.CategoriesActivity;
import com.technion.coolie.joinin.subactivities.CreateEventActivity;
import com.technion.coolie.joinin.subactivities.EventActivity;



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
	  public static final int RESULT_REMOVE_EVENT = RESULT_FAVORITE + 1;
	  public static final int RESULT_ADD_EVENT = RESULT_REMOVE_EVENT + 1;
	  private final int EVENT_ACTIVITY = 1;
	  private final int CREATE_EVENT_ACTIVITY = EVENT_ACTIVITY + 1;
	  private final int CATEGORIES_ACTIVITY = CREATE_EVENT_ACTIVITY + 1;
	  
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
	  private ArrayList<String> mListDataHeader = null;	
	  private HashMap<String, List<ClientEvent>> mListDataChild = null;

	     
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         //Set GCM
         GCMRegistrar.checkDevice(this);
         GCMRegistrar.checkManifest(this);                 
         setContentView(R.layout.ji__expandable_view);
         HandleLogIn();         
     }
     
     private void fetchEvents(){   
    	 // get the list view
    	 ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
    	 // go here if events empty
    	 if(mListDataHeader == null){
    		 mListDataHeader = new ArrayList<String>();
    		 mListDataChild = new HashMap<String, List<ClientEvent>>();
    		 prepareListData();
    	 }    	     	     	 
    	 // setting list adapter
    	 expListView.setAdapter(new ExpandableListAdapter(this, this , mListDataHeader, mListDataChild));
    	 // set listeners
    	 expListView.setOnChildClickListener(new OnChildClickListener() {	 
    		 @Override
    		 public boolean onChildClick(ExpandableListView parent, View v,
    				 int groupPosition, int childPosition, long id) {
    			 ExpandableListAdapter adp = (ExpandableListAdapter) parent.getExpandableListAdapter();
    			 ClientEvent eventDetails = (ClientEvent)adp.getChild(groupPosition, childPosition) ;          	 
    			 
    			 final Intent startEventActivity = new Intent(mContext, EventActivity.class);
                 startEventActivity.putExtra("event", eventDetails);
                 startEventActivity.putExtra("account", mLoggedAccount);
                 startActivityForResult(startEventActivity, EVENT_ACTIVITY);
                 
    			 return true;
    		 }
    	 });
     }
  

     private void HandleLogIn(){
    	 //Go here if there is no currently logged account
    	 if (mLoggedAccount == null){
        	 mJoinInPref = getSharedPreferences(PREFS_NAME, 0);
        	 mJoinInPref.edit().commit();
        	 mLoginDialog = new LoginDialog(this, afterLogin);
    		 FacebookLogin.login(this, afterLogin);
    	 }else{
    		 fetchEvents();
    	 }
     }
     
     //After login attempt callback 
     private final FacebookLogin.OnLoginDone afterLogin = new OnLoginDone() {
    	 @Override 
    	 public void loginCallback(final ClientAccount account) {
    		 //Login success
    		 if (account != null) {
    			 mLoggedAccount = account;
    			 mJoinInPref.edit().clear().putString("account", mLoggedAccount.toJson()).commit();
    			 fetchEvents();
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
    	 if (mLoggedAccount == null){
    		 FacebookLogin.onResult(this, requestCode, resultCode, data);
    		 return;
    	 }
    	 switch(requestCode){
    	 case CREATE_EVENT_ACTIVITY:
    		 break;
    	 case EVENT_ACTIVITY:
    		 break;
    	 case CATEGORIES_ACTIVITY:
    		 break;    	 
    	 default:
    	 }
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
    		 startActivityForResult(new Intent(mContext, CreateEventActivity.class).putExtra("account", mLoggedAccount), CREATE_EVENT_ACTIVITY);
    		 return true;
    	 case R.id.categories:
    		 startActivityForResult(new Intent(this, CategoriesActivity.class).putExtra("account", mLoggedAccount), 1);
    	 case android.R.id.home:
    		 this.finish();
    		 return true;
    	 default:
    		 return super.onOptionsItemSelected(item);
    	 }
     }
     
     private void prepareListData() {
    	 ArrayList<ClientEvent> attendingArr = new ArrayList<ClientEvent>();
    	 ArrayList<ClientEvent> myEventsArr = new ArrayList<ClientEvent>();
    	 getEventsAttending(attendingArr);
    	 getMyEvents(myEventsArr);
    	 //Create Headers
    	 mListDataHeader.add("I'm Attending");
    	 mListDataHeader.add("My Events");   
    	 // Add attending events
    	 mListDataChild.put(mListDataHeader.get(0), attendingArr);
    	 // Add My events
    	 mListDataChild.put(mListDataHeader.get(1), myEventsArr);
     }
     
     private void getEventsAttending(final ArrayList<ClientEvent> attendingArr){
    	 final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...");
    	 pd.setCancelable(false);
    	 //Fetch attending events from server
    	 ClientProxy.getEventsAttending(mLoggedAccount.getUsername(), new OnDone<List<ClientEvent>>() {
    		 @Override public void onDone(final List<ClientEvent> es) {
    			 pd.dismiss();
    			 for (final ClientEvent e : es) {
    				 if (mLoggedAccount.getUsername().equals(e.getOwner()))
    					 continue;    	          
    				 attendingArr.add(e);
    			 }
    		 }
    	 }, new OnError(this) {
    		 @Override public void beforeHandlingError() {
    			 pd.dismiss();
    		 }
    	 });
     }
     
     private void getMyEvents(final ArrayList<ClientEvent> myEventsArr){
    	 final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...");
    	 pd.setCancelable(false);
    	 //Fetch My events from server
    	 ClientProxy.getEventsByOwner(mLoggedAccount.getUsername(), new OnDone<List<ClientEvent>>() {
    		 @Override public void onDone(final List<ClientEvent> es) {
    			 pd.dismiss();
    			 for (final ClientEvent e : es) {
    				 myEventsArr.add(e);    				 
    			 }
    		 }
    	 }, new OnError(this) {
    		 @Override public void beforeHandlingError() {
    			 pd.dismiss();
    		 }
    	 });    	 
     }
     
}//MainActivity
