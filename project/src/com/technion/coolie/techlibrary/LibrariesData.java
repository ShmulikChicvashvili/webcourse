package com.technion.coolie.techlibrary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.google.android.gms.maps.model.LatLng;
import com.technion.coolie.techlibrary.BookItems.LibraryElement;
import com.technion.coolie.techlibrary.LibrariesData.Library;
import com.technion.coolie.R;

import android.content.Context;
import android.util.Log;

public class LibrariesData {
	private static ArrayList<Library> librariesList = null;
	private static ArrayList<String> names = null;

	public static void buildList(Context context, int resource) {
		
		getLibrariesName(context);
		// parser
		LibrariesInfoXMLHandler loansXMLHandler = new LibrariesInfoXMLHandler();
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			xr.setContentHandler(loansXMLHandler);
			InputStream is = context.getResources().openRawResource(resource);
			xr.parse(new InputSource(is));
		} catch (Exception e) {
			// TODO: ?
			Log.e("woooot:", "exception - " + e.getClass().toString(), e);
		}
	}

	//Library element
	public static class Library {
		public int id;
		public String name;
		public String headLibrarian;
		public String phone;
		public String email;
		public LatLng location;
	}

	static public Library getLibraryByName(String name) {
		if (name == null)
			return null;
		for (int i = 0; i < librariesList.size(); i++) {
			if (librariesList.get(i).name.compareTo(name) == 0) {
				return librariesList.get(i);
			}
		}
		return null;
	}
	static public Library getLibraryById(int id) {
		for(Library library : librariesList) {
			if (library.id == id) {
				return library;
			}
		}
		return null;
	}
	
	public static ArrayList<Library> getLibrariesList() {
		//TODO: just return libraryList?
		ArrayList<Library> list = new ArrayList<Library>();
		list.addAll(librariesList);
		return list;
	}
	
	private static void getLibrariesName(Context context) {
		//damn ugly :\
		names = new ArrayList<String>();
		names.add(context.getString(R.string.lib_library0_name));
		names.add(context.getString(R.string.lib_library1_name));
		names.add(context.getString(R.string.lib_library2_name));
		names.add(context.getString(R.string.lib_library3_name));
		names.add(context.getString(R.string.lib_library4_name));
		names.add(context.getString(R.string.lib_library5_name));
		names.add(context.getString(R.string.lib_library6_name));
		names.add(context.getString(R.string.lib_library7_name));
		names.add(context.getString(R.string.lib_library8_name));
		names.add(context.getString(R.string.lib_library9_name));
		names.add(context.getString(R.string.lib_library10_name));
		names.add(context.getString(R.string.lib_library11_name));
		names.add(context.getString(R.string.lib_library12_name));
		names.add(context.getString(R.string.lib_library13_name));
		names.add(context.getString(R.string.lib_library14_name));
		names.add(context.getString(R.string.lib_library15_name));
	}

	private static class LibrariesInfoXMLHandler extends DefaultHandler {
		private boolean currentElement = false;
		private String currentValue = null;
		private String currentLibLatitude = null;
		private Library currLibrary = null;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			currentElement = true;
			if (localName.equals("resources")) {
				/** Start */
				librariesList = new ArrayList<LibrariesData.Library>();
			}
			if (localName.equals("library")) {
				/** Start library */
				currLibrary = new Library();
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
			// book-item
			if (localName.equals("id")) {
				currLibrary.id = Integer.parseInt(currentValue);
			} else if (localName.equals("name")) {
				currLibrary.name = names.get(currLibrary.id);
			} else if (localName.equals("headLibrarian")) {
				currLibrary.headLibrarian = currentValue;
			} else if (localName.equals("headLibrarian")) {
				currLibrary.headLibrarian = currentValue;
			} else if (localName.equals("phone")) {
				currLibrary.phone = currentValue;
			} else if (localName.equals("email")) {
				currLibrary.email = currentValue;
			} else if (localName.equals("latitude")) {
				currentLibLatitude = currentValue;
			} else if (localName.equals("longitude")) {
				currLibrary.location = 
						new LatLng(Double.parseDouble(currentLibLatitude), 
											Double.parseDouble(currentValue));
			} else if (localName.equals("library")) {
				librariesList.add(currLibrary);
				currLibrary = new Library();
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
					currentValue.replace("&apos;", "'");
					currentValue.replace("&quot;", "\"");
					currentValue.replace("&amp;", "&");
					currentElement = false;
				}
			} else
				currentValue = "";
		}
	}
}
