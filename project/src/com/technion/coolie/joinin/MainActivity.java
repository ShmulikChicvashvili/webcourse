package com.technion.coolie.joinin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.EventDate;
import com.technion.coolie.joinin.gui.ExpandableListAdapter;
import com.technion.coolie.joinin.map.EventType;



public class MainActivity extends CoolieActivity {
	
 
     
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.ji__expandable_view);
  
         // get the list view
         ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
  
         // preparing list data
         ArrayList<String> listDataHeader = new ArrayList<String>();
         HashMap<String, List<ClientEvent>> listDataChild = new HashMap<String, List<ClientEvent>>();
        // DBBridge.prepareListData(listDataHeader,listDataChild);
         prepareListData(listDataHeader,listDataChild);
  
         ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, this , listDataHeader, listDataChild);
  
         // setting list adapter
         expListView.setAdapter(listAdapter);
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
