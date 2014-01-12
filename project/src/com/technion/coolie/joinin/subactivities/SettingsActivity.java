package com.technion.coolie.joinin.subactivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.FBClientAccount;
import com.technion.coolie.R;
import com.technion.coolie.joinin.map.MainMapActivity;

public class SettingsActivity extends PreferenceActivity {
  Context thisOne = this;
  FBClientAccount mLoggedAccount;
  
  @SuppressWarnings("deprecation") @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mLoggedAccount = (FBClientAccount) getIntent().getExtras().get("account");
    // Add 'general' preferences - calendar prefrences
    addPreferencesFromResource(R.xml.ji_pref_general);
    // Add 'notifications' preferences, and a corresponding header.
    final PreferenceCategory fakeHeader = new PreferenceCategory(this);
    fakeHeader.setTitle(R.string.pref_header_notifications);
    getPreferenceScreen().addPreference(fakeHeader);
    addPreferencesFromResource(R.xml.ji_pref_notification);
    // Bind the summaries of EditText/List/Dialog/Ringtone preferences to
    // their values. When their values change, their summaries are updated
    // to reflect the new value, per the Android Design guidelines.
    bindPreferenceSummaryToValue(findPreference("all_notifications_ringtone"));
    final ListView l = getListView();
    final Button setMyFavButoon = new Button(this);
    setMyFavButoon.setText("Set My Favorite Categories");
    setMyFavButoon.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        final Intent i = new Intent(thisOne, MyFavoritesActivity.class);
        i.putExtra("account", (FBClientAccount) getIntent().getExtras().get("account"));
        startActivityForResult(i, MainMapActivity.RESULT_FAVORITE);
      }
    });
    l.addFooterView(setMyFavButoon);
    final Button tutorialButton = new Button(this);
    tutorialButton.setText("HELP - Go to the Tutorial");
    tutorialButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        startActivity(new Intent(thisOne, TutorialActivity.class));
      }
    });
    l.addFooterView(tutorialButton);
    final Button aboutButton = new Button(this);
    aboutButton.setText("About");
    aboutButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View v) {
        AboutDialog.create(thisOne).show();
      }
    });
    l.addFooterView(aboutButton);
  }
  
  /**
   * initialize and creates the "about" dialog with links.
   * 
   * @author On
   * 
   */
  public static class AboutDialog {
    public static AlertDialog create(final Context context) {
      final TextView message = new TextView(context);
      final SpannableString s = new SpannableString(context.getText(R.string.dialog_message));
      Linkify.addLinks(s, Linkify.WEB_URLS);
      message.setText(s);
      message.setTextColor(Color.WHITE);
      message.setMovementMethod(LinkMovementMethod.getInstance());
      return new AlertDialog.Builder(context).setTitle("About").setCancelable(true).setIcon(android.R.drawable.ic_dialog_info)
          .setPositiveButton(android.R.string.ok, null).setView(message).create();
    }
  }
  
  /**
   * A preference value change listener that updates the preference's summary to
   * reflect its new value.
   */
  private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
    @Override public boolean onPreferenceChange(final Preference preference, final Object value) {
      if (preference instanceof ListPreference) {
        // For list preferences, look up the correct display value in
        // the preference's 'entries' list.
        final ListPreference listPreference = (ListPreference) preference;
        final int index = listPreference.findIndexOfValue(value.toString());
        // Set the summary to reflect the new value.
        preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
      } else if (preference instanceof RingtonePreference) {
        // For ringtone preferences, look up the correct display value
        // using RingtoneManager.
        if (TextUtils.isEmpty(value.toString()))
          // Empty values correspond to 'silent' (no ringtone).
          preference.setSummary(R.string.pref_ringtone_silent);
        else {
          final Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(value.toString()));
          if (ringtone == null)
            // Clear the summary if there was a lookup error.
            preference.setSummary(null);
          else
            // Set the summary to reflect the new ringtone display
            // name.
            preference.setSummary(ringtone.getTitle(preference.getContext()));
        }
      } else
        // For all other preferences, set the summary to the value's
        // simple string representation.
        preference.setSummary(value.toString());
      return true;
    }
  };
  
  /**
   * Binds a preference's summary to its value. More specifically, when the
   * preference's value is changed, its summary (line of text below the
   * preference title) is updated to reflect the value. The summary is also
   * immediately updated upon calling this method. The exact display format is
   * dependent on the type of preference.
   * 
   * @see #sBindPreferenceSummaryToValueListener
   */
  private static void bindPreferenceSummaryToValue(final Preference preference) {
    // Set the listener to watch for value changes.
    preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
    // Trigger the listener immediately with the preference's
    // current value.
    sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
        PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
  }
  
  @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != MainMapActivity.RESULT_FAVORITE)
      return;
    mLoggedAccount = (FBClientAccount) getIntent().getExtras().get("account");
    setResult(MainMapActivity.RESULT_FAVORITE, new Intent().putExtra("account", mLoggedAccount));
    finish();
  }
  
  @Override public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK)
      setResult(MainMapActivity.RESULT_FAVORITE, new Intent().putExtra("account", mLoggedAccount));
    return super.onKeyDown(keyCode, event);
  }
  
  /**
   * returns true if the Enable Calendar option on the settings is On false
   * otherwise
   * 
   * @param c
   * @return
   */
  public static boolean shouldUseCalendar(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("calendar_checkbox", true);
  }
  
  /**
   * returns true if the user enabled notifications at all. false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean EnabledNotifications(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("all_notifications", true);
  }
  
  /**
   * returns true if the user enabled the join notification. false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean isJoinEnabledNotifications(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("notifications_join", true);
  }
  
  /**
   * returns true if the user enabled the leave notification. false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean isLeaveEnabledNotifications(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("notifications_leave", true);
  }
  
  /**
   * returns true if the user enabled the update notification. false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean isUpdateEnabledNotifications(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("notifications_update", true);
  }
  
  /**
   * returns true if the user enabled the message notification. false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean isMessageEnabledNotifications(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("notifications_message", true);
  }
  
  /**
   * returns true if the user enabled the cancel notification. false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean isCancelEnabledNotifications(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("notifications_cancel", true);
  }
  
  /**
   * returns true if the user enabled viberate in the settings false otherwise
   * 
   * @param c
   * @return
   */
  public static boolean isVibarate(final Context c) {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("all_notifications_vibrate", true);
  }
}
