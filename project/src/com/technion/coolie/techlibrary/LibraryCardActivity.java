package com.technion.coolie.techlibrary;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LoanElement;

public class LibraryCardActivity extends FragmentActivity {

	// Shared pref - file
	private static final String SHARED_PREF = "lib_pref";
	// pref logged key
	private static final String LOGGED_IN = "is_logged";
	// default string return
	private static final String NA = "n/a";
	// code for login activity intent
	private static final int LOGGIN_CODE = 7;
	// shared pref
	private SharedPreferences mSharedPref;
	// UI references.
	private LoansFragment mLoansFragment;
	private HoldsFragment mHoldsFragment;

	// Keep track of the login task to ensure we can cancel it if requested.
	private GetBooksInfoTask mGetBooksInfoTask = null;

	private ArrayList<LoanElement> globalLoansList = null;
	private ArrayList<HoldElement> globalHoldsList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_activity_library_card);

		// Show the Up button in the action bar.
		setupActionBar();

		FragmentManager fmg = getSupportFragmentManager();
		Log.d("L.card:", "number of fragments?="
				+ ((Integer) fmg.getFragments().size()).toString());
		mLoansFragment = (LoansFragment) fmg
				.findFragmentById(R.id.loans_fragment);
		mHoldsFragment = (HoldsFragment) fmg
				.findFragmentById(R.id.holds_fragment); // null?

		globalLoansList = new ArrayList<BookItems.LoanElement>();
		globalHoldsList = new ArrayList<BookItems.HoldElement>();

		// check if login?
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, LOGGIN_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (LOGGIN_CODE): {
			if (resultCode != LoginActivity.RESULT_OK) {
				// TODO: generate error in login? maybe login was canceled?
			} else {
				mGetBooksInfoTask = new GetBooksInfoTask();
				mGetBooksInfoTask.execute();
			}
			break;
		}
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.library_card, menu);
		return true;
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_logout:
			SharedPreferences.Editor editor = mSharedPref.edit();
			editor.putBoolean(LOGGED_IN, false);
			editor.commit();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * getting loans list for fragment
	 */
	protected ArrayList<LoanElement> getLoansList() {
		return globalLoansList;
	}

	/**
	 * getting holds list for fragment
	 */
	protected ArrayList<HoldElement> getHoldsList() {
		return globalHoldsList;
	}

	/**
	 * Represents an asynchronous to get loans.
	 */
	private class GetBooksInfoTask extends AsyncTask<Void, Void, Boolean> {

		private static final String USER_ID = "user_id";
		private static final String userLoansUrl = "https://aleph2.technion.ac.il/X?op=bor-info&bor_id=";

		// error flag?
		private boolean mHasError; // TODO: check if needed, and return boolean
									// for doinback..
		// IS->string
		private String xml;
		private ProgressDialog progressDialog;
		// loan list - no need?
		private ArrayList<LoanElement> loansList;
		private ArrayList<HoldElement> holdsList;

		public GetBooksInfoTask() {
			super();
			mHasError = false;
			progressDialog = new ProgressDialog(LibraryCardActivity.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Retrieving data...");
			progressDialog.setTitle("Please wait");
			progressDialog.setIndeterminate(true);
		}

		// helping func to converrt IS->string
		private String convertStreamToString(InputStream is) {
			java.util.Scanner s = new java.util.Scanner(is, "UTF-8")
					.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}

		@Override
		protected void onPreExecute() {
			progressDialog.show();
		};

		@Override
		protected Boolean doInBackground(Void... params) {
			BooksInfoXMLHandler infoXMLHandler = new BooksInfoXMLHandler();
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				/** Send URL to parse XML Tags */
				mSharedPref = getSharedPreferences(SHARED_PREF, 0);
				String id = mSharedPref.getString(USER_ID, NA);
				// no shared pref exists?
				if (id == null || id.equals(NA)) {
					Intent intent = new Intent(LibraryCardActivity.this,
							LoginActivity.class);
					startActivity(intent);
					// assume login ok, what if user clicked back?
					// TODO: need finish? check login result etc.
				}
				String userAuthUrl = userLoansUrl + id; // TODO: change to
														// shared
														// pref?
				URL sourceUrl = new URL(userAuthUrl);

				/** Create handler to handle XML Tags ( extends DefaultHandler ) */

				xr.setContentHandler(infoXMLHandler);
				xml = convertStreamToString(sourceUrl.openStream());
				xr.parse(new InputSource(new StringReader(xml)));

			} catch (Exception e) {
				if ((e.getClass()) == java.net.UnknownHostException.class) {
					mHasError = true;
					return false;
				}
				if ((e.getClass() == SAXException.class)
						&& (((SAXException) e).getMessage().equals("bad auth"))) {
					return false;
				}
				Log.e("woooot:", "exception - " + e.getClass().toString(), e);
			}
			loansList = infoXMLHandler.loanList;
			holdsList = infoXMLHandler.holdList;

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean result) {
			mGetBooksInfoTask = null;
			// showProgress(false);

			//sorting loanlist according to duedate
			Collections.sort(loansList, new Comparator<LoanElement>() {
			    public int compare(LoanElement c1, LoanElement c2) {
			    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    	int res=7;
			        try {
						res = formatter.parse(c1.dueDate).compareTo(formatter.parse(c2.dueDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // use your logic
			        return res;
			    }
			}); 
			globalLoansList = loansList;
			globalHoldsList = holdsList;
			// TODO: what happens if list is empty?
			if (globalLoansList != null && globalHoldsList != null) {
				// TODO: notify change?
				Log.d("L.card activity:", "send notify change request");
				mLoansFragment.notifyListChange(globalLoansList);
				mHoldsFragment.notifyListChange(globalHoldsList);
			} else {
				// TODO: something went wrong, generate different error
				toastConnectionError();
			}
			if (mHasError) {
				toastConnectionError();
			}
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}

		@Override
		protected void onCancelled() {
			mGetBooksInfoTask = null;
			// showProgress(false);
		}

		/*
		 * toasting for bad connections
		 */
		private void toastConnectionError() {
			Toast toast = Toast.makeText(LibraryCardActivity.this,
					"connection error", Toast.LENGTH_LONG);
			toast.show();
		}

		/*
		 * XML parser for Loans + requests info.
		 */
		private class BooksInfoXMLHandler extends DefaultHandler {
			private boolean currentElement = false;
			private String currentValue = null;

			private boolean isInLoan = false;
			private boolean isInHold = false;
			private LoanElement currentLoan = null;
			private HoldElement currentHold = null;

			public ArrayList<LoanElement> loanList = null;
			public ArrayList<HoldElement> holdList = null;

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				currentElement = true;

				if (localName.equals("bor-info")) {
					/** Start */
					loanList = new ArrayList<LoanElement>();
					holdList = new ArrayList<HoldElement>();
				}
				if (localName.equals("item-l")) {
					/** Start loan */
					currentLoan = new BookItems().new LoanElement();
					isInLoan = true;
				}
				if (localName.equals("item-h")) {
					/** Start hold */
					currentHold = new BookItems().new HoldElement();
					isInHold = true;
				}
				if (localName.equals("error")) {
					/* invalid id/pass. exit? */
					throw new SAXException("bad something");
				}
			}

			/**
			 * Called when tag closing ( ex:- <name>AndroidPeople</name> --
			 * </name> )
			 */
			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				currentElement = false;

				/** set value */
				// book-item
				if (localName.equals("z30-doc-number")) {
					if (isInLoan) {
						currentLoan.id = currentValue;
					}
					if (isInHold) {
						currentHold.id = currentValue;
					}
				} else if (localName.equals("z13-title")) {
					if (isInLoan) {
						currentLoan.name = currentValue;
					}
					if (isInHold) {
						currentHold.name = currentValue;
					}
				} else if (localName.equals("z13-author")) {
					if (isInLoan) {
						currentLoan.author = currentValue;
					}
					if (isInHold) {
						currentHold.author = currentValue;
					}
				} else if (localName.equals("z30-material")) {
					if (isInLoan) {
						currentLoan.type = currentValue;
					}
					if (isInHold) {
						currentHold.type = currentValue;
					}
				} else if (localName.equals("z30-sub-library") && isInLoan) {
					if (isInLoan) {
						currentLoan.library = currentValue;
					}
					if (isInHold) {
						currentHold.library = currentValue;
					}
				}
				// loans specific
				else if (localName.equals("z36-due-date") && isInLoan) {
					currentLoan.dueDate = currentValue;
				}
				// holds specific
				else if (localName.equals("z37-request-date") && isInHold) {
					currentHold.createDate = currentValue;
					currentHold.arrivalDate = currentValue; // TODO: fix this
															// and next line
															// when we know
															// arrival date.
				} else if (localName.equals("shishkabab")/* TODO:... */
						&& isInHold) {
					currentHold.arrivalDate = currentValue;
				} else if (localName.equals("z37-sequence") && isInHold) {
					currentHold.queuePosition = currentValue;
				}
				// adding elements to list
				else if (localName.equals("item-l") && isInLoan) {
					loanList.add(currentLoan);
					isInLoan = false;
				} else if (localName.equals("item-h") && isInHold) {
					holdList.add(currentHold);
					isInHold = false;
				}
			}

			/**
			 * Called to get tag characters ( ex:- <name>AndroidPeople</name> --
			 * to get AndroidPeople Character )
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

	// this is for orientation change wont call asynctask again :\
	// not working. it wont change the layout - remove changeConfig in manifest?
	// @Override
	// public void onConfigurationChanged(Configuration newConfig)
	// {
	// super.onConfigurationChanged(newConfig);
	// //TODO: check if its the best way to do this
	// //setContentView(R.layout.lib_activity_library_card);
	//
	// //
	//
	// }
}
