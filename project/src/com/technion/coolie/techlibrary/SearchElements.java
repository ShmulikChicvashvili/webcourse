package com.technion.coolie.techlibrary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.HoldsAdapter.viewHolder;

public class SearchElements {
	static public class SearchFragment extends SherlockFragment {
		private static final int firstSearchPart = 1;
		private static final int secondSearchPart = 2;
		Editable input = null;
		String searchData = null;

		// search result
		public Integer setNum = null;
		public Integer numOfElements = null;
		private static final int DEFAULT_NUM_OF_ENTERIES = 20;
		public ArrayList<LibraryElement> items = null;
//		protected ImageView imageview;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			// Inflate the layout for this fragment
			View v = inflater.inflate(R.layout.lib_search_fragment, container,
					false);

			final EditText searchInputBox = (EditText) v
					.findViewById(R.id.lib_search_data);
//			searchInputBox.requestFocus();
//			searchInputBox
//					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//						@Override
//						public boolean onEditorAction(TextView textView,
//								int id, KeyEvent keyEvent) {
//							input = searchInputBox.getText();
//							if (id == R.id.search || id == EditorInfo.IME_NULL) {
//								if (input == null
//										|| input.toString().length() == 0) {
//									searchInputBox
//											.setError("Nothing to search");
//									searchInputBox.requestFocus();
//									return true;
//								} else {
//									getSearchDataSet(input.toString());
//									return false;
//								}
//
//							}
//							return true;
//						}
//					});
			Button searchButton = (Button) v
					.findViewById(R.id.lib_search_button);
			if (searchButton == null) {
				return v;
			}
			searchButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					input = searchInputBox.getText();
					if (input == null || input.toString().length() == 0) {
						searchInputBox.setError("Nothing to search");
						searchInputBox.requestFocus();

					} else {
						getSearchDataSet(input.toString());
					}

				}
			});
//			imageview = (ImageView) v.findViewById(R.id.lib_image);
			
			
			// ~~~~~~ Barcode ~~~~~~~~~~
			Button barcodeButton = (Button)v.findViewById(R.id.lib_search_barcode);
			barcodeButton.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					BarcodeSearch.scanForBook(getSherlockActivity());
					
				}
			});
			return v;
		}
		/** 
		 * First part of the search - get setData
		 */
		private void getSearchDataSet(String searchData) {
			HtmlGrabber hg = new HtmlGrabber(getActivity()) {
				@Override
				public void handleResult(String result, CoolieStatus status) {
					Log.d("xml result", result);
					try {
						parseResult(result, firstSearchPart);
						getSearchDataEntries();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.d("SEARCH ERROR", e.getMessage());
//						e.printStackTrace();
					} 
				}

				@Override
				public void handleImage(Bitmap b) {
					// TODO Auto-generated method stub
					
				}
			};
			// TODO
			String searchUrl = "http://aleph2.technion.ac.il/X?op=find&base=tecall&request="
					+ searchData.replace(" ", "+");
			hg.getHtmlSource(searchUrl, HtmlGrabber.Account.NONE);
		}
		/** 
		 * Second part of the search - get dataEntries
		 */
		private void getSearchDataEntries() {
			HtmlGrabber hg = new HtmlGrabber(getActivity()) {
				@Override
				public void handleResult(String result, CoolieStatus status) {
					Log.d("xml result!!!!!!!!!!!!!", result);
								
				}

				@Override
				public void handleImage(Bitmap b) {
					Log.d("in image ", "blaaaaaaaaaaaaaaaaaaaaaaaaa");
//					imageview.setImageBitmap(b);
					
				}
			};
			// TODO
			Log.d("the url for the second part is: ", "http://aleph2.technion.ac.il/X?op=present&set_no="
					+ setNum + "&set_entry=1-" + DEFAULT_NUM_OF_ENTERIES);
			String searchUrl = "http://aleph2.technion.ac.il/X?op=present&set_no="
					+ setNum + "&set_entry=1-" + DEFAULT_NUM_OF_ENTERIES;
//			hg.getHtmlSource(searchUrl, HtmlGrabber.Account.NONE);
			hg.getHtmlSource("http://books.google.co.il/books?vid=ISBN9654826356&printsec=frontcover&img=0&zoom=1", HtmlGrabber.Account.FACEBOOK);
		}

		/**
		 * 
		 * This function sands the "result" string to the right XMLParser - by in which part of the search the program is.
		 * 
		 * @param result - from the HtmlGrabber
		 * @param searchPart - DataSet(=0)/ DataEntries(=1)
		 * @throws Exception - if one of the results is an error
		 */
		private void parseResult(String result, int searchPart) throws Exception {
			if (searchPart == firstSearchPart) {
				SearchSetResult_XMLHandler setXMLHandler = new SearchSetResult_XMLHandler();
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();

					/**
					 * Create handler to handle XML Tags ( extends
					 * DefaultHandler )
					 */
					xr.setContentHandler(setXMLHandler);
					xr.parse(new InputSource(new StringReader(result)));
				} catch (Exception e) {
					throw new Exception(e.getMessage());
					// TODO: handle exceptions?
				}

			} else if (searchPart == secondSearchPart) {
				SearchItemsResult_XMLHandler itemsXMLHandler = new SearchItemsResult_XMLHandler();
				items = new ArrayList<BookItems.LibraryElement>();
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();

					/**
					 * Create handler to handle XML Tags ( extends
					 * DefaultHandler )
					 */
					xr.setContentHandler(itemsXMLHandler);
					xr.parse(new InputSource(new StringReader(result)));
				} catch (Exception e) {
					Log.e("woooot:", "exception - " + e.getClass().toString(),
							e);
					// TODO: handle exceptions?
				}
				if(items != null)
				Log.d("num of items:", "NUM OF THE ITEMS " + items.size());
			}
		}

		//  XML HANDLERS
		private class SearchSetResult_XMLHandler extends DefaultHandler {
			private boolean currentElement = false;
			private String currentValue = null;

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				currentElement = true;

				if (localName.equals("error")) {
					// TODO CHECK ERRORS..
					throw new SAXException("empty set");
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
				if (localName.equals("set_number")) {
					setNum = new Integer(currentValue);
				} else if (localName.equals("z303-name")) {
					numOfElements = new Integer(currentValue);
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
				}
			}
		}
		// -----------------------------------------------------------------
		private class SearchItemsResult_XMLHandler extends DefaultHandler {
			private boolean currentElement = false;
			private String currentValue = null;

			LibraryElement curr = null;
			private boolean inBookDetails = false;
			private boolean inBookName = false;
			private boolean inBookAuth = false;

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				currentElement = true;
				if (localName.equals("record")) {
					curr = new BookItems().new LibraryElement();
				}
				int index = -1;
				if (localName.equals("error")) {
					// TODO CHECK ERRORS..
					throw new SAXException("SEARCH ERROR");
				} else if (localName.equals("varfield")) {
					index = attributes.getIndex("id");
					if (index == -1) {
						// TODO ERROR
					} else if (attributes.getValue(index).equals("245")) {
						inBookDetails = true;
					}

				} else if (localName.equals("subfield") && inBookDetails) {
					index = attributes.getIndex("label");
					if (index == -1) {
						// TODO ERROR
					} else if (attributes.getValue(index).equals("a")) {
						inBookName = true;
					} else if (attributes.getValue(index).equals("c")) {
						inBookAuth = true;
					}
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
				if (localName.equals("doc_number")) {
					curr.id = currentValue;
				} else if (localName.equals("subfield")) {
					if (inBookName)
						curr.name = currentValue;
					else if (inBookAuth)
						curr.author = currentValue;
				} else if (localName.equals("record")) {
					items.add(curr);
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
				}
			}
		}
	}


	
