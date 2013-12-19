package com.technion.coolie.joinin.subactivities;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class MyEventsAttendingFragment extends Fragment implements OnFragmentRefresh {
  LinearLayout scrollList;
  
  @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View $ = inflater.inflate(R.layout.ji_frag_my_events, container, false);
    scrollList = (LinearLayout) $.findViewById(R.id.scrollList);
    onRefresh();
    return $;
  }
  
  /**
   * 
   * @return the logged account
   */
  ClientAccount getAccount() {
    return ((MyEventsActivity) getActivity()).getAccount();
  }
  
  @Override public void onRefresh() {
    final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Loading...");
    pd.setCancelable(false);
    ClientProxy.getEventsAttending(getAccount().getUsername(), new OnDone<List<ClientEvent>>() {
      @Override public void onDone(final List<ClientEvent> es) {
        pd.dismiss();
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scrollList.removeAllViews();
        for (final ClientEvent e : es) {
          if (getAccount().getUsername().equals(e.getOwner()))
            continue;
          final View v = inflater.inflate(R.layout.ji_frag_my_events_single, null);
          ((TextView) v.findViewById(R.id.rowTextView)).setText(e.getName());
          ((ImageView) v.findViewById(R.id.eventIcon)).setImageDrawable(e.getEventType().getDrawable(getActivity()));
          v.setOnClickListener(new OnClickListener() {
            @Override public void onClick(final View v1) {
              startActivityForResult(
                  new Intent(getActivity(), EventActivity.class).putExtra("event", e).putExtra("account", getAccount()), 1);
            }
          });
          scrollList.addView(v);
          scrollList.addView(inflater.inflate(R.layout.ji_gray_line, null));
        }
      }
    }, new OnError(getActivity()) {
      @Override public void beforeHandlingError() {
        pd.dismiss();
      }
    });
  }
}
