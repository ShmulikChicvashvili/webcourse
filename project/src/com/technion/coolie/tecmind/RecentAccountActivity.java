package com.technion.coolie.tecmind;

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
import android.os.AsyncTask;
import android.os.Bundle;
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
		initiateNumViews();
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
		
		setDateViewVal();
		
		
	}
	
	private void setDateViewVal() {
		
			try {
				Map<Integer, String> monthMap = new HashMap<Integer, String>();
				monthMap.put(1, "Jan");
				monthMap.put(2, "Fab");
				monthMap.put(3, "Mar");
				monthMap.put(4, "Apr");
				monthMap.put(5, "May");
				monthMap.put(6, "Jun");
				monthMap.put(7, "Jul");
				monthMap.put(8, "Aug");
				monthMap.put(9, "Sep");
				monthMap.put(10, "Oct");
				monthMap.put(11, "Nov");
				monthMap.put(12, "Dec");
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(MineActivity.exMiningDate);
				int monthInt = cal.get(Calendar.MONTH);
				Integer dayInt = cal.get(Calendar.DAY_OF_MONTH);
				
				month.setText(monthMap.get(monthInt+1));
				day.setText(dayInt.toString());
			} catch (Exception e) {
				day.setText("You haven't mined yet");
				day.setTextSize(12);
			}	
		
	}
	
	
	private void setValueTexts() {
		
		if (MineActivity.newMiningDate == null) {
			postNum.setText("0");
			commentsNum.setText("0");
			likesNum.setText("0");
		} else {
			postNum.setText(String.valueOf(MineActivity.postsDelta));
			commentsNum.setText(String.valueOf(MineActivity.commentsDelta));
			likesNum.setText(String.valueOf(MineActivity.likesDelta));
			totalTechions.setText(String.valueOf(MineActivity.totalDelta) +  "   Techoins");
			
			/* need to set ports comments and likes num  here */
		}
	}

	private void initiateNumViews() {
		postNum = (TextView) inflateView.findViewById(R.id.rec_account_number_posts);
		commentsNum = (TextView) inflateView.findViewById(R.id.rec_account_number_comments);
		likesNum = (TextView) inflateView.findViewById(R.id.rec_account_number_likes);

	}
	

	void setPopUp() {
		
	    PopUpItem item1 = new PopUpItem(0, "RUN ACTIVITY ONE", MineActivity.class);
	    PopUpItem item2 = new PopUpItem(1, "RUN ACTIVITY TWO", MainActivity.class);
	    PopUpItem item3 = new PopUpItem(2, "RUN ACTIVITY THREE", MainActivity.class);
        
        final Popup ourNicePopup = new Popup(getActivity());

        ArrayList<PopUpItem> items = new ArrayList<PopUpItem>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item3);
        items.add(item3);
        items.add(item3);
        items.add(item3);
        items.add(item3);
        items.add(item3);
        ourNicePopup.addItems(items);
     
        ourNicePopup.setOnItemClickListener(new OnPopupItemClickListener() {
  			@Override
  			public void onItemClick(Class<?> activityClass, int itemId) {
  				startActivity(new Intent(getActivity(), activityClass));
  			}
  		});
          
          inflateView.findViewById(R.id.rec_account_posts_layout).setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				ourNicePopup.show(v);
  				
  			}
  		});
          
      }  
}


