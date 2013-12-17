package com.technion.coolie.joinin.subactivities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.technion.coolie.R;
import com.technion.coolie.joinin.EventsDB;
import com.technion.coolie.joinin.MainActivity;
import com.technion.coolie.joinin.calander.CalendarHandler;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.OnTabRefresh;
import com.technion.coolie.joinin.map.MainMapActivity;

public class EventInfoFragment extends Fragment implements OnFragmentRefresh {
  protected static final int EDITED = 69;
  Context thisOne;
  TextView eventName;
  TextView eventAddress;
  TextView eventDate;
  TextView eventTime;
  TextView eventDesc;
  ImageView eventCat;
  //ImageButton eventO1;
  ImageButton joinImgBtn;
  //ImageButton directionsButton;
  ProgressBar pb;
  List<String> joinedAccounts;
  OnTabRefresh mCallback;
  private View pbView;
  
  /**
   * 
   * @return the logged account
   */
  ClientAccount getAccount() {
    return ((EventActivity) getActivity()).getAccount();
  }
  
  /**
   * 
   * @return the related event
   */
  ClientEvent getEvent() {
    return ((EventActivity) getActivity()).getEvent();
  }
  
  /**
   * Change the related event
   * 
   * @param e
   *          new event
   */
  void setEvent(final ClientEvent e) {
    ((EventActivity) getActivity()).setEvent(e);
  }
  
