package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.R.id;
import com.technion.coolie.R.layout;
import com.technion.coolie.R.menu;
import com.technion.coolie.joinin.LoginDialog;
import com.technion.coolie.joinin.calander.CalendarEventDatabase.NotFoundException;
import com.technion.coolie.joinin.calander.CalendarHandler;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.CategoryItem;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.EventDate;
import com.technion.coolie.joinin.data.TeamAppFacebookEvent;
import com.technion.coolie.joinin.facebook.FacebookEvent;
import com.technion.coolie.joinin.facebook.FacebookQueries;
import com.technion.coolie.joinin.facebook.FacebookQueries.OnGetUserEventsReturns;
import com.technion.coolie.joinin.gui.CategoryListAdapter;
import com.technion.coolie.joinin.gui.ExpandableListAdapter;
import com.technion.coolie.joinin.map.EventType;
import com.technion.coolie.joinin.map.MainMapActivity;
import android.app.Activity;

public class CategoryEventActivity extends CoolieActivity {
	  final Activity mContext = this;
	  public static ClientAccount mLoggedAccount = null;
	  String mCategory ;
	  ArrayList<ClientEvent> mList = null;
	  private ArrayList<String> mListDataHeader = null;	
	  private HashMap<String, List<ClientEvent>> mListDataChild = null;
	  private HashMap<String,HashMap<String, List<ClientEvent>>> mListChild = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ji__expandable_view);
		mList = (ArrayList<ClientEvent>) getIntent().getExtras().get("category");
		fetchEvents();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.ji_categories_activity_menu_item, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_item:
   		 	startActivityForResult(new Intent(mContext, CreateEventActivity.class).putExtra("account", mLoggedAccount), 1);
			return true;
		case android.R.id.home:
			this.finish();
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
    private void fetchEvents(){   
   	 // get the list view
   	 ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
   	 // go here if events empty
   	 if(mListDataHeader == null){
   		 mListDataHeader = new ArrayList<String>();
   		 prepareListData();
   	 }    	     	     	 
   	 // setting list adapter
   	 expListView.setAdapter(new ExpandableListAdapter(this, this , mListDataHeader, mListDataChild,true));
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
                startActivityForResult(startEventActivity, 1);
                
   			 return true;
   		 }
   	 });
    }
    private void prepareListData() {
     mListChild = new HashMap<String,HashMap<String, List<ClientEvent>>>();
     mListDataChild = new HashMap<String, List<ClientEvent>>();
     mListDataChild = new HashMap<String, List<ClientEvent>>();
   	 getDates();
    }
    
    private void getEventsAttending(final ArrayList<ClientEvent> attendingArr){
    	for (ClientEvent c :mList){
    		attendingArr.add(c);  
    	}
    }
    
    private void getMyEvents(final ArrayList<ClientEvent> myEventsArr){
    	for (ClientEvent c :mList){
    		myEventsArr.add(c);
    	}
    }
    
    private void getDates(){
    	String date;
   	 	ArrayList<ClientEvent> mTmp = new ArrayList<ClientEvent>();
    	for (ClientEvent c :mList){
    		date = c.getWhen().toString();
    		if (!mListDataHeader.contains(date)){		
    			mListDataHeader.add(date);
    			mListDataChild.put(date, new ArrayList<ClientEvent>());
    		}
    		mListDataChild.get(date).add(c);
    	}
    }

	
}
