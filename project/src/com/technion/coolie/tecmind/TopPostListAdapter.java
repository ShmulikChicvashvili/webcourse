package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.Date;

import com.technion.coolie.R;
import com.technion.coolie.tecmind.server.TecPost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class TopPostListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<TecPost> posts;

	public TopPostListAdapter(Context context, ArrayList<TecPost> item) {
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
			view = inflater.inflate(R.layout.techmind_top_post_list_view, null);
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

		Date date = new Date();
		TecPost post = (TecPost) getItem(position);
		holder.postContent.setText("....post content");
		holder.date.setText(post.getDate().toString());
		holder.comments_amount.setText("Recieved "+String.valueOf(post.getCommentCount()) + " Comments on this post");
		holder.comments_amount.setText("Recieved "+String.valueOf(post.getLikesCount()) + " Likes on this post");
		return view;
	}
}
