package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
import android.app.ProgressDialog;

public class CategoriesActivity extends CoolieActivity {
	  final Activity mContext = this;
	  public static ClientAccount mLoggedAccount = null;
	  private ListView mainListView ;  
	  private ArrayAdapter<String> listAdapter ; 
	  private HashMap<String,ArrayList<ClientEvent>> mMap = new HashMap<String, ArrayList<ClientEvent>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ji_activity_categories);
		mLoggedAccount = (ClientAccount) getIntent().getExtras().get("account");
		prepareData();
		showDat();
	}
	
//	protected void onResume()
//	{
//	   super.onResume();
//	}
	
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
	
	public void prepareData(){
        mMap.put(EventType.FOOD.toString(), new ArrayList<ClientEvent>());
        mMap.put(EventType.MOVIE.toString(), new ArrayList<ClientEvent>());
        mMap.put(EventType.NIGHT_LIFE.toString(), new ArrayList<ClientEvent>());
        mMap.put(EventType.OTHER.toString(), new ArrayList<ClientEvent>());
        mMap.put(EventType.SPORT.toString(), new ArrayList<ClientEvent>());
        mMap.put(EventType.STUDY.toString(), new ArrayList<ClientEvent>());
                       
        getAllEvents();
        		
	}
	
	public void showDat(){    
        CategoryItem categoryItem[] = new CategoryItem[]
        {
         
            new CategoryItem(R.drawable.ji_movie_icon, EventType.MOVIE.toString(),String.valueOf(mMap.get(EventType.MOVIE.toString()).size()),"Invite people to watch a movie!"),
            new CategoryItem(R.drawable.ji_study_icon, EventType.STUDY.toString(), String.valueOf(mMap.get(EventType.STUDY.toString()).size()),"Orgenize or join study groups!"),
            new CategoryItem(R.drawable.ji_sports_icon, EventType.SPORT.toString(),String.valueOf(mMap.get(EventType.SPORT.toString()).size()),"Orgenize or join sporting events!"),
            new CategoryItem(R.drawable.ji_food_icon,  EventType.FOOD.toString(), String.valueOf(mMap.get(EventType.FOOD.toString()).size()),"Order food together!"),
            new CategoryItem(R.drawable.ji_night_life_icon, EventType.NIGHT_LIFE.toString(),String.valueOf(mMap.get(EventType.NIGHT_LIFE.toString()).size()),"Invite or join social events!"),
        };
        
        CategoryListAdapter adapter = new CategoryListAdapter(this, 
                R.layout.ji_categories_list_item, categoryItem);
        
        mainListView = (ListView) findViewById( R.id.categorymainListView );         
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
            	CategoryListAdapter adp =(CategoryListAdapter) parent.getAdapter();
            	CategoryItem item = adp.getItem(position);	
            	String category = item.title;
            	if (mMap.get(category).size() > 0){
            		startActivity(new Intent(CategoriesActivity.this, CategoryEventActivity.class).putExtra("category", mMap.get(category)).putExtra("account", mLoggedAccount));
            	} 
            }

          });
		

	}
	
	private void getAllEvents(){	     
		final ProgressDialog pd = ProgressDialog.show(this, "Join-In", "Loading Events");
		pd.setCancelable(false);
		//Fetch My events from server    	 
		ClientProxy.getAllEvents(new OnDone<List<ClientEvent>>() {
			@Override public void onDone(final List<ClientEvent> ces) {	
				for (ClientEvent clientEvent : ces) {
					mMap.get(clientEvent.getEventType().toString()).add(clientEvent);
				}				
				pd.dismiss();
				showDat();
			}
		}, new OnError(this) {
			@Override public void beforeHandlingError() {
				pd.dismiss();
			}
		});
	}	
}
