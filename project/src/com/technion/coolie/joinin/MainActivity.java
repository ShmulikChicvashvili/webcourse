package com.technion.coolie.joinin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

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
	private MenuItem addEventButon;
	public static ClientAccount mLoggedAccount = null;
	final Activity mContext = this;

	     
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
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
