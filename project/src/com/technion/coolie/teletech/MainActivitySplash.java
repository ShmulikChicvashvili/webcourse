package com.technion.coolie.teletech;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import com.actionbarsherlock.view.Menu;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.teletech.api.ITeletech;
import com.technion.coolie.teletech.api.TeletechFactory;

public class MainActivitySplash extends CoolieActivity {
	public static final long MILLISECONDS_PER_DAY = 86400000;
	private static DBTools dataBase = null;
	public static List<ContactInformation> master = null;
	
	private TextView progress;
	
	ProgressBar splashProgress;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_splash);
		progress = (TextView) findViewById(com.technion.coolie.R.id.progress_text);
		
		splashProgress = (ProgressBar) findViewById(com.technion.coolie.R.id.splash_progress_bar);
		
		dataBase = new DBTools(this);

		// in the future - start of off-UI-thread section
		final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		final long timeStamp = prefs.getLong("TeletechTimestamp", -1);
		if (timeStamp == -1
				|| new Date().getTime() - timeStamp >= MILLISECONDS_PER_DAY) {
			final boolean wasServerRequestSent = prefs.getBoolean(
					"WasTeletechServerRequestSent", false);
			if (!wasServerRequestSent)
				new AsyncServerContactsRequest().execute();


		} else
			new AsyncDBContactsRequest().execute();

	}

	// public static DBTools dataBase(){
	// return dataBase;
	// }
	private void finishSplash() {
		final Intent showContacts = new Intent(this, MainActivity.class);
		startActivity(showContacts);
		finish();
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(com.technion.coolie.R.menu.main_activity_splash, menu);
		return true;
	}

	private class AsyncServerContactsRequest extends
	AsyncTask<Void, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			splashProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(final Void... params) {
			//			dataBase.clearTables();
			final SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE)
					.edit();
			final ITeletech teletech = TeletechFactory.getTeletech();

			// publish progress here
			editor.putBoolean("WasTeletechServerRequestSent", true).commit();
			publishProgress("Downloading contacts...");
			master = teletech.getAllContacts();
			Assert.assertNotNull(master);

			// publish progress here
			publishProgress("Storing contacts...");
			dataBase.insertContacts(master);
			editor.putLong("TeletechTimestamp", new Date().getTime());
			editor.putBoolean("WasTeletechServerRequestSent", false);
			editor.commit();

			return Integer.toString(master.size());
		}

		@Override
		protected void onProgressUpdate(final String... values) {
			super.onProgressUpdate(values);
			progress.setText(values[0]);

		}

		@Override
		protected void onPostExecute(final String s) {
			progress.setText("Saved " + s + " contacts locally");
			final SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE)
					.edit();
			editor.putString("TeletechNumberOfDBContacts", s).commit();
			finishSplash();
		}

	}

	private class AsyncDBContactsRequest extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(final Void... params) {
			// publish progress here
			publishProgress();
			master = dataBase.getAllContacts();
			return null;
		}

		@Override
		protected void onProgressUpdate(final Void... values) {
			super.onProgressUpdate(values);
			progress.setText("Opening contact list...");
		}

		@Override
		protected void onPostExecute(final Void result) {
			super.onPostExecute(result);
			splashProgress.setVisibility(View.GONE);
			finishSplash();
		}


	}

}
