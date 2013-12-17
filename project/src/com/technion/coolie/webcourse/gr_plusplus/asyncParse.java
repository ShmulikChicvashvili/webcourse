package com.example.gr_plusplus;

import java.util.List;

import support_communication.api.CommunicationServiceFactory;
import support_communication.api.IHtmlService;

import android.os.AsyncTask;
import com.example.gr_plusplus.*;
import com.parser.gr_plusplus.parser;


public class asyncParse<T> extends AsyncTask<String, Void , List<T>> {

	@Override
	protected List<T> doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
}

//final Bitmap b = loadImageFromNetwork();
//
//      mImageView.setImageBitmap(b);