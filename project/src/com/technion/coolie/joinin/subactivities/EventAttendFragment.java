package com.technion.coolie.joinin.subactivities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.facebook.widget.ProfilePictureView;
import com.technion.coolie.joinin.GCMActions;
import com.technion.coolie.joinin.GCMIntentService;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.GCMMessage;
import com.technion.coolie.joinin.data.TeamAppFacebookEvent;
import com.technion.coolie.joinin.facebook.FacebookQueries;
import com.technion.coolie.joinin.facebook.FacebookQueries.OnGetEventAttendeesReturns;
import com.technion.coolie.joinin.facebook.FacebookUser;
import com.technion.coolie.joinin.facebook.PublishToFeed;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class EventAttendFragment extends Fragment implements OnFragmentRefresh {
  TextView eventComing;
  LinearLayout attendingList;
  ProgressBar pb;
  private ImageButton showMore;
  //EditText searchBox;
  Integer attendees;
  TeamAppFacebookEvent facebookEvent;
  List<FacebookUser> attending;
  //ImageButton importButton;
 // private ImageButton shareButton;
  //private ImageButton refreshButton;
  //private ImageButton searchButton;
  private View pbView;
  static boolean isSearch = false;
  static int LIST_INDEX = 0;
  private static final int LIST_ADD_SIZE = 10;
  private static final int MAX_LIST_SIZE = 30;
  
  /**
   * 
   * @return the related event
   */
  ClientEvent getEvent() {
    try {
      return ((EventActivity) getActivity()).getEvent();
    } catch (final NullPointerException e) {
      return null;
    }
  }
  
  /**
   * 
   * @return the logged account
   */
  ClientAccount getAccount() {
    try {
      return ((EventActivity) getActivity()).getAccount();
    } catch (final NullPointerException e) {
      return null;
    }
  }
  
  @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View $ = inflater.inflate(R.layout.ji_frag_event_attend, container, false);
    eventComing = (TextView) $.findViewById(R.id.eventComingAtt);
    pb = (ProgressBar) $.findViewById(R.id.progressBar);
    attendingList = (LinearLayout) $.findViewById(R.id.attendingList);
   // searchBox = (EditText) $.findViewById(R.id.search_box);
    showMore = (ImageButton) $.findViewById(R.id.showMore);
//    shareButton = (ImageButton) $.findViewById(R.id.shareButton);
//    importButton = (ImageButton) $.findViewById(R.id.importButton);
//    refreshButton = (ImageButton) $.findViewById(R.id.refreshButton);
//    searchButton = (ImageButton) $.findViewById(R.id.searchButton);
    setListeners(inflater);
//    if (getAccount().getUsername().equals(getEvent().getOwner()))
//      getFacebookEvent(importButton);
    LIST_INDEX = 0;
    createList();
    return $;
  }
  
  /**
   * Sets button listeners
   * 
   * @param inflater
   *          a layout inflater
   */
  private void setListeners(final LayoutInflater inflater) {
    showMore.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        showMore(getEvent(), inflater);
      }
    });
//    shareButton.setOnClickListener(new View.OnClickListener() {
//      @Override public void onClick(final View view) {
//        new ShareDialog(getActivity()).show();
//      }
//    });
//    importButton.setOnClickListener(new View.OnClickListener() {
//      @Override public void onClick(final View view) {
//        new AlertDialog.Builder(getActivity()).setMessage(R.string.import_question)
//            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//              @Override public void onClick(final DialogInterface dialog, final int which) {
//                importFacebookAttendees();
//              }
//            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//              @Override public void onClick(final DialogInterface dialog, final int which) {
//                // do nothing...
//              }
//            }).create().show();
//      }
//    });
//    refreshButton.setOnClickListener(new OnClickListener() {
//      @Override public void onClick(final View v) {
//        onRefresh();
//      }
//    });
//    searchBox.setOnEditorActionListener(new OnEditorActionListener() {
//      @Override public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
//        if (actionId != EditorInfo.IME_ACTION_SEARCH)
//          return false;
//        performSearch(inflater);
//        return true;
//      }
//    });
//    searchButton.setOnClickListener(new OnClickListener() {
//      @Override public void onClick(final View v) {
//        performSearch(inflater);
//      }
//    });
  }
  
  /**
   * Handles the search menu (sets visibility for the search box) and performs
   * the search
   * 
   * @param inflater
   *          a layout inflater
   */
