package com.technion.coolie.tecmind;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.BL.Utilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecentAccountActivity extends SherlockFragment {
	TextView postNum;
	TextView commentsNum;
	TextView likesNum;
	TextView postValue;
	TextView commentsValue;
	TextView likesValue;
	TextView totalTechions;
	View inflateView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflateView = (LinearLayout) inflater.inflate(
				R.layout.techmind_activty_recent_account, container, false);
		initiateNumViews();
		initiateValueViews();
		int userPost = User.getUserInstance(null).postsNum;
		int userComments = User.getUserInstance(null).commentsNum;
		int userLikes = User.getUserInstance(null).likesNum + User.getUserInstance(null).likesOnPostsNum;
		setNumTexts(userPost, userComments, userLikes);
		setValueTexts(userPost, userComments, userLikes);
		return inflateView;
	}

	private void setNumTexts(int userPost, int userComments, int userLikes) {
		postNum.setText(String.valueOf(userPost));
		commentsNum.setText(String.valueOf(userComments));
		likesNum.setText(String.valueOf(userLikes));
	}

	private void setValueTexts(int userPost, int userComments, int userLikes) {
		postValue
				.setText(String.valueOf(Utilities.calculatePostsAux(userPost)) + "   Techions");
		commentsValue.setText(String.valueOf(Utilities
				.calculateCommentsAux(userComments)) + "   Techions");
		likesValue.setText(String.valueOf(Utilities
				.calculatelikesAux(userLikes)) + "   Techions");
		totalTechions.setText(String.valueOf(User.getUserInstance(null).totalTechoins) +  "   Techions");
	}

	private void initiateNumViews() {
		postNum = (TextView) inflateView.findViewById(R.id.number_posts);
		commentsNum = (TextView) inflateView.findViewById(R.id.number_comments);
		likesNum = (TextView) inflateView.findViewById(R.id.number_likes);

	}
	
	private void initiateValueViews(){
		postValue = (TextView) inflateView.findViewById(R.id.current_posts);
		commentsValue = (TextView) inflateView
				.findViewById(R.id.current_comments);
		likesValue = (TextView) inflateView.findViewById(R.id.current_likes);
		totalTechions = (TextView) inflateView.findViewById(R.id.current_total);
	}
}