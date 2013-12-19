package com.technion.coolie.techlibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class LibrariesData {
	public static ArrayList<Library> librariesList = null;
	private static String data = "<resources>        	<library>      	<name>Elyachar Central Library</name> 		<headLibrarian>Dalia Dolev - Director</headLibrarian> 		<phone>829-2507</phone> 		<email>ddalia@cl.technion.ac.il</email>      	</library>    	<library>     	<name>Aerospace Engineering</name> 		<headLibrarian>Ludmila Bronnikov</headLibrarian> 		<phone>829-2310</phone> 		<email>ludmilab@tx.technion.ac.il</email>       	</library>      <library>     	<name>Architecture and Town Planning</name> 		<headLibrarian>Viki Davidov</headLibrarian> 		<phone>829-4010</phone> 		<email>arclib@tx.technion.ac.il</email>        </library>    			     <library>     	<name>Biomedical Engineering</name> 		<headLibrarian>Ronit Revach</headLibrarian> 		<phone>829-4126</phone> 		<email>bmlib@tx.technion.ac.il</email>        </library>     <library>     	<name>Chemical Engineering, Biotechnology and Food Engineering</name>     	<headLibrarian>Ruth Shaviv</headLibrarian>     	<phone>829-3075</phone>     	<email>foodlib@tx.technion.ac.il</email>     </library> 	<library>     	<name>Chemistry and Biology</name>     	<headLibrarian>Hana Ilovich</headLibrarian>     	<phone>829-3734</phone>     	<email>ihana@tx.technion.ac.il</email>     </library> 	<library>     	<name>Civil and Environmental Engineering</name>     	<headLibrarian>Tamara Tal</headLibrarian>     	<phone>829-2731</phone>     	<email>civlib@tx.technion.ac.il</email> 	</library> 	<library>     	<name>Computer Science</name>     	<headLibrarian>Ariella Weinstein</headLibrarian>     	<phone>829-4870</phone>     	<email>cslib@cs.technion.ac.il</email>     </library> 	<library>     	<name>Department of Education in Science and Technology</name>     	<headLibrarian>Olga Kizner-Neznansky</headLibrarian>     	<phone>829-3109</phone>     	<email>edulib@tx.technion.ac.il</email>    	</library> 	<library>     	<name>Electrical Engineering</name>     	<headLibrarian>Galit Grinberg</headLibrarian>     	<phone>829-4772</phone>     	<email>galit@ee.technion.ac.il</email>    	</library> 	<library>     	<name>Industrial Engineering and Management</name>     	<headLibrarian>Giora Meisler</headLibrarian>     	<phone>829-2038</phone>     	<email>giora@tx.technion.ac.il</email> 	</library> 	<library>     	<name>Mathematics</name>     	<headLibrarian>Jenny Rudyk</headLibrarian>     	<phone>829-4283</phone>     	<email>mathlib@tx.technion.ac.il</email>     </library> 	<library>     	<name>Mechanical Engineering</name>     	<headLibrarian>Shelly Imberg</headLibrarian>     	<phone>829-2082</phone>     	<email>meshelly@tx.technion.ac.il</email>     </library> 	<library>     	<name>Medicine</name>     	<headLibrarian>Margie S. Cohn</headLibrarian>     	<phone>829-5350</phone>     	<email>margie@tx.technion.ac.il</email>     </library> 	<library>     	<name>Physics</name>     	<headLibrarian>Elena Bekker</headLibrarian>     	<phone>829-3535</phone>     	<email>physlib@physics.technion.ac.il</email>     </library> 	<library>    		<name>Transportation Research Institute</name>    		<headLibrarian>Ronit Revach</headLibrarian>     	<phone>829-2385</phone>     	<email>trrlib@tx.technion.ac.il</email>     </library> </resources> ";
	public static ArrayList<String> names = null;
	
	
	public static void buildList() {

//		File f = new File("lib_files/DB.xml");

		 // parser
		 LibrariesInfoXMLHandler loansXMLHandler = new LibrariesInfoXMLHandler();
		 try {
		 SAXParserFactory spf = SAXParserFactory.newInstance();
		 SAXParser sp = spf.newSAXParser();
		 XMLReader xr = sp.getXMLReader();
		
		 /** Create handler to handle XML Tags ( extends DefaultHandler ) */
		 xr.setContentHandler(loansXMLHandler);
		 xr.parse(new InputSource(new StringReader(data)));
		 } catch (Exception e) {
		 // TODO: ?
		 Log.e("woooot:", "exception - " + e.getClass().toString(), e);
		 }

		Log.d("hjhhhhhhhhhhh", "" + librariesList.size());
	}

	public static class Library {
		public String name;
		public String headLibrarian;
		public String phone;
		public String email;

	}
	static public Library getLibraryByName(String name){
		if(name == null)
			return null;
		for(int i = 0; i < librariesList.size(); i++){
			if(librariesList.get(i).name.compareTo(name) == 0){
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
