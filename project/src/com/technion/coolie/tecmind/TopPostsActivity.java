package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.sileria.util.Log;
import com.technion.coolie.R;
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
	List<TecPost> topPosts = new ArrayList<TecPost>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.techmind_activity_top_posts);
		final ListView listView = (ListView) findViewById(R.id.myListView);
		TopPostListAdapter newPostAdapter = new TopPostListAdapter(
				TopPostsActivity.this, topPosts);
		listView.setAdapter(newPostAdapter);
		try {
			List<TecPost> list = new ServerTopPosts().execute().get();
			for (int i = 0; i < list.size(); i++)
				topPosts.add((TecPost) list.get(i));
		} catch (InterruptedException e) {
			Log.e("Interupt*****", "e");
		} catch (ExecutionException e) {
			Log.e("exception*****", "e");
		}

	}

	class ServerTopPosts extends AsyncTask<Void, Void, List<TecPost>> {

		@Override
		protected List<TecPost> doInBackground(Void... params) {
			return connector.getTopBestPosts();
		}

		// @Override
		// protected void onPostExecute(List<TecPost> result) {
		// // save the list in the internal storage
		// for (TecPost post : result) {
		// if (post != null){
		// String id1 =post.getId();
		// String content1 ="hey";
		// Date date1 = post.getDate();
		// int technionValue1 = post.getTechnionValue();
		// String userID1 =post.getUserID();
		// int likesCount1 =post.getLikesCount();
		// int commentCount1 =post.getCommentCount();
		// TecPost newpost = new
		// TecPost(id1,content1,date1,technionValue1,userID1,likesCount1,commentCount1);
		// topPosts.add(newpost);
		// }
		// }
		// }
	}

}
