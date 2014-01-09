package com.technion.coolie.techlibrary;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.technion.coolie.CollieNotification;
import com.technion.coolie.CollieNotification.Priority;
import com.technion.coolie.HtmlGrabber;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.techlibrary.BookItems.LoanElement;
import com.technion.coolie.techlibrary.BookItems.HoldElement;
import com.technion.coolie.techlibrary.util.HoldsInfoXMLHandler;
import com.technion.coolie.techlibrary.util.LoansInfoXMLHandler;
import com.technion.coolie.techlibrary.MainActivity;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class testIntentService extends IntentService {
	private static final String userLoansUrl = "https://aleph2.technion.ac.il/X?op=bor-info&bor_id=";
	private String userId;
	private ArrayList<LoanElement> loansList;
	private ArrayList<HoldElement> HoldsList;
	
	public testIntentService() {
		super("test");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		userId = intent.getStringExtra("userID");
		HtmlGrabber hg = new HtmlGrabber(getApplicationContext()) {
			@Override
			public void handleResult(String result, CoolieStatus status) {
				if (status == CoolieStatus.RESULT_OK) {
					//parse result
					HoldsInfoXMLHandler holdsXMLHandler = new HoldsInfoXMLHandler();
					LoansInfoXMLHandler loansXMLHandler = new LoansInfoXMLHandler();
					try {
						SAXParserFactory spf = SAXParserFactory.newInstance();
						SAXParser sp = spf.newSAXParser();
						XMLReader xr = sp.getXMLReader();
						XMLReader xr2 = sp.getXMLReader();

						//holds
						xr.setContentHandler(holdsXMLHandler);
						xr.parse(new InputSource(new StringReader(result)));
						//loans
						xr2.setContentHandler(loansXMLHandler);
						xr2.parse(new InputSource(new StringReader(result)));
					} catch (Exception e) {
						// TODO: ?
						Log.e("woooot:", "exception - " + e.getClass().toString(), e);
					}
					loansList = loansXMLHandler.loanList;
					HoldsList = holdsXMLHandler.holdList;
					
					//maybe update DB
				
					//check if there is a loan to be returned in the next three days and notify
					Calendar cal = Calendar.getInstance();
					Date today = cal.getTime();
					cal.add(Calendar.DAY_OF_YEAR,7);
					Date todayPlus3 = cal.getTime();
					Log.d("datesDebug",today.toString()+", and 3 days later:" + todayPlus3.toString());
					SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
					//loans
					for(LoanElement loan : loansList) {
						//check if due date is within 3 days or overdue
						
						try {
							if(curFormater.parse(loan.dueDate).before(todayPlus3)) {
								//change boody message (library+book name+author+dueDate)
								new CollieNotification("Book Return",loan.name+" Due date: "+loan.dueDate,new MainActivity(),
										Priority.IN_A_DAY,false,getApplicationContext()).sendNotification();
							}

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					
					
					
					//check if there is a request to be picked and notify
					//		want to notify when arrive date is generated (request is here!)
					//		
					
					
					
				} else {
					
				}
			}
		};
		hg.getHtmlSource(userLoansUrl + userId, HtmlGrabber.Account.NONE);
	}
	
}
