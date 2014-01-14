package com.technion.coolie.tecmind;

import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.technion.coolie.R;

public class GraphPopUp {
	 
	private PopupWindow popupWindow;
	private LinearLayout rootView;
	private LayoutInflater lInf;
	
	public GraphPopUp(Context context) {
		super();
		this.popupWindow = new PopupWindow(context);
		lInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = (LinearLayout) lInf.inflate(R.layout.techmind_graph_popup, null);
	}

	
	public void show(View v, String txt, float x, float y)	{
		

		TextView tv = (TextView) rootView.findViewById(R.id.graph_gp_name);
		tv.setText(txt);
		
		popupWindow.setFocusable(true);
		popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setContentView(rootView);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (int)x, (int)y);
		
		
	}
		
}