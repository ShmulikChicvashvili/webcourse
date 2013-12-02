package com.technion.coolie.skeleton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.content.Intent;

public class FileManaggerService extends IntentService {

	public FileManaggerService() {
		super("FileManagger");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}
}
