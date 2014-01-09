package com.technion.coolie.techlibrary.util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.technion.coolie.techlibrary.BookItems;
import com.technion.coolie.techlibrary.BookItems.LoanElement;

/*
 * XML parser for Loans + requests info.
 */
public class LoansInfoXMLHandler extends DefaultHandler {
	private String currentValue = null;

	private boolean isInLoan = false;
	private LoanElement currentLoan = null;

	public ArrayList<LoanElement> loanList = null;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentValue = new String();

		if (localName.equals("bor-info")) {
			/** Start */
			loanList = new ArrayList<LoanElement>();
		}
		if (localName.equals("item-l")) {
			/** Start loan */
			currentLoan = new BookItems().new LoanElement();
			isInLoan = true;
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
		if (isInLoan) {
			if (localName.equals("z30-doc-number")) {
				currentLoan.id = currentValue;
			} else if (localName.equals("z13-title")) {
				currentValue = currentValue.substring(0, currentValue
						.indexOf("/") == -1 ? currentValue.length()
						: currentValue.indexOf("/") - 1);
				currentLoan.name = currentValue;
			} else if (localName.equals("z13-author")) {
				currentLoan.author = currentValue;
			} else if (localName.equals("z30-material")) {
				currentLoan.type = currentValue;
			} else if (localName.equals("z30-sub-library")) {
				currentLoan.library = currentValue;
			}
			// loans specific
			else if (localName.equals("z36-due-date") && isInLoan) {
				currentLoan.dueDate = currentValue;
			}
			// adding elements to list
			else if (localName.equals("item-l") && isInLoan) {
				loanList.add(currentLoan);
				isInLoan = false;
			}
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
