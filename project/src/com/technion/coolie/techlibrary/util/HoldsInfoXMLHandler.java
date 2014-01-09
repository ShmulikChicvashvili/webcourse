package com.technion.coolie.techlibrary.util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.technion.coolie.techlibrary.BookItems;
import com.technion.coolie.techlibrary.BookItems.HoldElement;

/*
 * XML parser for Loans + requests info.
 */
public class HoldsInfoXMLHandler extends DefaultHandler {
	private String currentValue = null;

	private boolean isInHold = false;
	private HoldElement currentHold = null;
	public ArrayList<HoldElement> holdList = null;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentValue = new String();
		if (localName.equals("bor-info")) {
			/** Start */
			holdList = new ArrayList<HoldElement>();
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
	 * Called when tag closing ( ex:- <name>AndroidPeople</name> -- </name>
	 * )
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(currentValue.isEmpty()){
			currentValue = "N/A";
		}
		/** set value */
		// book-item
		if (localName.equals("z30-doc-number")) {
			if (isInHold) {
				currentHold.id = currentValue;
			}
		} else if (localName.equals("z13-title")) {
			if (isInHold) {
				currentValue = currentValue.substring(0, currentValue
						.indexOf("/") == -1 ? currentValue.length()
						: currentValue.indexOf("/") - 1);
				currentHold.name = currentValue;
			}
		} else if (localName.equals("z13-author")) {
			if (isInHold) {
				currentHold.author = currentValue;
			}
		} else if (localName.equals("z30-material")) {
			if (isInHold) {
				currentHold.type = currentValue;
			}
		} else if (localName.equals("z30-sub-library")) {
			if (isInHold) {
				currentHold.library = currentValue;
			}
		} else if (localName.equals("z37-request-date") && isInHold) {
			currentHold.createDate = currentValue;
		} else if (localName.equals("z37-hold-date") && isInHold) {
			currentHold.arrivalDate = currentValue;
		} else if (localName.equals("z37-end-hold-date") && isInHold) {
			currentHold.endHoldDate = currentValue;
		} else if (localName.equals("z37-sequence") && isInHold) {
			currentHold.queuePosition = (new Integer(currentValue))
					.toString();
		} else if (localName.equals("z37-item-sequence") && isInHold) {
			currentHold.itemSequence = currentValue;
		} else if (localName.equals("item-h") && isInHold) {
			holdList.add(currentHold);
			currentHold = new BookItems().new HoldElement();
			isInHold = false;
		}
	}

	/**
	 * Called to get tag characters ( ex:- <name>AndroidPeople</name> -- to
	 * get AndroidPeople Character )
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
				currentValue += new String(ch, start, length);
			}
}
