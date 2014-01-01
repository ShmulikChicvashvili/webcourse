package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.tecmind.server.TecPost;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class TopPostListAdapter extends BaseAdapter {

	private Context mContext;
	private List<TecPost> posts;

	public TopPostListAdapter(Context context, List<TecPost> item) {
		Log.d("TopPostListAdapter", "TopPostListAdapter***");
		mContext = context;
		posts = item;
	}

	@Override
	public int getCount() {
		return posts.size();
	}

	@Override
	public Object getItem(int position) {
		return posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class HoldView {
		TextView postContent;
		TextView comments_amount;
		TextView likes_amount;
		TextView date;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		HoldView holder = null;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			Log.d("topPostAdapte", "convertView = null");
			view = inflater.inflate(R.layout.techmind_top_post_list_view, null);
			Log.d("techmind_top_post_list_view", "techmind_top_post_list_view***");
			holder = new HoldView();
			holder.postContent = (TextView) view.findViewById(R.id.post_content);
			holder.date = (TextView) view.findViewById(R.id.date);
			holder.comments_amount = (TextView) view.findViewById(R.id.comments_amount);
			holder.likes_amount = (TextView) view.findViewById(R.id.likes_amount);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (HoldView) view.getTag();
		}

		TecPost post = (TecPost) getItem(position);
		int index = post.getDate().toString().indexOf("GMT");
		String myPost = "my post is...";
		holder.postContent.setText(myPost);
		holder.date.setText(post.getDate().toString().subSequence(0, index));
		holder.comments_amount.setText("Recieved "+String.valueOf(post.getCommentCount()) + " Comments on this post");
		Log.d("comment_amount", String.valueOf(post.getCommentCount()));
		holder.likes_amount.setText("Recieved "+String.valueOf(post.getLikesCount()) + " Likes on this post");
		return view;
	}
}
