package com.technion.coolie.techlibrary;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.techlibrary.BookItems.LoanElement;
import com.technion.coolie.techlibrary.util.LoansInfoXMLHandler;

public class LoansFragment extends SherlockFragment {
	private ListView mListView;
	private TextView mEmptyView;

	private ArrayList<LoanElement> mLoansList;
	private LoansAdapter mLoansAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.lib_loans_list, container, false);
		mListView = (ListView) v.findViewById(R.id.list);
		mEmptyView = (TextView) v.findViewById(R.id.empty);
		mListView.setEmptyView(mEmptyView);
		mLoansList = ((LibraryCardFragment) getParentFragment()).getLoansList();
		mListView.setAdapter(new LoansAdapter(getActivity(), mLoansList));
		Log.d("LoansFrg:", "adapter set, number of items is:"
				+ ((Integer) mLoansList.size()).toString());
		mLoansAdapter = (LoansAdapter) mListView.getAdapter();
		return v;
	}

	public void notifyListChange(ArrayList<LoanElement> newList) {
		if (mLoansAdapter == null) {
			Log.e("ERRRRRRROR ADAPTER:", "WTF");
		}
		mLoansList.clear();
		mLoansList.addAll(newList);
		mLoansAdapter.notifyDataSetChanged();
	}

	public void onXMLRecieved(String data) {

		// parser
		LoansInfoXMLHandler infoXMLHandler = new LoansInfoXMLHandler();
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			xr.setContentHandler(infoXMLHandler);
			xr.parse(new InputSource(new StringReader(data)));
		} catch (Exception e) {
			if ((e.getClass()) == java.net.UnknownHostException.class) {
				// mHasError = true; TODO
				// return false;
				return;
			}
			if ((e.getClass() == SAXException.class)
					&& (((SAXException) e).getMessage().equals("bad auth"))) {
				// return false; TODO
			}
			Log.e("woooot:", "exception - " + e.getClass().toString(), e);
		}

		// set the list from the parser into the global loansList
		Collections.sort(infoXMLHandler.loanList,
				new Comparator<LoanElement>() {
					public int compare(LoanElement c1, LoanElement c2) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd/MM/yyyy");
						int res = 7;
						try {
							res = formatter.parse(c1.dueDate).compareTo(
									formatter.parse(c2.dueDate));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // use your logic
						return res;
					}
				});
		mLoansList.clear();
		mLoansList.addAll(infoXMLHandler.loanList);
		mLoansAdapter.notifyDataSetChanged();
	}
}
