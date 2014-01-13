package com.technion.coolie.techlibrary;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.CoolieAccount;
import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.util.HoldsInfoXMLHandler;

public class HoldsFragment extends SherlockFragment {

	private static final String SHARED_PREF = "lib_pref";
	private static final String USER_ID = "user_id";

	private ListView mListView;
	private TextView mEmptyView;

	private ArrayList<HoldElement> mHoldsList;
	private HoldsAdapter mHoldsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.lib_holds_list, container, false);
		mListView = (ListView) v.findViewById(R.id.list);
		mEmptyView = (TextView) v.findViewById(R.id.empty);
		mListView.setEmptyView(mEmptyView);

		mHoldsList = ((LibraryCardFragment) getParentFragment()).getHoldsList();
		mListView.setAdapter(new HoldsAdapter(getActivity(), mHoldsList, this));
		Log.d("LoansFrg:", "adapter set, number of items is:"
				+ ((Integer) mHoldsList.size()).toString());
		mHoldsAdapter = (HoldsAdapter) mListView.getAdapter();

		return v;
	}

	// TODO DELETE?
	public void notifyListChange(ArrayList<HoldElement> newList) {
		if (mHoldsAdapter == null) {
			Log.e("ERRRRRRROR ADAPTER:", "WTF");
		}
		mHoldsList.clear();
		mHoldsList.addAll(newList);
		mHoldsAdapter.notifyDataSetChanged();
	}

	protected void onXMLRecieved(String data) {
		// parser
		HoldsInfoXMLHandler holdsXMLHandler = new HoldsInfoXMLHandler();
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			xr.setContentHandler(holdsXMLHandler);
			xr.parse(new InputSource(new StringReader(data)));
		} catch (Exception e) {
			// TODO: ?
			Log.e("woooot:", "exception - " + e.getClass().toString(), e);
		}

		mHoldsList.clear();
		mHoldsList.addAll(holdsXMLHandler.holdList);
		mHoldsAdapter.notifyDataSetChanged();
	}

	

	// TODO
	// sending a request to the internet to delete the book from the data base..
	// need to add new fields to the holdItem : 1. docNumber 2. itemSequence
	// 3. sequence(?) 4. library
	public void notifyBookDelete(HoldElement holdElement) {
		// TODO Auto-generated method stub
		if (!isOnline()) {
			toastConnectionError();
		} else {
			HtmlGrabber hg = new HtmlGrabber(getSherlockActivity()) {
				@Override
				public void handleResult(String result, CoolieStatus status) {

					if (status == CoolieStatus.RESULT_OK
							&& !(result.contains("error"))) {
						Log.v("LibraryCard:", "cancel book result:\n" + result);

					} else {
						// TODO: generate error
					}

				}
			};
			SharedPreferences pref = getSherlockActivity()
					.getSharedPreferences(SHARED_PREF, 0);
			String docNum = holdElement.id, itemSeq = holdElement.itemSequence, seq = holdElement.queuePosition, borId = pref
					.getString(USER_ID, "&error"), library = "tec50";
			String userAuthUrl = "https://aleph2.technion.ac.il/X?" // https!
					+ "op=hold-req-cancel&doc_number="
					+ docNum
					+ "&item_sequence=" + itemSeq + "&sequence="
					+ seq
					+ "&bor_id=" + borId + "&library=" + library;
			// Log.v("LibraryCard:", "cancel book url:\n" + userAuthUrl);
			hg.getHtmlSource(userAuthUrl, CoolieAccount.NONE);
		}
	}

	private void toastConnectionError() {
		Toast toast = Toast.makeText(getSherlockActivity(),
				"Connection error, try again later.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSherlockActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
