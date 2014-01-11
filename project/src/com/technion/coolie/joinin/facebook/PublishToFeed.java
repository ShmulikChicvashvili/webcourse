package com.technion.coolie.joinin.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.technion.coolie.FacebookLogin;
import com.technion.coolie.joinin.data.ClientEvent;

/**
 * 
 * @author Ido
 * 
 */
public class PublishToFeed {
  /**
   * Publish an event to Facebook timeline.
   * 
   * @author Ido Gonen
   */
  public static boolean publishFeedDialog(final ClientEvent e, final Activity a, final String url) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    final Bundle params = new Bundle();
    params.putString("name", e.getName());
    params.putString("caption", new StringBuilder("in ").append(e.getAddress()).append(" | at ").append(e.getWhen().toString())
        .append(",\t").append(e.getWhen().printTime()).toString());
    params.putString("description", e.getDescription());
    params.putString("link", url);
    params.putString("picture", "http://s24.postimg.org/486eo22dd/ic_launcher.png");
    new WebDialog.FeedDialogBuilder(a, Session.getActiveSession(), params).setOnCompleteListener(new OnCompleteListener() {
      @Override public void onComplete(final Bundle values, final FacebookException error) {
        if (error == null) {
          // When the story is posted, echo the success
          // and the post Id.
          if (values.getString("post_id") != null)
            Toast.makeText(a, "Posted story", Toast.LENGTH_SHORT).show();
          else
            // User clicked the Cancel button
            Toast.makeText(a, "Publish cancelled", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FacebookOperationCanceledException)
          // User clicked the "x" button
          Toast.makeText(a, "Publish cancelled", Toast.LENGTH_SHORT).show();
        else
          // Generic, ex: network error
          Toast.makeText(a, "Error posting story", Toast.LENGTH_SHORT).show();
      }
    }).build().show();
    return true;
  }
  
  public static boolean publishFeedDialog(final ClientEvent e, final Activity a) {
    return publishFeedDialog(e, a, "https://play.google.com/store/apps/details?id=il.ac.technion.cs.cs234311.teamapp");
  }
}
