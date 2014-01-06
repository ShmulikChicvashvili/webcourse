package com.technion.coolie.techlibrary;

import java.io.StringReader;
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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.BookItems.LoanElement;

public class SearchElements {

	static public class SearchFragment extends SherlockFragment {
		private static final int firstSearchPart = 1;
		private static final int secondSearchPart = 2;
		Editable input = null;
		String searchData = null;
		private ArrayList<LibraryElement> searchItems = null;
		// search Url
		private static final String searchUrl = "https://aleph2.technion.ac.il/X?op=find&base=tec01&request=";
		//barcode consts
		public static final int BARCODE_REQUEST_CODE = 1;
		private static final String BARCODE = "barcode";
		private static final String FORMAT = "format";
		private static final String ISBN_FORMAT = "EAN_13";
		//
		private static final String SHARED_PREF_BARCODE = "lib_pref2";
		// search result
		public Integer setNum = null;
		public Integer numOfElements = null;
		private static final int DEFAULT_NUM_OF_ENTERIES = 20;
		private String lastSearch = null;

		private ListView mListView = null;
		private EditText mInputBoxView = null;

		// protected ImageView imageview;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			// Inflate the layout for this fragment
			View v = inflater.inflate(R.layout.lib_search_fragment, container,
					false);
			// set Adapter for list
			searchItems = new ArrayList<LibraryElement>();
			mListView = (ListView) v.findViewById(R.id.lib_search_list);
			mListView.setAdapter(new SearchResultAdapter(getSherlockActivity(),
					searchItems));
			mListView.setEmptyView((TextView) v.findViewById(R.id.lib_empty));
			mInputBoxView = (EditText) v.findViewById(R.id.lib_search_data);
			mInputBoxView.requestFocus();
			mInputBoxView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView,
								int id, KeyEvent keyEvent) {
							input = mInputBoxView.getText();
							if (id == R.id.search || id == EditorInfo.IME_NULL) {
								if (input == null
										|| input.toString().length() == 0) {
									mInputBoxView.setError("Nothing to search");
									mInputBoxView.requestFocus();
									return false;
								} else {
									keyBoardDown(textView);
									if (lastSearch == null
											|| lastSearch.equals(input
													.toString()) == false) {
										lastSearch = input.toString();
										searchItems.clear();
										((BaseAdapter) mListView.getAdapter())
												.notifyDataSetChanged();
										getSearchDataSet(input.toString(),
												searchUrl);
										return true;
									}
								}

							}
							return true;
						}

					});
			// ~~ regularSearchButton
			Button searchButton = (Button) v
					.findViewById(R.id.lib_search_button);
			searchButton.setOnClickListener(onButtonClick);
			// ~~ courseSearchButton
			Button courseButton = (Button) v
					.findViewById(R.id.lib_search_course);
			courseButton.setOnClickListener(onButtonClick);
			return v;
		}

		OnClickListener onButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.lib_search_button
						|| v.getId() == R.id.lib_search_course) {

					input = mInputBoxView.getText();
					if (input == null || input.toString().length() == 0) {
						mInputBoxView.setError("Nothing to search");
						mInputBoxView.requestFocus();

					} else {
						keyBoardDown(v);
						// if the user is a little child who like clicking on
						// search button.. ;)
						if (lastSearch != null
								&& lastSearch.equals(input.toString())) {
							return;
						} else {
							lastSearch = input.toString();
							searchItems.clear();
							((BaseAdapter) mListView.getAdapter())
									.notifyDataSetChanged();

							if (v.getId() == R.id.lib_search_course) {
								input.append("+2013?14+COM");
							}
							getSearchDataSet(input.toString(), searchUrl);
						}

					}
				}
			}
		};

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			MenuItem barcodeSearch = menu.add("Barcode Search");
			barcodeSearch.setIcon(R.drawable.lib_ic_action_scan);
			barcodeSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			barcodeSearch
					.setOnMenuItemClickListener(new OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							Intent intent = new Intent(getSherlockActivity(),
									BarcodeSearchActivity.class);
							Log.d("searchElement", "strat activity for result");
							startActivityForResult(intent, BARCODE_REQUEST_CODE);
							return true;
						}
					});
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			
			Log.d("searchElement", "on activity result " + requestCode);
			if(requestCode == BARCODE_REQUEST_CODE) {
				if(resultCode == BarcodeSearchActivity.RESULT_OK) {
					String url = searchUrl + getSherlockActivity().getSharedPreferences(SHARED_PREF_BARCODE, 0).getString(BARCODE, "0");
					if(getSherlockActivity().getSharedPreferences(SHARED_PREF_BARCODE, 0).getString(FORMAT, "0").equalsIgnoreCase(ISBN_FORMAT)) {
						//search for isbn
						url = url+"&code=ISBN";
					} else {
						//this is not isbn, search the server for barcode!
						url = url+"&code=BAR";
					}
					getSearchDataSet("",url);
				} else {
					Toast toastError = Toast.makeText(getSherlockActivity(),
							"No book scan data received!", Toast.LENGTH_SHORT);
					toastError.show();
				}
			}
			super.onActivityResult(requestCode, resultCode, intent);
		}

		protected void keyBoardDown(View v) {
			InputMethodManager keyBoard = ((InputMethodManager) getSherlockActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE));
			keyBoard.hideSoftInputFromWindow(v.getWindowToken(),
					InputMethodManager.RESULT_UNCHANGED_SHOWN);

		}

		// if there is an error toast it to the user
		protected boolean containsError(String result) {

			if (result.contains("<error>")) {
				Log.d("serchElements-error", result);
				//TODO: this is error because accessing record that not exist. need other fix
				if (result.contains("<error>There is no entry number:")) {
					return false;
				}
				
				if (result.contains("<error>empty set</error>")) {
					Toast toastEmpty = Toast.makeText(getSherlockActivity(),
							"no items for your search", Toast.LENGTH_SHORT);
					toastEmpty.show();
				} else {
					Toast toastError = Toast.makeText(getSherlockActivity(),
							"there was an error", Toast.LENGTH_SHORT);
					toastError.show();
				}
				return true;
			}
			return false;
		}

		/**
		 * First part of the search - get setData
		 */
		private void getSearchDataSet(String searchData, final String URL) {
			HtmlGrabber hg = new HtmlGrabber(getSherlockActivity()) {
				@Override
				public void handleResult(String result, CoolieStatus status) {
					//if isbn and ean_13, lets try isbn only (cut off initial 978) 
					if(URL.contains("&code=ISBN") && (result.contains("<error>empty set</error>")) &&
							(URL.length() == (searchUrl.length()+"&code=ISBN".length()+13))) {
						//lets try this again with deleting first three numbers
						String isbnUrl = URL.replaceFirst("request=\\d{3}", "request=");
						Log.d("searchElement", "isbn10 url:" + isbnUrl);
						getSearchDataSet("",isbnUrl); 
						return;
					}
					if (containsError(result)) {
						return;
					}

					Log.d("xml result", result);
					try {
						parseResult(result, firstSearchPart);
						getSearchDataEntries();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.d("SEARCH ERROR", e.getMessage());
						// e.printStackTrace();
					}
				}

				@Override
				public void handleImage(Bitmap b) {
					// TODO Auto-generated method stub

				}
			};
			// TODO
			Log.d("the url for the second part is: ",
					URL + searchData.replace(" ", "+"));
			hg.getHtmlSource(URL + searchData.replace(" ", "+"),
					HtmlGrabber.Account.NONE);
		}

		/**
		 * Second part of the search - get dataEntries
		 */
		private void getSearchDataEntries() {
			HtmlGrabber hg = new HtmlGrabber(getSherlockActivity()) {
				@Override
				public void handleResult(String result, CoolieStatus status) {

					if (containsError(result)) {
						return;
					}

					result = result.replace("<<", "").replace(">>", "");

					try {
						parseResult(result, secondSearchPart);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.d("SEARCH ERROR", e.getMessage());

						e.printStackTrace();
					}

				}

				@Override
				public void handleImage(Bitmap b) {
					Log.d("in image ", "blaaaaaaaaaaaaaaaaaaaaaaaaa");
					// imageview.setImageBitmap(b);

				}
			};
			// TODO
			Log.d("the url for the second part is: ",
					"https://aleph2.technion.ac.il/X?op=present&set_no="
							+ setNum + "&set_entry=1-"
							+ DEFAULT_NUM_OF_ENTERIES);
			String searchUrl = "https://aleph2.technion.ac.il/X?op=present&set_no="
					+ setNum + "&set_entry=1-" + DEFAULT_NUM_OF_ENTERIES;
			hg.getHtmlSource(searchUrl, HtmlGrabber.Account.NONE);
			// hg.getHtmlSource("https://books.google.co.il/books?vid=ISBN9654826356&printsec=frontcover&img=0&zoom=1",
			// HtmlGrabber.Account.FACEBOOK);
		}

		/**
		 * 
		 * This function sands the "result" string to the right XMLParser - by
		 * in which part of the search the program is.
		 * 
		 * @param result
		 *            - from the HtmlGrabber
		 * @param searchPart
		 *            - DataSet(=0)/ DataEntries(=1)
		 * @throws Exception
		 *             - if one of the results is an error
		 */
		private void parseResult(String result, int searchPart)
				throws Exception {
			if (result == null)
				return;
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
				searchItems.clear();
				searchItems.addAll(itemsXMLHandler.items);
				((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
				// Log.d("$$$$$$$$$$$$ first item", searchItems.get(1).author);
			}
		}

		// XML HANDLERS
		private class SearchSetResult_XMLHandler extends DefaultHandler {
			private boolean currentElement = false;
			private String currentValue = null;

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) {
				currentElement = true;
				currentValue = new String();
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
					currentValue += new String(ch, start, length);
				}
			}
		}

		// -----------------------------------------------------------------
		private class SearchItemsResult_XMLHandler extends DefaultHandler {
			private boolean currentElement = false;
			private String currentValue = null;
			public ArrayList<LibraryElement> items = null;
			LibraryElement curr = null;
			private boolean inBookDetails245 = false;
			private boolean inBookName = false;
			private boolean inBookAuthor = false;
			private boolean bookAuthorCheck = false;
			private boolean inBookDetails100 = false;

			public SearchItemsResult_XMLHandler() {
				items = new ArrayList<BookItems.LibraryElement>();
			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) {
				currentElement = true;
				currentValue = new String();
				if (localName.equals("record")) {
					curr = new BookItems().new LibraryElement();
				}
				int index = -1;
				if (localName.equals("varfield")) {
					index = attributes.getIndex("id");
					if (index == -1) {
						// TODO ERROR
					} else if (attributes.getValue(index).equals("245")) {
						inBookDetails245 = true;
					} else if (attributes.getValue(index).equals("100")) {
						inBookDetails100 = true;
					}

				} else if (localName.equals("subfield") && inBookDetails245) {
					index = attributes.getIndex("label");
					if (index == -1) {
						// TODO ERROR
					} else if (attributes.getValue(index).equals("a")) {
						inBookName = true;
					} else if (inBookAuthor == false && bookAuthorCheck == false
							&& attributes.getValue(index).equals("c")) {
						inBookAuthor = true;
					}
				} else if (!inBookAuthor && !bookAuthorCheck
						&& localName.equals("subfield") && inBookDetails100) {
					index = attributes.getIndex("label");
					if (index == -1) {
						// TODO ERROR
					} else if (attributes.getValue(index).equals("a")) {
						inBookAuthor = true;
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
					if (inBookName) {
						// Log.d("the name of the book is: ", currentValue);
						curr.name = currentValue.replace(":", "").replace("\\", "").replace("/", "");
						inBookName = false;
					} else if (inBookAuthor) {
						curr.author = currentValue;
						bookAuthorCheck = true;
						inBookAuthor = false;

					}
				} else if (localName.equals("varfield")) {
					inBookDetails100 = false;
					inBookDetails245 = false;
				} else if (localName.equals("record")) {
					items.add(curr);
					bookAuthorCheck = false;
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
					currentValue += new String(ch, start, length);					
				}
			}
		}
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%% LIST_ADAPTER %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	public static class SearchResultAdapter extends BaseAdapter {

		private final List<LibraryElement> items;
		private Context context;

		// protected boolean wasPressed = false;

		public SearchResultAdapter(final Context context,
				List<LibraryElement> searchList) {
			this.context = context;
			this.items = searchList;
		}

		class viewHolder {
			public TextView name;
			public TextView author;
			public ImageView photo;
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
				view = inflater.inflate(R.layout.lib_search_result_item, null);

				holder = new viewHolder();
				holder.name = (TextView) view.findViewById(R.id.lib_book_name);
				holder.author = (TextView) view
						.findViewById(R.id.lib_book_author);
				// holder.photo = (ImageView) view
				// .findViewById(R.id.lib_book_image);
				view.setTag(holder);

			} else {
				view = convertView;
				holder = (viewHolder) view.getTag();
			}

			holder.name.setText(items.get(position).name);
			holder.author.setText(items.get(position).author);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// BookDescription bD = new
					// BookDescription(holds.get(position));
					Intent intent = new Intent(context,
							BookDescriptionActivity.class);
					LibraryElement hE = items.get(position);
					String[] extraData = { hE.name, hE.author, hE.library,
							hE.id };
					intent.putExtra("description", extraData);
					((Activity) context).startActivityForResult(intent, 0);

				}
			});
			return view;
		}
	}

	/**
	 * SearchResult Activity - open this activity when user press SEARCH botton
	 */
	static public class SearchResultActivity extends CoolieActivity {

		// ///*
		// * LibraryDescriptionFragment
		// */
		// static public class LibraryDescriptionFragment extends
		// SherlockFragment {
		//
		// String name = null;
		//
		// @Override
		// public View onCreateView(LayoutInflater inflater, ViewGroup
		// container,
		// Bundle savedInstanceState) {
		// super.onCreateView(inflater, container, savedInstanceState);
		//
		// LibrariesData.Library libDetails = LibrariesData
		// .getLibraryByName(name);
		// // Inflate the layout for this fragment
		// View v = inflater
		// .inflate(R.layout.lib_activity_library_description,
		// container, false);
		//
		// if (libDetails == null) {
		// ((TextView) (v.findViewById(R.id.lib_name)))
		// .setText("No description available");
		//
		// } else {
		// ((TextView) (v.findViewById(R.id.lib_name)))
		// .setText(libDetails.name);
		// ((TextView) (v.findViewById(R.id.lib_head_librarien_name)))
		// .setText(libDetails.headLibrarian);
		// ((TextView) (v.findViewById(R.id.lib_phone_number)))
		// .setText(libDetails.phone);
		// ((TextView) (v.findViewById(R.id.lib_email_data)))
		// .setText(libDetails.email);
		// }
		// return v;
		// }
		//
		// public void setArguments(String name) {
		// this.name = name;
		// }
		//
		// }
		//
		//
		//
		// /*
		// * LibrariesListFragment
		// */
		// static public class LibrariesListFragment extends SherlockFragment {
		// private ListView mListView;
		// private TextView mEmptyView;
		// private ArrayList<String> mOpenHours;
		// @Override
		// public View onCreateView(LayoutInflater inflater, ViewGroup
		// container,
		// Bundle savedInstanceState) {
		// super.onCreateView(inflater, container, savedInstanceState);
		// mOpenHours = new ArrayList<String>();
		// mOpenHours.addAll(LibrariesData.names);
		//
		// // Inflate the layout for this fragment
		// View v = inflater.inflate(R.layout.lib_open_hours_list, container,
		// false);
		// mListView = (ListView) v.findViewById(R.id.list);
		// mEmptyView = (TextView) v.findViewById(R.id.empty);
		// mListView.setEmptyView(mEmptyView);
		//
		// mListView.setAdapter(new OpenHoursAdapter(getSherlockActivity(),
		// mOpenHours, this));
		// Log.d("LoansFrg:", "adapter set, number of items is:"
		// + ((Integer) mOpenHours.size()).toString());
		//
		// return v;
		// }
		//
		// public void notifyClickedItem(String libraryName) {
		// Log.d(" NOTIFICATION : you pressed library : " ,"" + libraryName);
		// if (getParentFragment() != null) {
		// ((OpenHoursFragment) getParentFragment())
		// .notifyClickedItem(libraryName);
		// }
		// }
		// }
		//
		//
		// /*
		// * LibraryDescriptionActivity
		// */
		// static public class LibraryDescriptionActivity extends CoolieActivity
		// {
		//
		// @Override
		// public void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		// if (getResources().getConfiguration().orientation ==
		// Configuration.ORIENTATION_LANDSCAPE) {
		// // If the screen is now in landscape mode, we can show the
		// // dialog in-line with the list so we don't need this activity.
		// finish();
		// return;
		// }
		// setContentView(R.layout.lib_activity_library_description);
		// String lib_Name = getIntent().getExtras().getString("name");
		// LibrariesData.Library tmp = LibrariesData
		// .getLibraryByName(lib_Name);
		// if (tmp == null) {
		// ((TextView) (findViewById(R.id.lib_name)))
		// .setText("No description available");
		//
		// } else {
		// ((TextView) (findViewById(R.id.lib_name))).setText(tmp.name);
		// ((TextView) (findViewById(R.id.lib_head_librarien_name)))
		// .setText(tmp.headLibrarian);
		// ((TextView) (findViewById(R.id.lib_phone_number)))
		// .setText(tmp.phone);
		// ((TextView) (findViewById(R.id.lib_email_data)))
		// .setText(tmp.email);
		// }
		//
		// }
		//
		// }
	}
}