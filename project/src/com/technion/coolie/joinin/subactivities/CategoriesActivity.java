package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.technion.coolie.joinin.calander.CalendarEventDatabase.NotFoundException;
import com.technion.coolie.joinin.calander.CalendarHandler;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.EventDate;
import com.technion.coolie.joinin.data.TeamAppFacebookEvent;
import com.technion.coolie.joinin.facebook.FacebookEvent;
import com.technion.coolie.joinin.facebook.FacebookQueries;
import com.technion.coolie.joinin.facebook.FacebookQueries.OnGetUserEventsReturns;
import com.technion.coolie.joinin.map.EventType;
import com.technion.coolie.joinin.map.MainMapActivity;

public class CategoriesActivity extends CoolieActivity {
	private ListView mainListView ;  
	 private ArrayAdapter<String> listAdapter ; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ji_activity_categories);
		
	    // Find the ListView resource.   
	    mainListView = (ListView) findViewById( R.id.mainListView );  
	  
	    // Create and populate a List of planet names.  
	    String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",  
	                                      "Jupiter", "Saturn", "Uranus", "Neptune"};    
	    ArrayList<String> planetList = new ArrayList<String>();  
	    planetList.addAll( Arrays.asList(planets) );  
	      
	    // Create ArrayAdapter using the planet list.  
	    listAdapter = new ArrayAdapter<String>(this, R.layout.ji_categories_list_item, planetList);  
	      
	    // Add more planets. If you passed a String[] instead of a List<String>   
	    // into the ArrayAdapter constructor, you must not add more items.   
	    // Otherwise an exception will occur.  
	    listAdapter.add( "Ceres" );  
	    listAdapter.add( "Pluto" );  
	    listAdapter.add( "Haumea" );  
	    listAdapter.add( "Makemake" );  
	    listAdapter.add( "Eris" );  
	      
	    // Set the ArrayAdapter as the ListView's adapter.  
	    mainListView.setAdapter( listAdapter );
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
			return true;
		case android.R.id.home:
			this.finish();
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
