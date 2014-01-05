package com.technion.coolie.techlibrary;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.R.layout;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.SearchElements.SearchResultAdapter;
import com.technion.coolie.techlibrary.SearchElements.SearchResultAdapter.viewHolder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BookDescriptionActivity extends CoolieActivity {

	private ListView mListView = null;
	public String name = null;
	private String author = null;
	private String library = null;
	private String id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] data = getIntent().getExtras().getStringArray("description");
		setContentView(R.layout.lib_activity_book_description);
		name = data[0];
		author = data[1];
		library = data[2];
		id = data[3];
		((TextView) findViewById(R.id.lib_book_name)).setText(data[0]);
		((TextView) findViewById(R.id.lib_book_author)).setText(data[1]);
		((TextView) findViewById(R.id.lib_book_library)).setText(data[3]);
		// lib_copies_list
		// set Adapter for list
		items = new ArrayList<CopyItem>();
//		getCopiesData();
		mListView = (ListView) findViewById(R.id.lib_copies_list);
//		mListView.setAdapter(new CopiesListAdapter(this, items, name));
		setAdapter();
		Log.d("check calling activity", "" + getCallingActivity()); // null :(

	}

	private void getCopiesData() {
		HtmlGrabber hg = new HtmlGrabber(this) {
			@Override
			public void handleResult(String result, CoolieStatus status) {
				Log.d("xml result", result);
				parseAndDisplay(result);
				setAdapter();
				

			}

		};
		// TODO
		Log.d("the url for copies is: ",
				"http://aleph2.technion.ac.il/X?op=circ-status&sys_no=" + id
						+ "&library=tec01");
		String searchUrl = "http://aleph2.technion.ac.il/X?op=circ-status&sys_no="
				+ id + "&library=tec01";
		hg.getHtmlSource(searchUrl, HtmlGrabber.Account.NONE);
	}

	protected void setAdapter() {
		ArrayList<Boolean> items = new ArrayList<Boolean>();
		int num = 1;
		for (int i = 0; i < 9; ++i) {
			items.add(1 - num == 1 ? true : false);
			num = 1 - num;
		}
		mListView.setAdapter(new CopiesListAdapter(this, items, name));		
	}

	protected void parseAndDisplay(String result) {
		// TODO Auto-generated method stub

	}

	private class CopyItem {
		public String status; // if and how long you may loan this item.
		public String dueDate;
		public String library;
		public String barcode;

	}

	private ArrayList<CopyItem> items = null;

	/*
	 * XML parser for Loans + requests info.
	 */
	private class CopiesXMLHandler extends DefaultHandler {

		/*
		 * <circ-status> <item-data> <z30-description/> <loan-status>BOOK IS
		 * LOST*</loan-status> <due-date/> <due-hour/> <sub-library>Aerospace
		 * Engineering</sub-library> <collection/> <location/> <pages/>
		 * <no-requests/> <location-2/> <barcode>000144802072</barcode>
		 * <opac-note/> </item-data>
		 */

		private boolean currentElement = false;
		private String currentValue = null;
		private CopyItem currentCopy = null;

		// public ArrayList<HoldElement> holdList = null;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			currentElement = true;
			currentValue = "";
//			if (localName.equals("circ-status")) {
//				/** Start */
//				items = new ArrayList<CopyItem>();
//			}
			if (localName.equals("item-data")) {
				currentCopy = new CopyItem();
			}
			// TODO CHECK .....
			if (localName.equals("error")) {
				/* invalid id/pass. exit? */
				throw new SAXException("bad something");
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
			if (localName.equals("loan-status")) {
				currentCopy.status = currentValue;
			} else if (localName.equals("due-date")) {
				currentCopy.dueDate = currentValue;
			} else if (localName.equals("sub-library")) {
				currentCopy.library = currentValue;
			} else if (localName.equals("barcode")) {
				currentCopy.barcode = currentValue;
			}else if (localName.equals("item-data")) {
				items.add(currentCopy);
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
				// currentHold = new BookItems().new HoldElement();
				if (length <= 0) {
					currentValue = "";
				} else {
					currentValue = new String(ch, start, length);
					currentValue = currentValue.replace("&apos;", "'")
							.replace("&quot;", "\"").replace("&amp;", "&");
					currentElement = false;
				}
			}
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static class CopiesListAdapter extends BaseAdapter {

		private final List<Boolean> items; // change!!!!!
		private BookDescriptionActivity context;
		private String[] funny = { "C", "l", "i", "c", "k", "o", "n", "m", "e" };
		// protected boolean wasPressed = false;
		private String bookName = null;

		OnClickListener onClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// LinearLayout b = (LinearLayout)
				// ((LinearLayout)((Button)v).getParent()).getParent();
				// TextView i = (TextView)
				// b.findViewById(R.id.lib_copy_due_date);
				Intent itemDataToAdd = new Intent();
				itemDataToAdd.putExtra("name", bookName);
				itemDataToAdd.putExtra("activity", "bookDescription");
				context.setResult(RESULT_OK, itemDataToAdd);
				Log.d("clicked", ((Button) v).getText().toString());

			}
		};

		public CopiesListAdapter(final Context context,
				List<Boolean> searchList, String name) {
			this.context = (BookDescriptionActivity) context;
			this.items = searchList;
			this.bookName = name;
		}

		class viewHolder {
			public TextView library;
			public TextView dueDate;
			public TextView loanPeriod;
			public ImageView availabilityIcon;
			public Button holdOrWishAdd;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int arg0) {
			// check-boundries?
			return items.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			viewHolder holder;
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.lib_book_copies_item, null);

				holder = new viewHolder();
				holder.availabilityIcon = (ImageView) view
						.findViewById(R.id.lib_availability_icon);
				holder.dueDate = (TextView) view
						.findViewById(R.id.lib_copy_due_date);
				holder.loanPeriod = (TextView) view
						.findViewById(R.id.lib_loan_period);
				holder.library = (TextView) view
						.findViewById(R.id.lib_book_library);
				holder.holdOrWishAdd = (Button) view
						.findViewById(R.id.lib_add_button);
				view.setTag(holder);

			} else {
				view = convertView;
				holder = (viewHolder) view.getTag();
			}
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if (items.get(position)) {
				holder.availabilityIcon
						.setImageResource(R.drawable.lib_green_circle);
				holder.dueDate.setText("waiting 4 u ;)");
				holder.loanPeriod.setText("one week");
			} else {
				holder.availabilityIcon
						.setImageResource(R.drawable.lib_red_circle);
				holder.loanPeriod.setText("hold on mama, im taken!");
				holder.dueDate.setText("in loan till:\n12/13/14");
//				holder.dueDate.setText("in loan till:\n" + context.items.get(position).dueDate);
			}
			holder.holdOrWishAdd.setText(funny[position]);
			holder.holdOrWishAdd.setOnClickListener(onClick);
//			holder.library.setText(context.items.get(position).library);
			// holder.name.setText(items.get(position).name);
			// holder.author.setText(items.get(position).author);

			return view;
		}
	}

}
