package com.technion.coolie.joinin.subactivities;

import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.technion.coolie.joinin.GCMActions;
import com.technion.coolie.joinin.GCMIntentService;
import com.technion.coolie.FBClientAccount;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.EventDate;
import com.technion.coolie.joinin.data.EventMessage;
import com.technion.coolie.joinin.data.GCMMessage;

public class EventMessagesFragment extends Fragment implements OnFragmentRefresh {
  ImageButton sendButton;
  EditText messageInput;
  LinearLayout messageList;
  LinearLayout enterText;
  ScrollView scroll;
  private static final int ENTRY_REFRESH = Menu.FIRST;
  
  /**
   * 
   * @return the logged account
   */
  FBClientAccount getAccount() {
    return ((EventActivity) getActivity()).getAccount();
  }
  
  /**
   * 
   * @return the related event
   */
  ClientEvent getEvent() {
    return ((EventActivity) getActivity()).getEvent();
  }
  
  @Override public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }
  
  @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View $ = inflater.inflate(R.layout.ji_frag_event_message, container, false);
    messageInput = (EditText) $.findViewById(R.id.messageInput);
    sendButton = (ImageButton) $.findViewById(R.id.sendButton);
    messageList = (LinearLayout) $.findViewById(R.id.messageList);
    enterText = (LinearLayout) $.findViewById(R.id.enterText);
    scroll = (ScrollView) $.findViewById(R.id.scrollList);
    sendButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        final String text = messageInput.getText().toString();
        if (text.trim().length() <= 0) {
          messageInput.setError(getString(R.string.error_enter_message));
          return;
        }
        final EventMessage m = new EventMessage(getEvent().getId(), getAccount().getUsername(), getAccount().getFacebookId(),
            getAccount().getName(), text, new EventDate(Calendar.getInstance().getTimeInMillis()));
        if (getActivity() == null)
          return;
        final View addedView = addMessage(m);
        ClientProxy.addMessage(m, new OnDone<Long>() {
          @Override public void onDone(final Long id) {
            messageInput.setText("");
          }
        }, new OnError(getActivity()) {
          @Override public void beforeHandlingError() {
            messageList.removeView(addedView);
          }
        });
        scrollDown();
      }
    });
    onRefresh();
    return $;
  }
  
  View addMessage(final EventMessage m) {
    if (getActivity() == null)
      return null;
    final View $ = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
        R.layout.ji_message_list, null);
    ((ProfilePictureView) $.findViewById(R.id.profile_pic)).setProfileId(m.getfId());
    ((TextView) $.findViewById(R.id.messageUsername)).setText(m.getName());
    final StringBuilder sb = new StringBuilder(m.getDate().printShortDate());
    sb.append(",  ");
    sb.append(m.getDate().printTime());
    ((TextView) $.findViewById(R.id.messageHeader)).setText(sb);
    ((TextView) $.findViewById(R.id.messageContent)).setText(m.getText());
    if (m.getUsername().equals(getAccount().getUsername()))
      setDeleteListener(m, $);
    messageList.addView($);
    return $;
  }
  
  /**
   * Enable to delete the message
   * 
   * @param m
   *          the message to delete
   * @param v
   *          the view related to this message
   */
  private void setDeleteListener(final EventMessage m, final View v) {
    v.setOnLongClickListener(new OnLongClickListener() {
      @Override public boolean onLongClick(final View v1) {
        new AlertDialog.Builder(getActivity()).setTitle("Do you wish to delete this message?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override public void onClick(final DialogInterface dialog, final int which) {
                final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Loading...");
                pd.setCancelable(false);
                ClientProxy.deleteMessage(m.getMessageId(), new OnDone<Void>() {
                  @Override public void onDone(final Void t) {
                    pd.dismiss();
                    messageList.removeView(v1);
                  }
                }, new OnError(getActivity()) {
                  @Override public void beforeHandlingError() {
                    pd.dismiss();
                  }
                });
              }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
              @Override public void onClick(final DialogInterface dialog, final int which) {
                // Do nothing...
              }
            }).create().show();
        return true;
      }
    });
  }
  
  @Override public void onRefresh() {
    ClientProxy.getMessages(getEvent().getId(), new OnDone<List<EventMessage>>() {
      @Override public void onDone(final List<EventMessage> ms) {
        messageList.removeAllViews();
        for (final EventMessage m : ms)
          if (addMessage(m) == null)
            break;
        scrollDown();
      }
    }, new OnError(getActivity()) {
      @Override public void beforeHandlingError() {
        // Do nothing...
      }
    });
  }
  
  @Override public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    menu.add(0, ENTRY_REFRESH, 0, "Refresh");
  }
  
  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() == ENTRY_REFRESH) {
      onRefresh();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
  
  @Override public void onResume() {
    super.onResume();
    if (!getEvent().getUsers().contains(getAccount().getUsername()))
      return;
    final IntentFilter f = new IntentFilter(GCMActions.ACTION_ADD_MESSAGE);
    f.setPriority(GCMIntentService.PRIORITY_BEFORE_DEFAULT_BROADCAST);
    getActivity().registerReceiver(messageReceiver, f);
  }
  
  @Override public void onPause() {
    if (getEvent().getUsers().contains(getAccount().getUsername()))
      getActivity().unregisterReceiver(messageReceiver);
    super.onPause();
  }
  
  /**
   * Scroll down the message list
   */
  void scrollDown() {
    scroll.post(new Runnable() {
      @Override public void run() {
        scroll.fullScroll(View.FOCUS_DOWN);
      }
    });
  }
  
  private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
    @Override public void onReceive(final Context context, final Intent intent) {
      Log.w("MessageFragment", "received message");
      final EventMessage m = EventMessage.toEventMessage(GCMMessage.fromJson(intent.getExtras().getString("message")).getMessage());
      if (m.getEventId() != getEvent().getId() || m.getUsername().equals(getAccount().getUsername()))
        return;
      addMessage(m);
      scrollDown();
      abortBroadcast();
    }
  };
}
