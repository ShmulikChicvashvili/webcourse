package com.technion.coolie.joinin.subactivities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.facebook.FacebookLogin.OnLoginDone;
import com.technion.coolie.joinin.gui.WrapperView;
import com.technion.coolie.joinin.map.MainMapActivity;

/**
 * 
 * @author Tom Yitav (some functions modified by Alon)
 * 
 *         Activity which displays a login screen to the user, offering
 *         registration as well.
 */
public class LoginActivity extends Activity {
  Button logInButton;
  EditText usernameText;
  EditText passwordFieldText;
  TextView display;
  Button signUpButton;
  WrapperView mWrapper;
  
  @SuppressWarnings({ "static-access", "unused" }) @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mWrapper = new WrapperView(this, inflater.inflate(R.layout.ji_activity_login, null));
    setContentView(mWrapper);
    final Display d = getWindowManager().getDefaultDisplay();
    @SuppressWarnings("deprecation")
    final int hieght = d.getHeight();
    @SuppressWarnings("deprecation")
    final int width = d.getWidth();
    final RelativeLayout logoWrapper = (RelativeLayout) findViewById(R.id.logo_wrapper);
    logoWrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, hieght / 3));
    display = (TextView) findViewById(R.id.text_login_failed);
    // setComponents();
  }
  
  /**
   * ADDED BY: Alon An OnDone callback to be called after async-login.
   */
  private final FacebookLogin.OnLoginDone afterLogin = new OnLoginDone() {
    @Override public void loginCallback(final ClientAccount a) {
      if (a != null) {
        setResult(MainMapActivity.RESULT_LOGIN_ACCOUNT, new Intent().putExtra("account", a));
        finish();
      } else
        Toast.makeText(getApplicationContext(), "Failed login please try again", Toast.LENGTH_LONG).show();
    }
  };
  
  /**
   * ADDED BY:Alon To be called after the Login with facebook button was
   * pressed.
   * 
   * @param v
   *          - Not in use. Just so that this could be a button call.
   */
  public void internal_login(final View v) {
    FacebookLogin.login(this, afterLogin);
  }
  
  /**
   * ADDED BY:Alon To be called after the External user login button was
   * pressed.
   * 
   * @param v
   *          - Not in use. Just so that this could be a button call.
   */
  public void exernal_login(final View v) {
    FacebookLogin.switchUser(this, afterLogin);
  }
  
  @Override protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
  }
  
  @Override public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      setResult(MainMapActivity.RESULT_FINISH);
      finish();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
  
  @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    FacebookLogin.onResult(this, requestCode, resultCode, data);
    switch (resultCode) {
      case MainMapActivity.RESULT_LOGIN_ACCOUNT:
        setResult(MainMapActivity.RESULT_LOGIN_ACCOUNT, new Intent().putExtra("account", data.getParcelableExtra("account")));
        finish();
        break;
      default:
        break;
    }
  }
  
  public enum LoginResult {
    LOGIN_SUCCESS {
      @Override public String toString() {
        return "";
      }
    },
    LOGIN_ILLEGAL_USERNAME {
      @Override public String toString() {
        return "Username is illegal! Please choose a different username";
      }
    },
    LOGIN_ILLEGAL_PASSWORD {
      @Override public String toString() {
        return "Password is illegal!";
      }
    }
  }
}
