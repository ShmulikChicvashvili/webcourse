package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.tecmind.server.ReturnCode;
import com.technion.coolie.tecmind.server.TecPost;
import com.technion.coolie.tecmind.server.TechmineAPI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class TopPostsActivity extends Activity {
	TechmineAPI connector = new TechmineAPI();
	ArrayList<TecPost> topPosts = new ArrayList<TecPost>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.techmind_activity_top_posts);
		final ListView listView = (ListView) findViewById(R.id.myListView);
		TopPostListAdapter newPostAdapter = new TopPostListAdapter(this,
				topPosts);
		listView.setAdapter(newPostAdapter);
		new ServerTopPosts().execute();
	}

	class ServerTopPosts extends AsyncTask<Void, Void, List<TecPost>> {

		@Override
		protected List<TecPost> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return connector.getTopBestPosts();
		}

		@Override
		protected void onPostExecute(List<TecPost> result) {
			// save the list in the internal storage
			for (TecPost post : result) {
				topPosts.add(post);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_posts, menu);
		return true;
	}

}
