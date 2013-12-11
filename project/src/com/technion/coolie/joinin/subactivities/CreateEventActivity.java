package com.technion.coolie.joinin.subactivities;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

/**
 * 
 * @author Shimon Kama
 * 
 */
public class CreateEventActivity extends FragmentActivity {
  final FragmentActivity thisActivity = this;
  static final int DATE_DIALOG_ID = 999;
  static final int TIME_DIALOG_ID = 998;
  ClientAccount mAccount;
  ImageView catImage;
  EditText titleInput;
  EditText locationInput;
  EditText invitedInput;
  EditText descriptionInput;
  TextView charsCount;
  ImageButton importButton;
  View pb;
  private TextView dateInput;
  private TextView timeInput;
  ImageButton createEvent;
  EventDate date;
  EventType type = EventType.OTHER;
  long latitude;
  long longtitude;
  private Spinner catSpinner;
  private ArrayAdapter<CharSequence> catAdapter;
  Long eventFBid;
  private static final int MAX_DESCRIPTION = 400;
  private static final int MAX_TITLE = 50;
  private static final int MAX_LOCATION = 50;
  
  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    setContentView(inflater.inflate(R.layout.ji_create_event, null));
    eventFBid = null;
    mAccount = (ClientAccount) getIntent().getExtras().get("account");
    catImage = (ImageView) findViewById(R.id.eventCat);
    titleInput = (EditText) findViewById(R.id.eventTitleInput);
    locationInput = (EditText) findViewById(R.id.eventLocationInput);
    invitedInput = (EditText) findViewById(R.id.eventParticInput);
    descriptionInput = (EditText) findViewById(R.id.eventDescriptionInput);
    charsCount = (TextView) findViewById(R.id.charsCount);
    dateInput = (TextView) findViewById(R.id.eventDateInput);
    timeInput = (TextView) findViewById(R.id.eventTimeInput);
    importButton = (ImageButton) findViewById(R.id.importButton);
    setupSpinner();
    createEvent = (ImageButton) findViewById(R.id.createButton);
    latitude = getIntent().getExtras().getInt("Latitude");
    longtitude = getIntent().getExtras().getInt("Longtitude");
    date = new EventDate(Calendar.getInstance().getTimeInMillis());
    updateListeners();
  }
  
  /**
   * create button listeners
   */
  private void updateListeners() {
    updateDateOnView();
    charsCount.setText("0 / " + MAX_DESCRIPTION);
    descriptionInput.addTextChangedListener(new TextWatcher() {
      @Override public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        charsCount.setText(s.length() + " / " + MAX_DESCRIPTION);
        charsCount.setTextColor(s.length() > MAX_DESCRIPTION ? Color.parseColor("#ff0000") : Color.parseColor("#333333"));
      }
      
      @Override public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        // do nothing
      }
      
      @Override public void afterTextChanged(final Editable s) {
        // do nothing
      }
    });
    dateInput.setOnClickListener(new OnClickListener() {
      @SuppressWarnings("deprecation") @Override public void onClick(final View v) {
        showDialog(DATE_DIALOG_ID);
      }
    });
    timeInput.setOnClickListener(new OnClickListener() {
      @SuppressWarnings("deprecation") @Override public void onClick(final View v) {
        showDialog(TIME_DIALOG_ID);
      }
    });
    final ClientEvent e = (ClientEvent) getIntent().getExtras().get("event");
    if (e != null)
      showDetails(e);
    createEvent.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        pressOk(e);
      }
    });
    importButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        createImportDialog();
      }
    });
  }
  
  private void setupSpinner() {
    catSpinner = (Spinner) findViewById(R.id.categorySpinner);
    final String[] data = new String[EventType.values().length];
    for (int i = 0; i < data.length; i++)
      data[i] = EventType.values()[i].toString();
    catAdapter = new ArrayAdapter<CharSequence>(this, R.layout.ji_cat_spinner, R.id.rowTextView, data);
    catSpinner.setAdapter(catAdapter);
    catSpinner.setSelection(EventType.OTHER.ordinal());
    catSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override public void onItemSelected(final AdapterView<?> parent, final View v, final int pos, final long row) {
        type = EventType.values()[pos];
        catImage.setImageDrawable(type.getDrawable(thisActivity));
      }
      
      @Override public void onNothingSelected(final AdapterView<?> arg0) {
        // Do nothing...
      }
    });
  }
  
  private void showDetails(final ClientEvent e) {
    date = e.getWhen();
    latitude = e.getLatitude();
    longtitude = e.getLongitude();
    titleInput.setText(e.getName());
    locationInput.setText(e.getAddress());
    invitedInput.setText(String.valueOf(e.getInvited()));
    descriptionInput.setText(e.getDescription());
    catSpinner.setSelection(e.getEventType().ordinal());
    updateDateOnView();
  }
  
  void modifyEvent(final ClientEvent e) {
    showProgressBar(createEvent);
    ClientProxy.modifyEvent(e, new OnDone<Boolean>() {
      @Override public void onDone(final Boolean t) {
        hideProgressBar(createEvent);
        try {
          new CalendarHandler(thisActivity).updateEvent(thisActivity, e);
        } catch (final NotFoundException nfe) {
          // Do nothing...
        }
        setResult(MainMapActivity.RESULT_REFRESH, new Intent().putExtra("event", e));
        finish();
      }
    }, new OnError(this) {
      @Override public void beforeHandlingError() {
        hideProgressBar(createEvent);
      }
    });
  }
  
  void addNewEvent(final ClientEvent e) {
    showProgressBar(createEvent);
    ClientProxy.addEvent(e, new TeamAppFacebookEvent(e.getId(), eventFBid != null ? eventFBid.longValue() : -1),
        new ClientProxy.OnDone<Long>() {
          @SuppressWarnings("boxing") @Override public void onDone(final Long i) {
            hideProgressBar(createEvent);
            e.setId(i);
            new CalendarHandler(thisActivity).setNewEvent(thisActivity, e, new CalendarHandler.Listener() {
              @Override public void onDone() {
                setResult(MainMapActivity.RESULT_REFRESH, new Intent().putExtra("event", e));
                finish();
              }
            });
          }
        }, new OnError(this) {
          @Override public void beforeHandlingError() {
            hideProgressBar(createEvent);
          }
        });
  }
  
  /**
   * Shows a progress bar instead of the 'V' image
   */
  private void showProgressBar(final View toReplace) {
    importButton.setEnabled(false);
    createEvent.setEnabled(false);
    final ViewGroup p = (ViewGroup) toReplace.getParent();
    final int i = p.indexOfChild(toReplace);
    p.removeView(toReplace);
    pb = getLayoutInflater().inflate(R.layout.ji_refresh_layout, p, false);
    pb.setLayoutParams(toReplace.getLayoutParams());
    p.addView(pb, i);
  }
  
  /**
   * Hides the progress bar.
   */
  void hideProgressBar(final View toReplace) {
    importButton.setEnabled(true);
    createEvent.setEnabled(true);
    final ViewGroup p = (ViewGroup) pb.getParent();
    final int i = p.indexOfChild(pb);
    p.removeView(pb);
    p.addView(toReplace, i);
  }
  
  @Override protected Dialog onCreateDialog(final int id) {
    switch (id) {
      case DATE_DIALOG_ID:
        return new DatePickerDialog(this, datePickerListener, date.getYear(), date.getMonth() - 1, date.getDay());
      case TIME_DIALOG_ID:
        return new TimePickerDialog(this, timePickerListener, date.getHour(), date.getMinute(), true);
      default:
        return null;
    }
  }
  
  private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
    // when dialog box is closed, below method will be called.
    @Override public void onDateSet(final DatePicker view, final int selectedYear, final int selectedMonth, final int selectedDay) {
      date.setYear(selectedYear);
      date.setMonth(selectedMonth + 1);
      date.setDay(selectedDay);
      updateDateOnView();
    }
  };
  private final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
    @Override public void onTimeSet(final TimePicker view, final int selectedHour, final int selectedMinute) {
      date.setHour(selectedHour);
      date.setMinute(selectedMinute);
      updateDateOnView();
    }
  };
  
  void updateDateOnView() {
    dateInput.setText(date.toString());
    timeInput.setText(date.printTime());
  }
  
  @Override public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (keyCode != KeyEvent.KEYCODE_BACK || event.getRepeatCount() != 0)
      return super.onKeyDown(keyCode, event);
    setResult(MainMapActivity.RESULT_DO_NOTHING);
    finish();
    return true;
  }
  
  /**
   * 
   * @return true iff all input paramaters don't cross the characters limit
   */
  private boolean checkParams() {
    return checkParamsField(titleInput, MAX_TITLE) && checkParamsField(descriptionInput, MAX_DESCRIPTION)
        && checkParamsField(locationInput, MAX_LOCATION) && checkEmpty(titleInput) && checkEmpty(locationInput);
  }
  
  /**
   * Checks the edit text for a limited size
   * 
   * @param t
   *          an edit text
   * @param size
   *          limited size of characters
   * @return true iff the length of the string is no greater than size
   */
  @SuppressWarnings("boxing") private boolean checkParamsField(final EditText t, final int size) {
    if (t.getText().length() <= size)
      return true;
    t.setError(String.format(getString(R.string.text_overflow), size));
    return false;
  }
  
  /**
   * Check if the given text is empty
   * 
   * @param t
   *          an edit text
   * @return true iff the edit text contains an empty string
   */
  private boolean checkEmpty(final EditText t) {
    if (t.getText().toString().trim().length() >= 0)
      return true;
    t.setError(getString(R.string.text_empty));
    return false;
  }
  
  /**
   * Deals with the press on the ok button
   * 
   * @param e
   *          The event to add or modify
   */
  void pressOk(final ClientEvent e) {
    if (!checkParams())
      return;
    final ClientEvent toAdd = new ClientEvent(0, titleInput.getText().toString().trim(), locationInput.getText().toString().trim(),
        descriptionInput.getText().toString().trim(), latitude, longtitude, date, invitedInput.getText().length() > 0 ? Long
            .valueOf(invitedInput.getText().toString()).longValue() : 0, type, mAccount.getUsername());
    toAdd.addUser(mAccount.toFacebookUser());
    if (e != null) {
      toAdd.setUsers(e.getUsers());
      toAdd.setId(e.getId());
      modifyEvent(toAdd);
    } else
      addNewEvent(toAdd);
  }
  
  @Override protected void onActivityResult(final int arg0, final int arg1, final Intent arg2) {
    FacebookQueries.onResult(this, arg0, arg1, arg2);
    super.onActivityResult(arg0, arg1, arg2);
  }
  
  /**
   * Fill the screen with the event data
   * 
   * @param fbe
   *          a Facebook event
   */
  void onEventChosen(final FacebookEvent fbe) {
    titleInput.setText(fbe.getEventName());
    descriptionInput.setText(fbe.getEventDescription());
    date = new EventDate(fbe.getEventTime().longValue());
    locationInput.setText(fbe.getEventAddress());
    updateDateOnView();
    eventFBid = fbe.getEventId();
  }
  
  /**
   * Creates a dialog which will allow the user to see his Facebook events.
   */
  public void createImportDialog() {
    showProgressBar(importButton);
    if (!FacebookQueries.getUserEvents(this, new OnGetUserEventsReturns() {
      @Override public void onGetUserEventsReturns(final List<FacebookEvent> events) {
        hideProgressBar(importButton);
        if (events.size() <= 0) {
          Toast.makeText(thisActivity, "You have no Facebook events", Toast.LENGTH_LONG).show();
          return;
        }
        final String[] names = new String[events.size()];
        for (int i = 0; i < names.length; i++)
          names[i] = events.get(i).getEventName();
        new DialogFragment() {
          @Override public Dialog onCreateDialog(final Bundle savedInstanceState) {
            final AlertDialog $ = new AlertDialog.Builder(getActivity()).setTitle(R.string.import_title)
                .setItems(names, new Dialog.OnClickListener() {
                  @Override public void onClick(final DialogInterface dialog, final int i) {
                    onEventChosen(events.get(i));
                  }
                }).create();
            $.setCanceledOnTouchOutside(false);
            return $;
          }
        }.show(getSupportFragmentManager(), "importDialog");
      }
    })) {
      hideProgressBar(importButton);
      startActivityForResult(new Intent(this, LoginActivity.class), 1);
    }
  }
}
