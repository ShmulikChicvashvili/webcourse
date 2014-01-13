package com.technion.coolie.tecmind;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.LayoutParams;
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
	TextView month; 
	TextView day;
	TextView totalTechions;
	View inflateView;
	LayoutInflater inflater;
	FrameLayout f;
	
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		inflateView = (LinearLayout) inflater.inflate(
				R.layout.techmind_activity_recent_account, container, false);
		iniateViews();		
		setPopUp();
		setViewVal();

		

		return inflateView;
	}
	
	private void iniateViews() {
		postNum = (TextView) inflateView.findViewById(R.id.rec_account_number_posts);
		commentsNum = (TextView) inflateView.findViewById(R.id.rec_account_number_comments);
		likesNum = (TextView) inflateView.findViewById(R.id.rec_account_number_likes);	
		totalTechions = (TextView) inflateView.findViewById(R.id.rec_account_current_total);
		month = (TextView) inflateView.findViewById(R.id.rec_date_mon);
		day = (TextView) inflateView.findViewById(R.id.rec_date_day);
	}
	
	private void setViewVal() {
		if (MineActivity.newMiningDate == null) {
			postNum.setText("0");
			commentsNum.setText("0");
			likesNum.setText("0");
			totalTechions.setText("0 Techoins");
		} else {
			postNum.setText(String.valueOf(MineActivity.postsDelta));
			commentsNum.setText(String.valueOf(MineActivity.commentsDelta));
			likesNum.setText(String.valueOf(MineActivity.likesDelta));
			totalTechions.setText(String.valueOf(MineActivity.totalDelta) +  " Techoins");
			
		}
		
		try {
			MineDateView.getInstance().setDateView(MineActivity.exMiningDate, month, day);
		} catch (Exception e) {
			day.setText("N/A");
		}
		
		
	}
	
	void setPopUp() {
	
		final Popup ourNicePopup = new Popup(getActivity());
		ArrayList<PopUpItem> items = new ArrayList<PopUpItem>();
		HashMap<String, String> postsGroups = Mine.getMineInstance(null).getPostsGroupsNames();
		HashMap<String, String> postsUrls = Mine.getMineInstance(null).getPostsUrls();
		HashMap<String, Date> postsDates = Mine.getMineInstance(null).getPostsDates();
		HashMap<String, String> postsContent = Mine.getMineInstance(null).getPostsContent();
		int i = 0;
		
		for (String key : postsGroups.keySet()) {
			PopUpItem item = new PopUpItem(i, postsContent.get(key), postsDates.get(key),
					key , postsGroups.get(key));
			items.add(item);
			i++;
		}
		
        ourNicePopup.addItems(items);
     
        ourNicePopup.setOnItemClickListener(new OnPopupItemClickListener() {
  			@Override
  			public void onItemClick(String postId, PopUpItem item) {
  				//String s = "https://m.facebook.com/251685474991614";
  				String urlStr = "https://m.facebook.com/" + postId.split("_")[1];

			   try {
			    startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr)));
			   } catch (Exception e) {
				   try {
					   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("m.facebook.com")));
				   }
				   catch (Exception ex) {
					   
				   }
			   }  				
				   //startActivity(new Intent(getActivity(), activityClass));
//  			  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
 // 			  startActivity(myIntent);
  				
  			}
  		});
        
          
          inflateView.findViewById(R.id.rec_account_posts_layout).setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				if (Integer.parseInt(postNum.getText().toString()) > 0) {
  					ourNicePopup.show(v);
  				}
  				
  			}
  		});
          
      } 

private void initiateNumViews() {
	postNum = (TextView) inflateView.findViewById(R.id.rec_account_number_posts);
	commentsNum = (TextView) inflateView.findViewById(R.id.rec_account_number_comments);
	likesNum = (TextView) inflateView.findViewById(R.id.rec_account_number_likes);

}
	
	
}