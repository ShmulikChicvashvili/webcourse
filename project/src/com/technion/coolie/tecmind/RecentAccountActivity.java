package com.technion.coolie.tecmind;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Mine;
import com.technion.coolie.tecmind.BL.Post;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.BL.Utilities;
import com.technion.coolie.tecmind.MineActivity.ServerUpdateUserData;
import com.technion.coolie.tecmind.server.ReturnCode;
import com.technion.coolie.tecmind.server.TecPost;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TecUserTitle;
import com.technion.coolie.tecmind.server.TechmineAPI;

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
				R.layout.techmind_activity_recent_account, container, false);
		initiateNumViews();
		initiateValueViews();
		setValueTexts();
		setDates();
		return inflateView;
	}
	
	void setDates() {
		
		if (MineActivity.newMiningDate == null) {
			Toast.makeText(getActivity(), "You have'nt mined yet.",
	        		Toast.LENGTH_LONG).show();
			
			LinearLayout from = (LinearLayout) inflateView.findViewById(R.id.rec_account_from_layout);
			//from.setVisibility(View.INVISIBLE);
			from.setVisibility(View.INVISIBLE);
					
		}
		else{
			TextView from = (TextView) inflateView.findViewById(R.id.rec_account_from_data);
			int index = MineActivity.exMiningDate.toString().indexOf("GMT");
			from.setText(MineActivity.exMiningDate.toString().subSequence(0, index));
			//TextView to = (TextView) inflateView.findViewById(R.id.rec_account_to_data);
			//index = MineActivity.newMiningDate.toString().indexOf("GMT");
			//to.setText(MineActivity.newMiningDate.toString().subSequence(0, index));
			//TextView total = (TextView) inflateView.findViewById(R.id.rec_account_current_total);
			//total.setText(String.valueOf(MineActivity.totalDelta));
		}
		
	}

	private void setValueTexts() {
		
		if (MineActivity.newMiningDate == null) {
			postValue.setText("0   Techoins");
			commentsValue.setText("0   Techoins");
			likesValue.setText("0   Techoins");
			totalTechions.setText("0   Techoins");
		} else {
			postValue.setText(String.valueOf(MineActivity.postsDelta) + "   Techoins");
			commentsValue.setText(String.valueOf(MineActivity.commentsDelta) + "   Techoins");
			likesValue.setText(String.valueOf(MineActivity.likesDelta) + "   Techoins");
			totalTechions.setText(String.valueOf(MineActivity.totalDelta) +  "   Techoins");
			
			/* need to set ports comments and likes num  here */
		}
	}

	private void initiateNumViews() {
		postNum = (TextView) inflateView.findViewById(R.id.rec_account_number_posts);
		commentsNum = (TextView) inflateView.findViewById(R.id.rec_account_number_comments);
		likesNum = (TextView) inflateView.findViewById(R.id.rec_account_number_likes);

	}
	
	private void initiateValueViews(){
		postValue = (TextView) inflateView.findViewById(R.id.rec_account_current_posts);
		commentsValue = (TextView) inflateView
				.findViewById(R.id.rec_account_current_comments);
		likesValue = (TextView) inflateView.findViewById(R.id.rec_account_current_likes);
		totalTechions = (TextView) inflateView.findViewById(R.id.rec_account_current_total);
	}

}

