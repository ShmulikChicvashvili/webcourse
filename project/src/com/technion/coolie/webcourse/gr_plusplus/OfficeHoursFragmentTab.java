package com.technion.coolie.webcourse.gr_plusplus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;



public class OfficeHoursFragmentTab extends SherlockFragment {
    
	String mMessage;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
		View rootView = inflater.inflate(R.layout.web_office_hours_fragment, container, false);
		
		TextView tvOfficeHours = (TextView) rootView.findViewById(R.id.office_hours_fragment_id);
		tvOfficeHours.setText(mMessage);
		
        return rootView;
    }
	
	public void SetIntentMessage (String message) {
		
		mMessage = message;
	}
 
}