  @Override public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    thisOne = getActivity();
  }
  
  @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View $ = inflater.inflate(R.layout.ji_frag_event_info, container, false);
    eventName = (TextView) $.findViewById(R.id.eventTitle);
    eventAddress = (TextView) $.findViewById(R.id.eventAddress);
    eventDate = (TextView) $.findViewById(R.id.eventDate);
    eventDesc = (TextView) $.findViewById(R.id.eventDesc);
    joinImgBtn = (ImageButton) $.findViewById(R.id.joinImgBtn);
    //directionsButton = (ImageButton) $.findViewById(R.id.directionsButton);
    eventCat = (ImageView) $.findViewById(R.id.eventCat);
    pb = (ProgressBar) $.findViewById(R.id.progressBar);
    setEventDetails(getEvent());
    return $;
  }
  
  /**
   * Fill screen with the event's details
   * 
   * @param e
   *          an event
   */
  void setEventDetails(final ClientEvent e) {
    eventCat.setImageDrawable(e.getEventType().getDrawable(getActivity()));
    eventName.setText(e.getName());
    eventAddress.setText(e.getAddress());
    eventDate.setText(new StringBuilder(e.getWhen().toString()).append(",\t  ").append(e.getWhen().printTime()).toString());
    eventDesc.setText(e.getDescription());
    setEventsButtons();
  }
  
  /**
   * Set on click actions for the layout buttons 
   * */
  /** prev ver   
//  void setEventsButtons() {
//	  //currently no support for directions
//	  ////////////////////////////////////////////
////    directionsButton.setOnClickListener(new OnClickListener() {
////      @Override public void onClick(final View v) {
////        new DirectionsDialog(getActivity(), getEvent()).show();
////      }
////    });
//
//	  //if owner those options moved to the action bar 
////    if (getAccount().getUsername().equals(getEvent().getOwner())) {
////      // this is the owner
////      eventO1.setOnClickListener(new OnClickListener() {
////        @Override public void onClick(final View v) {
////          startActivityForResult(
////              new Intent(thisOne, CreateEventActivity.class).putExtra("account", getAccount()).putExtra("event",
////                  new ClientEvent(getEvent())), EDITED);
////        }
////      });
////      eventO2.setOnClickListener(new OnClickListener() {
////        @Override public void onClick(final View v) {
////          cancelEvent();
////        }
////      });
////    } else {
//     
//	  // not owner
//      //eventO1.setVisibility(View.GONE);
////      final boolean b = !getEvent().getUsers().contains(getAccount().toFacebookUser());
////      eventO2.setImageDrawable(getResources().getDrawable(b ? R.drawable.ji_join : R.drawable.ji_leave));
////      eventO2.setOnClickListener(b ? new OnClickListener() {
////        @Override public void onClick(final View v) {
////          joinEvent();
////        }
////      } : new OnClickListener() {
////        @Override public void onClick(final View v) {
////          leaveEvent();
////        }
////      });
//    //}
//  } */

  private void setEventsButtons(){
	  if(getEvent().getOwner().equals(getAccount().getUsername())){ 
		  return;
	  }
	  final boolean b = !getEvent().getUsers().contains(getAccount().toFacebookUser());
		joinImgBtn.setImageDrawable(getResources().getDrawable(b ? R.drawable.ji_join : R.drawable.ji_leave));
		joinImgBtn.setOnClickListener(b ? new OnClickListener() {
		@Override public void onClick(final View v) {
		  joinEvent();
		}
		} : new OnClickListener() {
		@Override public void onClick(final View v) {
		  leaveEvent();
		}
		});
  }
  
  void leaveEvent() {
    showProgressBar(joinImgBtn);
    ClientProxy.unattend(getAccount().getUsername(), getEvent().getId(), new OnDone<ClientEvent>() {
      @Override public void onDone(final ClientEvent e) {
    	//getActivity().setResult(MainActivity.RESULT_REMOVE_EVENT, new Intent().putExtra("event", e));
    	EventsDB.DB.leaveEvent(e);
    	hideProgressBar(joinImgBtn);
        Toast.makeText(thisOne, "You left this event", Toast.LENGTH_SHORT).show();
        setEvent(e);
        setEventDetails(getEvent());
        mCallback.onRefresh(EventActivity.TAB_EVENT_ATTENDING);
        new CalendarHandler(getActivity()).deleteEvent(getActivity(), e);
      }
    }, new OnError(getActivity()) {
      @Override public void beforeHandlingError() {
        hideProgressBar(joinImgBtn);
      }
    });
  }
  
  void joinEvent() {
    showProgressBar(joinImgBtn);
    ClientProxy.attend(getAccount().toFacebookUser(), getEvent().getId(), new OnDone<ClientEvent>() {
      @Override public void onDone(final ClientEvent e) {
    	//getActivity().setResult(MainActivity.RESULT_JOIN_EVENT, new Intent().putExtra("event", e));
    	EventsDB.DB.joinEevnt(e);
    	hideProgressBar(joinImgBtn);
        Toast.makeText(thisOne, "You joined this event!", Toast.LENGTH_SHORT).show();
        setEvent(e);
        setEventDetails(getEvent());
        mCallback.onRefresh(EventActivity.TAB_EVENT_ATTENDING);
        new CalendarHandler(getActivity()).setNewEvent(getActivity(), e, new CalendarHandler.Listener() {
          @Override public void onDone() {
            // Do nothing
          }
        });
      }
    }, new OnError(getActivity()) {
      @Override public void beforeHandlingError() {
        hideProgressBar(joinImgBtn);
      }
    });
  }
  
  void cancelEvent() {
    showProgressBar(joinImgBtn);
    ClientProxy.deleteEvent(getEvent().getId(), new OnDone<Void>() {
      @Override public void onDone(final Void t) {
        hideProgressBar(joinImgBtn);
        new CalendarHandler(getActivity()).deleteEvent(getActivity(), getEvent());
        getActivity().setResult(MainMapActivity.RESULT_DELETE, new Intent().putExtra("event", getEvent()));
        getActivity().finish();
      }
    }, new OnError(getActivity()) {
      @Override public void beforeHandlingError() {
        hideProgressBar(joinImgBtn);
      }
    });
  }
  
  @Override public void onAttach(final Activity a) {
    super.onAttach(a);
    mCallback = (OnTabRefresh) a;
  }
  
  @Override public void onRefresh() {
    setEventDetails(getEvent());
  }
  
  /**
   * Shows a progress bar instead of the 'V' image
   */
  private void showProgressBar(final View toReplace) {
    disableButtons();
    final ViewGroup p = (ViewGroup) toReplace.getParent();
    final int i = p.indexOfChild(toReplace);
    p.removeView(toReplace);
    pbView = getActivity().getLayoutInflater().inflate(R.layout.ji_refresh_layout, p, false);
    pbView.setLayoutParams(toReplace.getLayoutParams());
    p.addView(pbView, i);
  }
  
  /**
   * Hides the progress bar.
   */
  void hideProgressBar(final View toReplace) {
    enableButtons();
    final ViewGroup p = (ViewGroup) pbView.getParent();
    final int i = p.indexOfChild(pbView);
    p.removeView(pbView);
    p.addView(toReplace, i);
  }
  
  private void disableButtons() {
    //eventO1.setEnabled(false);
    joinImgBtn.setEnabled(false);
    //directionsButton.setEnabled(false);
  }
  
  private void enableButtons() {
    //eventO1.setEnabled(true);
    joinImgBtn.setEnabled(true);
   // directionsButton.setEnabled(true);
  }
}
