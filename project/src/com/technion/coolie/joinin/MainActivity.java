package com.technion.coolie.joinin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.facebook.FacebookLogin.OnLoginDone;
import com.technion.coolie.joinin.gui.ExpandableListAdapter;
import com.technion.coolie.joinin.subactivities.CategoriesActivity;
import com.technion.coolie.joinin.subactivities.CreateEventActivity;
import com.technion.coolie.joinin.subactivities.EventActivity;



public class MainActivity extends CoolieActivity {	

	public static final String PREFS_NAME = "com.technion.coolie.joinin";
	
	//Result codes
	private static final int BASE_RESULT 		 = 100;
	public static  final int RESULT_REMOVE_EVENT = BASE_RESULT + 1;
	public static  final int RESULT_ADD_EVENT 	 = BASE_RESULT + 2;
	public static  final int RESULT_EDIT_EVENT 	 = BASE_RESULT + 3;
	public static  final int RESULT_JOIN_EVENT 	 = BASE_RESULT + 4;
	//Request codes
	private static final int BASE_REQUEST 				   = 200;
	public static  final int REQUEST_EVENT_ACTIVITY		   = BASE_REQUEST + 1;
	public static  final int REQUEST_CREATE_EVENT_ACTIVITY = BASE_REQUEST + 2;
	public static  final int REQUEST_CATEGORIES_ACTIVITY   = BASE_REQUEST + 3;
	
	//Private members	
	private ClientAccount mLoggedAccount = null;
	private SharedPreferences mJoinInPref = null;
	private LoginDialog mLoginDialog = null;

	     
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         //Set GCM
         GCMRegistrar.checkDevice(this);
         GCMRegistrar.checkManifest(this);                 
         setContentView(R.layout.ji__expandable_view);
         setExpandableListViewListener();
         HandleLogIn();         
     }
     
     private void setExpandableListViewListener(){  
    	 ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
    	 // set listeners
    	 expListView.setOnChildClickListener(new OnChildClickListener() {	 
    		 @Override
    		 public boolean onChildClick(ExpandableListView parent, View v,
    				 int groupPosition, int childPosition, long id) {
    			 ExpandableListAdapter adp = (ExpandableListAdapter) parent.getExpandableListAdapter();
    			 ClientEvent eventDetails = (ClientEvent)adp.getChild(groupPosition, childPosition) ;     
    			 Intent startEventActivity = new Intent(MainActivity.this, EventActivity.class);
    			 startEventActivity.putExtra("event", eventDetails);
    			 startEventActivity.putExtra("account", mLoggedAccount);
    			 startActivityForResult(startEventActivity, REQUEST_EVENT_ACTIVITY);
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
    		 getEvents();
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
    			 getEvents();
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
    	 case REQUEST_CREATE_EVENT_ACTIVITY:
    		 break;
    	 case REQUEST_EVENT_ACTIVITY:
    		 break;
    	 case REQUEST_CATEGORIES_ACTIVITY:
    		 break;    	 
    	 default:
    	 }
     }

     
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
    	 super.onCreateOptionsMenu(menu);
    	 MenuInflater inflater = getSupportMenuInflater();
    	 inflater.inflate(R.menu.join_in_activity_itemlist, menu);        
    	 return true;
     }
     
     @Override public boolean onOptionsItemSelected(final MenuItem item) {
    	 switch (item.getItemId()) {
    	 case R.id.add_item:
    		 startActivityForResult(new Intent(this, CreateEventActivity.class).putExtra("account", mLoggedAccount)
    				 , REQUEST_CREATE_EVENT_ACTIVITY);
    		 return true;
    	 case R.id.categories:
    		 startActivityForResult(new Intent(this, CategoriesActivity.class).putExtra("account", mLoggedAccount)
    				 , REQUEST_CATEGORIES_ACTIVITY);
    		 return true;
    	 case android.R.id.home:
    		 this.finish();
    		 return true;
    	 default:
    		 return super.onOptionsItemSelected(item);
    	 }
     }
     
     
     private void getEvents(){
    	 final ProgressDialog pd = ProgressDialog.show(this, "Join-In", "Fetching events from server");
    	 final ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
    	 pd.setCancelable(false);
    	 //Fetch attending events from server
    	 ClientProxy.getEventsAttending(mLoggedAccount.getUsername(), new OnDone<List<ClientEvent>>() {
    		 @Override public void onDone(final List<ClientEvent> es) {   
    			 pd.dismiss();			 
    			 if (es.size() > 0){
    				 
    				 ArrayList<ClientEvent> attending = new ArrayList<ClientEvent>();
    				 ArrayList<ClientEvent> myEvents = new ArrayList<ClientEvent>();
    				 String userName = mLoggedAccount.getUsername();
    				 for (final ClientEvent e : es) {
    					 if (userName == e.getOwner()){
    						 myEvents.add(e);    						 
    					 }else{
    						 attending.add(e);    						 
    					 }    					 
    				 }    				     				 
    				 ArrayList<String> headers = new ArrayList<String>();
    				 HashMap<String, List<ClientEvent>> events = new HashMap<String, List<ClientEvent>>();
    				 if (attending.size() > 0){
    					 headers.add("I'm attending");
    					 Collections.sort(attending, sortEvents);
    					 events.put("I'm attending", attending);
    				 }
    				 if (myEvents.size() > 0 ){
    					 headers.add("My Events");
    					 Collections.sort(myEvents, sortEvents);
    					 events.put("My Events", myEvents);
    				 }
    				 expListView.setAdapter(new ExpandableListAdapter(MainActivity.this, MainActivity.this, 
    						 headers, events));
    				 for (int i = 0; i < headers.size(); ++i){
    					 expListView.expandGroup(i);
    				 } 
    			 }else{
    	    		 startActivityForResult(new Intent(MainActivity.this, CategoriesActivity.class).putExtra("account", mLoggedAccount)
    	    				 , REQUEST_CATEGORIES_ACTIVITY);
    			 }
    		 }
    	 }, new OnError(this) {
    		 @Override public void beforeHandlingError() {
    			 pd.dismiss();
    		 }
    	 });
     }      
     
     
     private final Comparator<ClientEvent> sortEvents = new Comparator<ClientEvent>() {		
		@Override
		public int compare(ClientEvent lhs, ClientEvent rhs) {
			return (int)(lhs.getWhen().getTime() - rhs.getWhen().getTime());
		}
	};
}//MainActivity
