package com.technion.coolie.tecmind;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;

public class TotalAccountActivity extends SherlockFragment {
	
	View inflateView;
	ViewPager pager;
	FragmentPager pagerAdapter;
	 private static final Field sChildFragmentManagerField;
	
	 static {
	        Field f = null;
	        try {
	            f = Fragment.class.getDeclaredField("mChildFragmentManager");
	            f.setAccessible(true);
	        } catch (NoSuchFieldException e) {
	            Log.e("LOGTAG", "Error getting mChildFragmentManager field", e);
	        }
	        sChildFragmentManagerField = f;
	    }
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		inflateView = (LinearLayout)inflater.inflate(
				R.layout.techmind_activity_total, container, false);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, metrics.heightPixels * 7 / 13);
		
		LinearLayout pagerView = (LinearLayout) inflateView.findViewById(R.id.pager_lay);
		pagerView.setLayoutParams(params);
		pagerView.setGravity(Gravity.CENTER);
		
		pager = (ViewPager) inflateView.findViewById(R.id.pager);

	    FragmentManager fm = getChildFragmentManager();
	   	    
	    pagerAdapter = new FragmentPager(fm);

	    pager.setAdapter(pagerAdapter);
	    pager.setCurrentItem(0);
	
    	return inflateView;
	   
			
		
		/*
		inflateView = (LinearLayout)inflater.inflate(
				R.layout.techmind_activity_total_account_new, container, false);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, LayoutParams.MATCH_PARENT);

		
		LinearLayout icon1 = (LinearLayout) inflateView.findViewById(R.id.tot_cntrs_layout);
		icon1.setLayoutParams(params);

		LinearLayout icon2 = (LinearLayout) inflateView.findViewById(R.id.bla);
		icon2.setLayoutParams(params);
		
		final int w = metrics.widthPixels;
		final int windowWidth = params.width;
		CenterHorizontalScrollView hscView = (CenterHorizontalScrollView) 
				inflateView.findViewById(R.id.tot_scroll_layout);
		
		hscView.setScrollListener(new OnScrollListener(){
			int currX = 0;
			
			public void onScrollChanged(CenterHorizontalScrollView scrollView, int x, int y, int oldX, int oldY) {
				
				int newOffX =  (x + (w / 2)) / w;
				if (Math.abs(x-oldX) < 5) {
					scrollView.smoothScrollTo(newOffX*w, y);
					currX = newOffX;
				}
			}
		});
		
		*/
		//int scrollX = 
		/*
		
		
		HorizontalScrollView hscView = (HorizontalScrollView) inflateView.findViewById(R.id.tot_scroll_layout);
		LinearLayout ll = (LinearLayout)inflateView.findViewById(R.id.tot_layout);
		int halfScreen = (ll.getRight()+ll.getLeft()) / 2;
		int svCenter = (hscView.getRight() +hscView.getLeft()) / 2;
		int newX = (svCenter + halfScreen) / 2;
		
		hscView.scrollTo(newX, 0);
*/
	/*	initiateNumViews();
		initiateValueViews();
		
		int userPost = User.getUserInstance(null).postsNum;
		int userComments = User.getUserInstance(null).commentsNum;
		int userLikes = User.getUserInstance(null).likesNum + User.getUserInstance(null).likesOnPostsNum;
		setNumTexts(userPost, userComments, userLikes);
		setValueTexts(userPost, userComments, userLikes);
	 	*/

	}

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e("LOGTAG", "Error setting mChildFragmentManager field", e);
            }
        }
    }

	
	public Fragment getFragmentIdx(int position) {

		FragmentManager fm = getActivity().getSupportFragmentManager();
	    Fragment f = fm.findFragmentByTag("android:switcher:" + 
	    		pager.getId() + ":" + pagerAdapter.getItemId(position));
	    return f;
	}
	
}
