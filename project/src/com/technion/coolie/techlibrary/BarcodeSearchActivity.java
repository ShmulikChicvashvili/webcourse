package com.technion.coolie.techlibrary;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.scan.IntentIntegrator;
import com.technion.coolie.techlibrary.scan.IntentResult;

public class BarcodeSearchActivity extends Activity {
	private String barcodeSearchUrl = "https://aleph2.technion.ac.il/X?op=ill-item-by-bc&barcode=";
	private String bookDescriptionUrl = "http://aleph2.technion.ac.il/X?op=ill-get-doc-short&library=tec01&doc_no=";

	private static final String SHARED_PREF_BARCODE = "lib_pref2";
	private static final String BARCODE = "barcode";
	private static final String FORMAT = "format";
	private String FINISHED = "finished";
	private SharedPreferences mSharedPref;
	private SharedPreferences.Editor mSharedPrefEditor;
	protected String ERROR_NO_RECORED_FOUND = "<error>Item record could not be found</error>";
	protected String TOAST_NO_RECORD = "No books with given barcode in database";
	private String TOAST_NO_SCAN = "No book scan data received!";

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.v("barcode", "onCreate!");

		setContentView(R.layout.lib_activity_barcode_search);

		mSharedPref = getSharedPreferences(SHARED_PREF_BARCODE, 0);
		mSharedPrefEditor = mSharedPref.edit();
		if (mSharedPref.getBoolean(FINISHED, true)) {
			scanForBook();
		}
		mSharedPrefEditor.putBoolean(FINISHED, true);
		mSharedPrefEditor.commit();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (!isFinishing()) {
			mSharedPrefEditor.putBoolean(FINISHED, false);
			mSharedPrefEditor.commit();
		}
	}

	public void scanForBook() {
		// scan for barcode
		Log.v("barcode", "scanforbook");
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		// retrieve result of scanning - instantiate ZXing object
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);

		// check we have a valid result
		if (scanResult != null) {
			// get content from Intent Result
			String scanContent = scanResult.getContents();
			// get format name of data scanned
			String scanFormat = scanResult.getFormatName();
			Log.v("SCAN", "content: " + scanContent + " - format: "
					+ scanFormat);

			// search for the book
			Intent barcodeData = new Intent();
			if (scanContent != null && scanFormat != null) {
				// bloody intent wont work! using shared prefs
				//barcodeData.putExtra(BARCODE, scanContent);
				//barcodeData.putExtra(FORMAT, scanFormat);
				mSharedPrefEditor.putString(BARCODE, scanContent);
				mSharedPrefEditor.putString(FORMAT, scanFormat);
				mSharedPrefEditor.commit();
				setResult(RESULT_OK, barcodeData);
				//searchBook(scanContent); TODO: delete
			} else {
				// invalid scan data or scan canceled
				setResult(RESULT_CANCELED);
			}
		}
		finish();
	}
//TODO: delete!
//	private void searchBook(String scanContent) {
//		HtmlGrabber hg = new HtmlGrabber(this) {
//			String bookDocNum = null;
//
//			@Override
//			public void handleResult(String result, CoolieStatus status) {
//				if (status == CoolieStatus.RESULT_OK) {
//					// checking for error TODO: check other error?
//					if (result.contains(ERROR_NO_RECORED_FOUND)) {
//						showToastAndFinish(TOAST_NO_RECORD); 
//						//TODO: maybe change to dialog alert with ok button
//					} else {
//						BarCodeResultXMLHandler xmlhandler = new BarCodeResultXMLHandler();
//						try {
//							SAXParserFactory spf = SAXParserFactory
//									.newInstance();
//							SAXParser sp = spf.newSAXParser();
//							XMLReader xr = sp.getXMLReader();
//
//							xr.setContentHandler(xmlhandler);
//							xr.parse(new InputSource(new StringReader(result)));
//						} catch (Exception e) {
//							// TODO: ?
//							Log.e("woooot:", "exception - "
//									+ e.getClass().toString(), e);
//						}
//
//						bookDocNum = xmlhandler.docNum;
//						if (bookDocNum == null) {
//							// TODO: error - think why and generate error
//							Log.e("BarcodeSearch", "no doc-num!? " + result);
//							Log.e("BarcodeSearch", result);
//						} else {
//							getBookInfo(bookDocNum);
//						}
//					}
//				} else {
//					// TODO: generate error
//				}
//			}
//		};
//		hg.getHtmlSource(barcodeSearchUrl + scanContent,
//				HtmlGrabber.Account.NONE);
//	}
//
//	protected void getBookInfo(String bookDocNum) {
//		HtmlGrabber hg = new HtmlGrabber(this) {
//			@Override
//			public void handleResult(String result, CoolieStatus status) {
//				if (status == CoolieStatus.RESULT_OK) {
//					// get book info and call book desc activity
//					BookDescXMLHandler bookXmlHandler = new BookDescXMLHandler();
//					try {
//						SAXParserFactory spf = SAXParserFactory.newInstance();
//						SAXParser sp = spf.newSAXParser();
//						XMLReader xr = sp.getXMLReader();
//
//						xr.setContentHandler(bookXmlHandler);
//						xr.parse(new InputSource(new StringReader(result)));
//					} catch (Exception e) {
//						// TODO: ?
//						Log.e("woooot:", "exception - "
//								+ e.getClass().toString(), e);
//					}
//					// Opening book description
//					Intent intent = new Intent(BarcodeSearchActivity.this,
//							BookDescriptionActivity.class);
//					String[] extraData = { bookXmlHandler.title,
//							bookXmlHandler.author, "kazachstan faculty" };
//					intent.putExtra("description", extraData);
//					startActivity(intent);
//					finish();
//				} else {
//
//					// TODO: generate error
//				}
//			}
//		};
//		hg.getHtmlSource(bookDescriptionUrl + bookDocNum,
//				HtmlGrabber.Account.NONE);
//	}

	protected void showToastAndFinish(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.show();
		finish();
	}

	/*
	 * XML parser
	 */
	protected class BarCodeResultXMLHandler extends DefaultHandler {
		private boolean currentElement = false;
		private String currentValue = null;
		String docNum = null;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			currentElement = true;
			// if (localName.equals("error")) {
			// /* ? */
			// throw new SAXException("bad something");
			// }
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
			if (localName.equals("z30-doc-number")) {
				docNum = currentValue;
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
			}
		}
	}

	/*
	 * XML parser
	 */
	protected class BookDescXMLHandler extends DefaultHandler {
		private boolean currentElement = false;
		private String currentValue = null;
		String title = null;
		String author = null;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			currentElement = true;
			if (localName.equals("error")) {
				/* ? */
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
			if (localName.equals("z13-title")) {
				title = currentValue;
			} else if (localName.equals("z13-author")) {
				author = currentValue;
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
			}
		}
	}

}
