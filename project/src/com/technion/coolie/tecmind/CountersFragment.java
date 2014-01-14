package com.technion.coolie.tecmind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.User;


public class CountersFragment extends Fragment {
	TextView postNum;
	TextView commentsNum;
	TextView likesNum;
	int likes = 0;
	int comments = 0;
	int posts = 0;
	int total = 0;
	TextView totalTechions;
	View inflateView;


	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		   inflateView = (LinearLayout)inflater.inflate(
					R.layout.techmind_total_counters_new, container, false);
		   
		   posts = User.getUserInstance(null).postsNum;
		   comments = User.getUserInstance(null).commentsNum;
		   likes = User.getUserInstance(null).likesOnPostsNum;
		   total = User.getUserInstance(null).totalTechoins;
			
		   initiateNumViews();
		   setNumTexts();
		   paintBars();
	       return inflateView;
	    }
	
	private void setNumTexts() {
		postNum.setText(String.valueOf(posts));
		commentsNum.setText(String.valueOf(comments));
		likesNum.setText(String.valueOf(likes));
		totalTechions.setText(String.valueOf(total) +  " Techoins");
	}
	
	private void paintBars() {
		
		int[] vals = {posts, comments, likes};
		int max = vals[0];
		for (int i = 0; i < 2; i++) {
			if (vals[i] < vals[i+1]) {
				max = vals[i+1];
			}
		}
		
		if (max > 0) {
			paintBar(R.id.post_bar, R.id.post_blue_bar, posts, max);
			paintBar(R.id.comment_bar, R.id.comment_blue_bar, comments, max);
			paintBar(R.id.like_bar, R.id.like_blue_bar, likes, max);	
		}
				
	}
		
		private void paintBar(int barId, int blueBarId, int val, int max) {
		
			RelativeLayout bar = (RelativeLayout)inflateView.findViewById(barId);
			int barHeigt = bar.getLayoutParams().height;
			int blueBarHeigt = barHeigt * val / max;
			if (blueBarHeigt == 0) {
				blueBarHeigt = 5;
			}

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, blueBarHeigt);
			RelativeLayout blueBar = (RelativeLayout)inflateView.findViewById(blueBarId);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			blueBar.setLayoutParams(params);			
	}


	private void initiateNumViews() {
		postNum = (TextView) inflateView.findViewById(R.id.tot_account_number_posts);
		commentsNum = (TextView) inflateView.findViewById(R.id.tot_account_number_comments);
		likesNum = (TextView) inflateView.findViewById(R.id.tot_account_number_likes);
		totalTechions = (TextView) inflateView.findViewById(R.id.tot_account_current_total);

	}
}