//  void performSearch(final LayoutInflater inflater) {
//    if (searchBox.getVisibility() == View.VISIBLE)
//      searchAttendee(getEvent(), inflater, searchBox.getText().toString().trim());
//    searchBox.setVisibility(View.VISIBLE);
//  }
  
  /**
   * Generates the list of attendees.
   */
  private void createList() {
    isSearch = false;
    //searchBox.setVisibility(View.GONE);
    //searchBox.setText("");
    pb.setVisibility(View.VISIBLE);
    final ClientEvent e = getEvent(); // Should not be inlined
    if (e != null)
      createListAux(e);
  }
  
  /**
   * Displays more attendees on screen
   * 
   * @param e
   *          the current event
   * @param owner
   *          the owner of the event
   * @param inflater
   *          a layout inflater
   */
  void showMore(final ClientEvent e, final LayoutInflater inflater) {
    pb.setVisibility(View.VISIBLE);
    if (attendingList.getChildCount() > MAX_LIST_SIZE)
      attendingList.removeViews(1, LIST_ADD_SIZE + 1);
    for (int i = 0; LIST_INDEX < attending.size() && i < LIST_ADD_SIZE; LIST_INDEX++, i++)
      addAccount(e, inflater, attending.get(LIST_INDEX));
    pb.setVisibility(View.GONE);
    showMore.setVisibility(LIST_INDEX < attending.size() ? View.VISIBLE : View.GONE);
  }
  
  /**
   * Adds a user to the users list. Performs a server request.
   * 
   * @param e
   *          The current event
   * @param inflater
   *          a layout inflater
   * @param attendingList
   *          the main linear layout
   * @param name
   *          the user to add
   */
  void addAccount(final ClientEvent e, final LayoutInflater inflater, final FacebookUser u) {
    final View vi = inflater.inflate(R.layout.ji_members_list_in_event_activity, null);
    final TextView tv = (TextView) vi.findViewById(R.id.rowTextView);
    vi.setTag(u.getUsername());
    final StringBuilder sb = new StringBuilder(u.getFullname());
    if (u.getUsername().equals(e.getOwner())) {
      sb.append("\t\t(Owner)");
      tv.setTextColor(Color.parseColor("#562e92"));
    }
    tv.setText(sb.toString());
    ((ProfilePictureView) vi.findViewById(R.id.profile_pic)).setProfileId(u.getUsername());
    attendingList.addView(vi);
    attendingList.addView(inflater.inflate(R.layout.ji_gray_line, null));
  }
  
  @Override public void onRefresh() {
    createList();
  }
  
  @Override public void onResume() {
    super.onResume();
    final IntentFilter filterAttend = new IntentFilter(GCMActions.ACTION_ATTEND);
    filterAttend.setPriority(GCMIntentService.PRIORITY_BEFORE_DEFAULT_BROADCAST);
    getActivity().registerReceiver(attendReceiver, filterAttend);
    final IntentFilter filterUnattend = new IntentFilter(GCMActions.ACTION_UNATTEND);
    filterUnattend.setPriority(GCMIntentService.PRIORITY_BEFORE_DEFAULT_BROADCAST);
    getActivity().registerReceiver(unattendReceiver, filterUnattend);
  }
  
  @Override public void onPause() {
    getActivity().unregisterReceiver(attendReceiver);
    getActivity().unregisterReceiver(unattendReceiver);
    super.onPause();
  }
  
  /**
   * Sets the title which displayes how many people are attending the event.
   */
  @SuppressWarnings("boxing") void setTitle() {
    if (attendees > 1) {
      final StringBuilder sb = new StringBuilder(String.valueOf(attendees));
      sb.append(" people are already coming!");
      eventComing.setText(sb.toString());
    }
    eventComing.setVisibility(View.VISIBLE);
  }
  
  private void getFacebookEvent(final ImageButton b) {
    facebookEvent = null;
    ClientProxy.getFacebookEvent(getEvent().getId(), new OnDone<TeamAppFacebookEvent>() {
      @Override public void onDone(final TeamAppFacebookEvent e) {
        facebookEvent = e;
        if (facebookEvent != null)
          b.setVisibility(View.VISIBLE);
      }
    }, null);
  }
  
  /**
   * Import attendees from the event on facebook
   */
