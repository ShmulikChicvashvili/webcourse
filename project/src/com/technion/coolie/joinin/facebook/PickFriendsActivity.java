package com.technion.coolie.joinin.facebook;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.technion.coolie.FacebookLogin;
import com.technion.coolie.R;
import com.technion.coolie.joinin.map.MainMapActivity;
import com.technion.coolie.joinin.subactivities.EventFilterActivity;
import com.technion.coolie.joinin.subactivities.LoginActivity;

/**
 * Taken from facebook samples. PickFriendsActivity is a fragment activity which
 * shows a list of facebook friends to choose from.
 * 
 * @author Ido
 * 
 */
public class PickFriendsActivity extends FragmentActivity {
  FriendPickerFragment friendPickerFragment;
  Context context = this;
  
  /**
   * Adds the desired parameters for the picker fragments activity.
   * 
   * @param intent
   *          - the intent to add the parameters to.
   * @param userId
   *          - the user to show his friends.
   * @param multiSelect
   *          - whether or not to allow multi select.
   * @param showTitleBar
   *          - whether or not to show a title bar.
   */
  @SuppressWarnings("static-access") public static void populateParameters(final Intent intent, final String userId,
      final boolean multiSelect, final boolean showTitleBar) {
    intent.putExtra(FriendPickerFragment.USER_ID_BUNDLE_KEY, userId);
    intent.putExtra(FriendPickerFragment.MULTI_SELECT_BUNDLE_KEY, multiSelect);
    intent.putExtra(FriendPickerFragment.SHOW_TITLE_BAR_BUNDLE_KEY, showTitleBar);
    intent.putExtra(FriendPickerFragment.EXTRA_FIELDS_BUNDLE_KEY, "username");
  }
  
  @Override public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!FacebookLogin.hasOpenSession()) {
      startActivityForResult(new Intent(this, LoginActivity.class), 1);
      finish();
    }
    setContentView(R.layout.ji_pick_friends_activity);
    final FragmentManager fm = getSupportFragmentManager();
    if (savedInstanceState == null) {
      final Bundle args = getIntent().getExtras();
      friendPickerFragment = new FriendPickerFragment(args);
      fm.beginTransaction().add(R.id.friend_picker_fragment, friendPickerFragment).commit();
    } else
      friendPickerFragment = (FriendPickerFragment) fm.findFragmentById(R.id.friend_picker_fragment);
    friendPickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
      @SuppressWarnings("synthetic-access") @Override public void onError(final PickerFragment<?> fragment,
          final FacebookException error) {
        PickFriendsActivity.this.onError(getString(R.string.connection_error_description));
      }
    });
    friendPickerFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
      @Override public void onDoneButtonClicked(final PickerFragment<?> fragment) {
        final Intent newIntent = new Intent(context, EventFilterActivity.class);
        final ArrayList<String> userNames = new ArrayList<String>();
        for (final GraphUser gu : friendPickerFragment.getSelection())
          userNames.add(gu.getUsername());
        newIntent.putExtra("friends", userNames);
        newIntent.putExtra("isFaceGroup", false);
        setResult(MainMapActivity.RESULT_FILTER, newIntent);
        finish();
      }
    });
  }
  
  /**
   * Puts a toast on the screen with the specified error.
   * 
   * @param s
   *          - the specified error.
   */
  private void onError(final String s) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }
  
  @Override protected void onStart() {
    super.onStart();
    try {
      friendPickerFragment.loadData(false);
    } catch (final Exception ex) {
      onError(getString(R.string.connection_error_description));
    }
  }
}
