package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.technion.coolie.joinin.EventsDB;
import com.technion.coolie.joinin.LoginDialog;
import com.technion.coolie.joinin.MainActivity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ji__expandable_view);
		mCategory = (String) getIntent().getExtras().get("category");
		mLoggedAccount = (ClientAccount) getIntent().getExtras().get("account");
	    showData();
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == 0) {
	        if (resultCode == RESULT_CANCELED) {   	
	        }
	        else{
//	        	ClientEvent e =	(ClientEvent) data.getExtras().get("event");
	        	if (checkModified()){
	        		showData();
	        	}
	        }
	
	        }
	    }
	
    private void showData(){   
    	ArrayList<String> listDataHeader = new ArrayList<String>();
    	HashMap<String, List<ClientEvent>> listDataChild = new HashMap<String, List<ClientEvent>>();
    	String date;
    	List<ClientEvent> mList = getCategoryByString(mCategory);
    	for (ClientEvent c :mList){
    		date = c.getWhen().toString();
    		if (!listDataHeader.contains(date)){		
    			listDataHeader.add(date);
    			listDataChild.put(date, new ArrayList<ClientEvent>());
    		}
    		listDataChild.get(date).add(c);
    	}
    	Collections.sort(listDataHeader);
    	for (String s : listDataHeader){
    		Collections.sort(listDataChild.get(s), new Comparator<ClientEvent>(){
    			@Override
    			public int compare(ClientEvent lhs, ClientEvent rhs) {
    				return (int)(lhs.getWhen().getTime() - rhs.getWhen().getTime());
    			}
    		});
    	}
    
   	 // get the list view
   	 ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expendable_list);
   	 // setting list adapter
   	 expListView.setAdapter(new ExpandableListAdapter(this, this , listDataHeader, listDataChild,true));
   	 //checking just in case 
   	 if (listDataHeader.size() > 0){
   		expListView.expandGroup(0);
   	 }
   	 // set listeners
   	 expListView.setOnChildClickListener(new OnChildClickListener() {	 
   		 @Override
   		 public boolean onChildClick(ExpandableListView parent, View v,
   				 int groupPosition, int childPosition, long id) {
			 ExpandableListAdapter adp = (ExpandableListAdapter) parent.getExpandableListAdapter();
			 ClientEvent eventDetails = (ClientEvent)adp.getChild(groupPosition, childPosition) ;     
			 Intent startEventActivity = new Intent(CategoryEventActivity.this, EventActivity.class);
			 startEventActivity.putExtra("event", eventDetails);
			 startEventActivity.putExtra("account", mLoggedAccount);
			 startActivityForResult(startEventActivity, 0);
			 return true;
   		 }
   	 });
    }
	private List<ClientEvent> getCategoryByString(String s){
		if (s == EventType.MOVIE.toString()) {
			return EventsDB.DB.getCategoryMovie();
		}
		else if (s == EventType.STUDY.toString()){
			return EventsDB.DB.getCategoryStudy();
		}
		else if (s == EventType.NIGHT_LIFE.toString()) {
			return EventsDB.DB.getCategoryNightLife();
		}
		else if (s == EventType.SPORT.toString()){
			return EventsDB.DB.getCategorySport();
		}
		else if (s == EventType.FOOD.toString()) {
			return EventsDB.DB.getCategoryFood();
		}
		else{
			return EventsDB.DB.getCategoryOther();
		}
	}
	
	private boolean checkModified(){
		if (mCategory == "MOVIE"){
			return EventsDB.DB.IsModified(EventsDB.DB.CAT_MOVIE);
		}
		if (mCategory == "STUDY"){
			return EventsDB.DB.IsModified(EventsDB.DB.CAT_STUDY);
		}
		if (mCategory == "NIGHT_LIFE"){
			return EventsDB.DB.IsModified(EventsDB.DB.CAT_NIGHT_LIFE);
		}
		if (mCategory == "SPORT"){
			return EventsDB.DB.IsModified(EventsDB.DB.CAT_SPORT);
		}
		if (mCategory == "FOOD"){
			return EventsDB.DB.IsModified(EventsDB.DB.CAT_FOOD);
		}
		if (mCategory == "OTHER"){
			return EventsDB.DB.IsModified(EventsDB.DB.CAT_OTHER);
		}
		return false;
	}
    	
}
