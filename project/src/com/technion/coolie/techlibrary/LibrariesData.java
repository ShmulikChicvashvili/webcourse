package com.technion.coolie.techlibrary;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.util.Log;

public class LibrariesData {
	public static ArrayList<Library> librariesList = null;
	public static ArrayList<String> names = null;

	public static void buildList(Context context, int resource) {
		
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
		public String name;
		public String headLibrarian;
		public String phone;
		public String email;
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

	private static class LibrariesInfoXMLHandler extends DefaultHandler {
		private boolean currentElement = false;
		private String currentValue = null;

		private Library currLibrary = null;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			currentElement = true;
			if (localName.equals("resources")) {
				/** Start */
				librariesList = new ArrayList<LibrariesData.Library>();
				names = new ArrayList<String>();
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
			if (localName.equals("name")) {
				currLibrary.name = currentValue;
				names.add(currentValue);
			} else if (localName.equals("headLibrarian")) {
				currLibrary.headLibrarian = currentValue;
			} else if (localName.equals("headLibrarian")) {
				currLibrary.headLibrarian = currentValue;
			} else if (localName.equals("phone")) {
				currLibrary.phone = currentValue;
			} else if (localName.equals("email")) {
				currLibrary.email = currentValue;
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
