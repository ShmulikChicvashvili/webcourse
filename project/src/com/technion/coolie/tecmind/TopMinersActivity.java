package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.technion.coolie.R;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TechmineAPI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class TopMinersActivity extends Activity {
	TechmineAPI connector = new TechmineAPI();
	List<TecUser> topMiners = new ArrayList<TecUser>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.techmind_activity_top_miners);
		final ListView listView = (ListView) findViewById(R.id.minersListView);
		TopMinersListAdapter newPostAdapter = new TopMinersListAdapter(
				TopMinersActivity.this, topMiners);
		listView.setAdapter(newPostAdapter);
		try {
			List<TecUser> list = new ServerTopMiners().execute().get();
			for (int i = 0; i < list.size(); i++)
				topMiners.add((TecUser) list.get(i));
		} catch (InterruptedException e) {

		} catch (ExecutionException e) {

		}
	}

	class ServerTopMiners extends AsyncTask<Void, Void, List<TecUser>> {

		@Override
		protected List<TecUser> doInBackground(Void... params) {
			// return connector.getTopMiners();
			return null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_miners, menu);
		return true;
	}

}
