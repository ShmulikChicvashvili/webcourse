package com.technion.coolie.techlibrary;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieStatus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

/**
 * Activity which displays a login screen to the user.
 */
public class LoginActivity extends Activity {
	// Keep track of the login task to ensure we can cancel it if requested.
	private UserLoginTask mAuthTask = null;

	// url for authentication
	public static final String userAuthUrl_id = "http://aleph2.technion.ac.il/X?op=bor-auth&bor_id=";
	public static final String userAuthUrl_pass = "&verification=";
	
	//shared pref file.
	private static final String SHARED_PREF = "lib_pref";
	
	//pref logged key
	private static final String LOGGED_IN = "is_logged";

	// Values for id and password at the time of the login attempt.
	private String mUserId;
	private String mPassword;

	// UI references.
	private EditText mUserIdView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	public SharedPreferences mSharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lib_activity_login);
		setupActionBar();
		
		Log.d("LoginActicity:", "login...............");
		
		mSharedPref = getSharedPreferences(SHARED_PREF,0);
		if(mSharedPref.getBoolean(LOGGED_IN, false)) {
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}

		// Set up the login form.
		// delete? mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mUserIdView = (EditText) findViewById(R.id.user_id);
		// mUserIdView.setText(mEmail);
		// mUserIdView.setHint(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in the account specified by the login form. If there are
	 * form errors (invalid id, missing fields, etc.), the errors are presented
	 * and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserIdView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUserId = mUserIdView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid id.
		if (TextUtils.isEmpty(mUserId)) {
			mUserIdView.setError(getString(R.string.error_field_required));
			focusView = mUserIdView;
			cancel = true;
		}
		// else if (!mUserId.contains("@")) {
		// mUserIdView.setError(getString(R.string.error_invalid_id));
		// focusView = mUserIdView;
		// cancel = true;
		// }

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			//code example for using html grabber!
			//TODO: change code and use it.
			HtmlGrabber hg = new HtmlGrabber(getApplicationContext())
			 {
					@Override
					public void handleResult(String result,
							CoolieStatus status) {
						// TODO Auto-generated method stub
						mAuthTask = new UserLoginTask(result);	
						mAuthTask.execute((Void) null);
							
					}
			 };
			 String userAuthUrl = userAuthUrl_id + mUserId
						+ userAuthUrl_pass + mPassword;
			 hg.getHtmlSource(userAuthUrl, HtmlGrabber.Account.NONE);
		}
		
	}

	private void toastConnectionError() {
		Toast toast = Toast.makeText(this, "connection error", Toast.LENGTH_LONG);
		toast.show();
	}
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		public UserInfo user = null;
		private boolean mHasError;
		private String result;
		
		public UserLoginTask(String result) {
			super();
			this.result = result;
			mHasError = false;
		}
		
		String convertStreamToString(InputStream is) {
		    java.util.Scanner s = new java.util.Scanner(is,"UTF-8").useDelimiter("\\A");
		    return s.hasNext() ? s.next() : "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			UserInfoXMLHandler userXMLHandler = new UserInfoXMLHandler();
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				/** Create handler to handle XML Tags ( extends DefaultHandler ) */

				xr.setContentHandler(userXMLHandler);
				xr.parse(new InputSource(new StringReader(result)));
				

			} catch (Exception e) {
				if ((e.getClass())==java.net.UnknownHostException.class) {
					mHasError = true;
					return false;
				}
				if ((e.getClass() == SAXException.class)
						&& (((SAXException) e).getMessage().equals("bad auth"))) {
					return false;
				}
				Log.e("woooot:", "exception - " + e.getClass().toString(), e);
			}
			user = userXMLHandler.user;
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			
			if (success) {
				SharedPreferences.Editor editor = mSharedPref.edit();
				editor.putBoolean(LOGGED_IN, true);
				
				// save the id (and not password) in lid_pref
				editor.putString("user_id", mUserId); 
				//editor.putString("user_pass", mPassword);
				// save the user info in the lib_pref
				int index = user.fullName.indexOf(",");
				editor.putString("user_first_name",
						user.fullName.substring(index + 1));
				editor.putString("user_last_name",
						user.fullName.substring(0, index - 1));
				editor.putString("user_address", user.address);
				editor.putString("user_email", user.email);
				editor.putString("user_telephone", user.telephone);
				editor.putString("user_home_library", user.homeLibraryENG + " , "
						+ user.homeLibraryHEB);
				editor.putString("user_education_status", user.educationStatus);
				//
				// String[] keys = { "user_first_name", "user_last_name",
				// "user_address", "user_email", "user_telephone",
				// "user_home_library", "user_education_status" };
				// //int index = user.fullName.indexOf(",");
				// String lastName = user.fullName.substring(0, index - 1);
				// String firstName = user.fullName.substring(index + 1);
				// String[] values = { firstName, lastName, user.address,
				// user.email, user.telephone,
				// user.homeLibraryENG + "," + user.homeLibraryHEB,
				// user.educationStatus };
				//
				// for(int i = 0 ; i < values.length; i++){
				// editor.putString(keys[i], values[i]);
				// }
				//
				editor.commit();
				Intent resultIntent = new Intent();
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			} else if(mHasError) {
				toastConnectionError();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_CANCELED, resultIntent);
		}
	}

	/*
	 * XML parser for user info.
	 */
	private class UserInfoXMLHandler extends DefaultHandler {
		private boolean currentElement = false;
		private String currentValue = null;
		public UserInfo user = null;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			currentElement = true;

			if (localName.equals("bor-auth")) {
				/** Start */
				user = new UserInfo();
			}
			if (localName.equals("error")) {
				/* invalid id/pass. exit? */
				throw new SAXException("bad auth");
			}
		}

		/**
		 * Called when tag closing ( ex:- <name>AndroidPeople</name> -- </name>
		 * )
		 */
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			currentElement = false;

			/** set value */
			if (localName.equals("z303-id")) {
				user.id = currentValue;
			} else if (localName.equals("z303-name")) {
				user.fullName = currentValue;
			} else if (localName.equals("z303-update-date")) {
				user.updateDate = currentValue;
			} else if (localName.equals("z303-home-library")) {
				user.homeLibraryENG = currentValue;
			} else if (localName.equals("z303-field-1")) {
				user.homeLibraryHEB = currentValue;
			} else if (localName.equals("z304-address-1")) {
				user.address = currentValue;
			} else if (localName.equals("z304-email-address")) {
				user.email = currentValue;
			} else if (localName.equals("z305-bor-status")) {
				user.educationStatus = currentValue;
			} else if (localName.equals("z304-telephone")) {
				user.telephone = currentValue;
			}
		}

		/**
		 * Called to get tag characters ( ex:- <name>AndroidPeople</name> -- to
		 * get AndroidPeople Character )
		 */
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (currentElement) {
				currentValue = new String(ch, start, length);
				currentElement = false;
			}
		}
	}
}