//  @SuppressWarnings("boxing") void importFacebookAttendees() {
//    if (facebookEvent == null)
//      return;
//    showProgressBar(importButton);
//    FacebookQueries.getEventAttendees(facebookEvent.getFacebookId(), getActivity(), new OnGetEventAttendeesReturns() {
//      @Override public void onGetEventAttendeesReturns(final List<FacebookUser> users) {
//        final ClientEvent e = getEvent(); // Should not be inlined!
//        boolean modified = false;
//        final Set<FacebookUser> $ = e.getUsers();
//        modified = $.addAll(users);
//        e.setUsers($);
//        if (modified)
//          ClientProxy.modifyEvent(e, new OnDone<Boolean>() {
//            @Override public void onDone(final Boolean b) {
//              hideProgressBar(importButton);
//              onRefresh();
//            }
//          }, new OnError(getActivity()) {
//            @Override public void beforeHandlingError() {
//              hideProgressBar(importButton);
//            }
//          });
//      }
//    });
//  }
//  
  /**
   * Search the given string as a name of an attendee
   * 
   * @param e
   *          the current event
   * @param inflater
   *          a layout inflater
   * @param s
   *          a string to search
   */
//  void searchAttendee(final ClientEvent e, final LayoutInflater inflater, final String s) {
//    isSearch = true;
//    attendingList.removeAllViews();
//    showMore.setVisibility(View.GONE);
//    showProgressBar(searchButton);
//    attending = new ArrayList<FacebookUser>(e.getUsers());
//    LIST_INDEX = 0;
//    for (; LIST_INDEX < attending.size() && attendingList.getChildCount() < MAX_LIST_SIZE; LIST_INDEX++)
//      if (nameContains(s, attending.get(LIST_INDEX).getFullname()))
//        addAccount(e, inflater, attending.get(LIST_INDEX));
//    if (attendingList.getChildCount() <= 0) {
//      final View v = inflater.inflate(R.layout.ji_members_list_in_event_activity, null);
//      ((TextView) v.findViewById(R.id.rowTextView)).setText(R.string.no_results);
//      v.findViewById(R.id.profile_pic).setVisibility(View.GONE);
//      attendingList.addView(v);
//    }
//    hideProgressBar(searchButton);
//  }
  
  /**
   * Checks whether a name contains token of a given string
   * 
   * @param toSearch
   *          a string containing tokens
   * @param name
   *          A name of a user
   * @return true iff all tokens are contained in the string
   */
  private static boolean nameContains(final String toSearch, final String name) {
    for (final String str : toSearch.split("\\s+"))
      if (!name.toLowerCase(Locale.getDefault()).contains(str.toLowerCase(Locale.getDefault())))
        return false;
    return true;
  }
  
  @SuppressWarnings("boxing") void createListAux(final ClientEvent e) {
    pb.setVisibility(View.GONE);
    attendees = e.getConfirmed();
    setTitle();
    if (getActivity() == null)
      return;
    final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    attending = new ArrayList<FacebookUser>(e.getUsers());
    Collections.sort(attending, new Comparator<FacebookUser>() {
      private boolean isOwner(final FacebookUser u) {
        return u.getUsername().equals(e.getOwner());
      }
      
      @Override public int compare(final FacebookUser lhs, final FacebookUser rhs) {
        if (isOwner(lhs) && !isOwner(rhs))
          return -1;
        if (!isOwner(lhs) && isOwner(rhs))
          return 1;
        return lhs.compareTo(rhs);
      }
    });
    LIST_INDEX = 0;
    attendingList.removeAllViews();
    showMore(e, inflater);
  }
  
  private class ShareDialog extends Dialog {
    public ShareDialog(final Context context) {
      super(context);
    }
    
    @Override protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
      getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
      setContentView(R.layout.ji_share_dialog);
      findViewById(R.id.share_facebook).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(final View v) {
          dismiss();
          boolean res;
          try {
            res = PublishToFeed.publishFeedDialog(getEvent(), getActivity(),
                createShareUrl(getAccount().getName(), getEvent().getId()));
          } catch (final UnsupportedEncodingException e) {
            res = PublishToFeed.publishFeedDialog(getEvent(), getActivity());
          }
          if (!res)
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1);
        }
      });
      findViewById(R.id.share_other).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(final View v) {
          dismiss();
          generalShare();
        }
      });
    }
    
    void generalShare() {
      try {
        startActivity(new Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                String.format(getString(R.string.invite_text), getEvent().getName(),
                    createShareUrl(getAccount().getName(), getEvent().getId())))
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.invite_subject)));
      } catch (final Exception e) {
        return;
      }
    }
  }
  
  /**
   * A broadcast receiver used as a listener for when a new user attends this
   * event. The view will update according to the new user.
   */
  private final BroadcastReceiver attendReceiver = new BroadcastReceiver() {
    @SuppressWarnings("boxing") @Override public void onReceive(final Context context, final Intent intent) {
      if (isSearch)
        return;
      final GCMMessage m = GCMMessage.fromJson(intent.getExtras().getString("message"));
      if (getEvent().getId() != m.getEvent().getId())
        return;
      addAccount(new ClientEvent(m.getEvent()), (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
          new ClientAccount(m.getAccount()).toFacebookUser());
      synchronized (attendees) {
        attendees += 1;
      }
      setTitle();
      abortBroadcast();
    }
  };
  /**
   * A broadcast receiver used as a listener for when a new user leaves this
   * event. The view will update according to the new user.
   */
  private final BroadcastReceiver unattendReceiver = new BroadcastReceiver() {
    @Override public void onReceive(final Context context, final Intent intent) {
      if (isSearch)
        return;
      final GCMMessage m = GCMMessage.fromJson(intent.getExtras().getString("message"));
      if (getEvent().getId() != m.getEvent().getId())
        return;
      attendingList.removeView(attendingList.findViewWithTag(new ClientAccount(m.getAccount()).getUsername()));
      abortBroadcast();
    }
  };
  
  @Override public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    FacebookQueries.onResult(getActivity(), requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }
  
  /**
   * @return the share url of the current event
   * @throws UnsupportedEncodingException
   */
  public static String createShareUrl(final String inviter, final long event) throws UnsupportedEncodingException {
    return new StringBuilder(ClientProxy.getBaseAddress()).append("/invite?inviter=").append(URLEncoder.encode(inviter, "UTF-8"))
        .append("&eventid=").append(event).toString();
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
    //importButton.setEnabled(false);
   // shareButton.setEnabled(false);
    showMore.setEnabled(false);
    //searchButton.setEnabled(false);
    //refreshButton.setEnabled(false);
  }
  
  private void enableButtons() {
    //importButton.setEnabled(true);
    //shareButton.setEnabled(true);
    showMore.setEnabled(true);
    //searchButton.setEnabled(true);
    //refreshButton.setEnabled(true);
  }
}