//	public static class SearchResultAdapter extends BaseAdapter {
//
//		private final List<BookItems> items;
//		private Context context;
//		// protected boolean wasPressed = false;
//
//		public SearchResultAdapter(final Context context, List<BookItems> searchList) {
//			this.context = context;
//			this.items = searchList;
//		}
//
//		class viewHolder {
//			public TextView name;
//			public TextView author;
//			public ImageView photo;
//		}
//
//		@Override
//		public int getCount() {
//			return items.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// check-boundries?
//			return items.get(arg0);
//		}
//
//		@Override
//		public long getItemId(int position) {
//
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			viewHolder holder;
//			View view = null;
//			if (convertView == null) {
//				LayoutInflater inflater = (LayoutInflater) parent.getContext()
//						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				view = inflater.inflate(R.layout.lib_holds_item, null);
//
//				holder = new viewHolder();
//				holder.name = (TextView) view.findViewById(R.id.lib_book_name);
//				holder.author = (TextView) view.findViewById(R.id.lib_book_author);
//				holder.photo = (ImageView) view.findViewById(R.id.lib_book_image);
//				view.setTag(holder);
//
//			} else {
//				view = convertView;
//				holder = (viewHolder) view.getTag();
//			}
//			
//			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//			
//			view.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
////					BookDescription bD = new BookDescription(holds.get(position));
//					Intent intent = new Intent(context,
//							BookDescriptionActivity.class);
//					HoldElement hE = items.get(position);
//					String[] extraData = {hE.name, hE.author, hE.library};
//					intent.putExtra("description", extraData);
//					((Activity)context).startActivityForResult(intent, 1);
//					
//				}
//			});
//			
//			
//			
//			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
//			
//			ImageButton cancel = (ImageButton) view
//					.findViewById(R.id.lib_cancel_button);
//			cancel.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Log.d("cancel clicked", "the obj is: " + position);
//					DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//						// DialogInterface called while setting the AlertDialog
//						// Buttons
//						public void onClick(DialogInterface dialog, int which) {
//							// Here you can perform functions of Alert Dialog
//							// Buttons as shown
//							switch (which) {
//							case DialogInterface.BUTTON_POSITIVE:
//								// Yes button clicked
//								items.remove(position);
//								notifyDataSetChanged();
//								break;
//
//							case DialogInterface.BUTTON_NEGATIVE:
//								// No button clicked
//								break;
//							}
//						}
//					};
//
//					AlertDialog.Builder builder = new AlertDialog.Builder(
//							context);
//					builder.setTitle("Deleting a book from holds list");
//					// Set the Title of Alert Dialog
//					builder.setMessage("Are you sure?")
//							.setPositiveButton("Yes", dialogClickListener)
//							.setNegativeButton("No", dialogClickListener)
//							.show();
//
//				}
//			});
//			
//			holder.name.setText(items.get(position).name);
//			holder.author.setText(items.get(position).author);
//
//
//			return view;
//		}
	
	
	
	
	/**
	 * SearchResult Activity - open this activity when user press SEARCH botton
	 */
	static public class SearchResultActivity extends CoolieActivity {
		
	
	
	
	
//	///*
//	 * LibraryDescriptionFragment
//	 */
//	static public class LibraryDescriptionFragment extends SherlockFragment {
//
//		String name = null;
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			super.onCreateView(inflater, container, savedInstanceState);
//			
//			LibrariesData.Library libDetails = LibrariesData
//					.getLibraryByName(name);
//			// Inflate the layout for this fragment
//			View v = inflater
//					.inflate(R.layout.lib_activity_library_description,
//							container, false);
//
//			if (libDetails == null) {
//				((TextView) (v.findViewById(R.id.lib_name)))
//						.setText("No description available");
//
//			} else {
//				((TextView) (v.findViewById(R.id.lib_name)))
//						.setText(libDetails.name);
//				((TextView) (v.findViewById(R.id.lib_head_librarien_name)))
//						.setText(libDetails.headLibrarian);
//				((TextView) (v.findViewById(R.id.lib_phone_number)))
//						.setText(libDetails.phone);
//				((TextView) (v.findViewById(R.id.lib_email_data)))
//						.setText(libDetails.email);
//			}
//			return v;
//		}
//
//		public void setArguments(String name) {
//			this.name = name;
//		}
//
//	}
//
//	
//	
//	/*
//	 * LibrariesListFragment
//	 */
//	static public class LibrariesListFragment extends SherlockFragment {
//		private ListView mListView;
//		private TextView mEmptyView;
//		private ArrayList<String> mOpenHours;
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			super.onCreateView(inflater, container, savedInstanceState);
//			mOpenHours = new ArrayList<String>();
//			mOpenHours.addAll(LibrariesData.names);
//
//			// Inflate the layout for this fragment
//			View v = inflater.inflate(R.layout.lib_open_hours_list, container,
//					false);
//			mListView = (ListView) v.findViewById(R.id.list);
//			mEmptyView = (TextView) v.findViewById(R.id.empty);
//			mListView.setEmptyView(mEmptyView);
//
//			mListView.setAdapter(new OpenHoursAdapter(getActivity(),
//					mOpenHours, this));
//			Log.d("LoansFrg:", "adapter set, number of items is:"
//					+ ((Integer) mOpenHours.size()).toString());
//
//			return v;
//		}
//
//		public void notifyClickedItem(String libraryName) {
//			Log.d(" NOTIFICATION : you pressed library : " ,"" + libraryName);
//			if (getParentFragment() != null) {
//				((OpenHoursFragment) getParentFragment())
//						.notifyClickedItem(libraryName);
//			}
//		}
//	}
//
//	
//	/*
//	 * LibraryDescriptionActivity
//	 */
//	static public class LibraryDescriptionActivity extends CoolieActivity {
//
//		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
//			 if (getResources().getConfiguration().orientation ==
//			 Configuration.ORIENTATION_LANDSCAPE) {
//			 // If the screen is now in landscape mode, we can show the
//			 // dialog in-line with the list so we don't need this activity.
//			 finish();
//			 return;
//			 }
//			setContentView(R.layout.lib_activity_library_description);
//			String lib_Name = getIntent().getExtras().getString("name");
//			LibrariesData.Library tmp = LibrariesData
//					.getLibraryByName(lib_Name);
//			if (tmp == null) {
//				((TextView) (findViewById(R.id.lib_name)))
//						.setText("No description available");
//
//			} else {
//				((TextView) (findViewById(R.id.lib_name))).setText(tmp.name);
//				((TextView) (findViewById(R.id.lib_head_librarien_name)))
//						.setText(tmp.headLibrarian);
//				((TextView) (findViewById(R.id.lib_phone_number)))
//						.setText(tmp.phone);
//				((TextView) (findViewById(R.id.lib_email_data)))
//						.setText(tmp.email);
//			}
//
//		}
//
//	}
	}
}