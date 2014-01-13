package com.technion.coolie.tecmind;

import java.util.ArrayList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technion.coolie.R;

public class GraphFragment extends Fragment  {
	View inflateView;
	ImageView imView;
	LayoutInflater inflater;
	
	
    int count[] = { 20,10,25,5,15,25};
    String groups[] = { "item 1", "item 2", "item 3", "item 4", "item 5","item 6"};
    
    PieChart pieChart =null;
    
    float x;
    float y;
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		  this.inflater = inflater;
	  
		   inflateView = (LinearLayout)inflater.inflate(
					R.layout.techmind_activity_total_graph, container, false);
		   
		   imView = (ImageView)inflateView.findViewById(R.id.pie);
		   
		   DisplayMetrics metrics = new DisplayMetrics();
		   getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		   			
		   String[] colorsStr = inflater.getContext().getResources().getStringArray(R.array.techmindColors);
		   int[] colors = {Color.parseColor(colorsStr[0]), Color.parseColor(colorsStr[1]), Color.parseColor(colorsStr[2]),
				   Color.parseColor(colorsStr[3]), Color.parseColor(colorsStr[4]), Color.parseColor(colorsStr[5]),
				   Color.parseColor(colorsStr[6]), Color.parseColor(colorsStr[7])};
		   
		   int dely = (metrics.heightPixels - imView.getHeight()) / 60;
		   int delx = (metrics.widthPixels - imView.getWidth()) / 10;
		   		   
		   pieChart = new PieChart(groups, imView, count, colors, delx, dely);
		   imView.setImageDrawable(pieChart);
	   

			imView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					x = event.getX();
					y = event.getY();
					return false;
				}
		   });
			
			imView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {

					//float x = event.getX(), y = event.getY();
					String text = pieChart.getTouchedGroupName(x, y);
					
					if (text == "") {
						return false;
					}

					final GraphPopUp popup = new GraphPopUp(getActivity());
					ArrayList<GraphPopUpItem> items = new ArrayList<GraphPopUpItem>();
					GraphPopUpItem item = new GraphPopUpItem(0, text, 0);
					items.add(item);


			        popup.show(imView, text, x/2, y*2);

					return true;
				}
				
			});
		   
	       return inflateView;
	    }

	  }
