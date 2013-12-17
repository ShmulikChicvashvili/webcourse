package com.technion.coolie.joinin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.facebook.FacebookUser;
import com.technion.coolie.joinin.map.EventType;

public enum EventsDB {
	DB;
	
	private List<ClientEvent> mMyEvents = new ArrayList<ClientEvent>();
	private List<ClientEvent> mImAttending = new ArrayList<ClientEvent>();
	private List<ClientEvent> mCategoryMovie = new ArrayList<ClientEvent>();
	private List<ClientEvent> mCategoryStudy = new ArrayList<ClientEvent>();
	private List<ClientEvent> mCategoryFood = new ArrayList<ClientEvent>();
	private List<ClientEvent> mCategoryNightLife = new ArrayList<ClientEvent>();
	private List<ClientEvent> mCategorySport = new ArrayList<ClientEvent>();
	private List<ClientEvent> mCategoryOther = new ArrayList<ClientEvent>();	
	
	private boolean[] mModified = new boolean[]{true, true, true, true, true, true, true};
	
		
	public int CAT_FOOD  	  = 0;
	public int MY_EVENTS 	  = 1;
	public int CAT_STUDY 	  = 2;	
	public int CAT_SPORT 	  = 3;
	public int CAT_OTHER 	  = 4;
	public int CAT_MOVIE	  = 5;
	public int IM_ATTENDING   = 6;
	public int CAT_NIGHT_LIFE = 7;
	
	
	public void SetModified(int entry){
		mModified[entry] = true;
	}
	
	public void ClearModified(int entry){
		mModified[entry] = false;
	}
	
	public boolean IsModified(int entry){
		return mModified[entry];
	}
	
	public void Initialize(final Activity ac, final Runnable doneFetching){
		final ProgressDialog pd = ProgressDialog.show(ac, "Join-In", "Fetching events from server");
		pd.setCancelable(false);
		//Fetch My events from server    	 
		ClientProxy.getAllEvents(new OnDone<List<ClientEvent>>() {
			@Override public void onDone(final List<ClientEvent> ces) {								
				sortEvents(ces);
				pd.dismiss();
				new Handler().post(doneFetching);
			}
		}, new OnError(ac) {
			@Override public void beforeHandlingError() {
				pd.dismiss();
			}
		});
	}
	
	public List<ClientEvent> getMyEvents(){
		return mMyEvents;
	}
	public List<ClientEvent> getImAttending(){
		return mImAttending;
	}	
	public List<ClientEvent> getCategoryMovie(){
		return mCategoryMovie;
	}	
	public List<ClientEvent> getCategoryStudy(){
		return mCategoryStudy;
	}
	public List<ClientEvent> getCategoryFood(){
		return mCategoryFood;
	}
	public List<ClientEvent> getCategoryNightLife(){
		return mCategoryNightLife;
	}
	public List<ClientEvent> getCategorySport(){
		return mCategorySport;
	}
	public List<ClientEvent> getCategoryOther(){
		return mCategoryOther;
	} 
	
	private void sortEvents(final List<ClientEvent> events){
		FacebookUser user = new FacebookUser(FacebookLogin.getLoggedUser().getName(), FacebookLogin.getLoggedUser().getUsername());		
		for (ClientEvent ce : events) {
			if(ce.getOwner() == user.getUsername()){
				mMyEvents.add(ce);
			}else if(ce.isSubscribed(user)){
				mImAttending.add(ce);
			}
			switch(ce.getEventType()){
			case FOOD:
				mCategoryFood.add(ce);
				break;
			case MOVIE:
				mCategoryMovie.add(ce);
				break;
			case NIGHT_LIFE:
				mCategoryNightLife.add(ce);
				break;
			case SPORT:
				mCategorySport.add(ce);
				break;
			case STUDY:
				mCategoryStudy.add(ce);
				break;	
			case OTHER:
				mCategoryOther.add(ce);
			}
		}		
	}		
	
	public void joinEevnt(ClientEvent event){
		getImAttending().add(event);
		SetModified(IM_ATTENDING);
	}
	
	public void leaveEvent(ClientEvent event){
		getImAttending().remove(event);
		SetModified(IM_ATTENDING);
	}
	
	private List<ClientEvent> getList(EventType eventType) {
		List<ClientEvent> list = null;
		switch (eventType){
			case MOVIE : list = getCategoryMovie();
			case STUDY : list = getCategoryStudy();
			case FOOD : list = getCategoryFood(); 
			case NIGHT_LIFE : list = getCategoryNightLife(); 
			case SPORT : list = getCategorySport(); 
			case OTHER : list = getCategoryOther();
		}
		return list;
	}
	
	private int getFlag(EventType eventType) {
		int flag = 0;
		switch (eventType){
			case MOVIE : flag =  CAT_MOVIE;
			case STUDY : flag =  CAT_STUDY;
			case FOOD : flag =  CAT_FOOD; 
			case NIGHT_LIFE : flag =  CAT_NIGHT_LIFE; 
			case SPORT : flag =  CAT_SPORT; 
			case OTHER : flag =  CAT_OTHER;
		}
		return flag;
	}
	
	public void discardEvent(ClientEvent event){
		getMyEvents().remove(event);
		SetModified(MY_EVENTS);
		getList(event.getEventType()).remove(event);
		SetModified(getFlag(event.getEventType()));
	}
	
	public void addNewEvent(ClientEvent event){
		getMyEvents().add(event);
		SetModified(MY_EVENTS);
		getList(event.getEventType()).add(event);
		SetModified(getFlag(event.getEventType()));
	}
	
	private void resetEntry(List<ClientEvent> list, ClientEvent oldObj, ClientEvent newObj){
		list.set(list.indexOf(oldObj), newObj);
	}
	
	public void editEvent(ClientEvent oldEvent, ClientEvent newEvent){
		
		resetEntry(getMyEvents(), oldEvent, newEvent);
		SetModified(MY_EVENTS);
		
		if(oldEvent.getEventType().equals(newEvent.getEventType())){
			resetEntry(getList(oldEvent.getEventType()), oldEvent, newEvent);
			SetModified(getFlag(oldEvent.getEventType()));
		}else{
			getList(oldEvent.getEventType()).remove(oldEvent);
			SetModified(getFlag(oldEvent.getEventType()));
			getList(newEvent.getEventType()).add(newEvent);
			SetModified(getFlag(newEvent.getEventType()));
		}
	}

}//EventsDB

