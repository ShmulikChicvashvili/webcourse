package com.technion.coolie.ug;

import android.os.Bundle;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {

	public static final String DEBUG_TAG = "DEBUG";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ug_main_screen_tablet_vertical);
        
//		startActivity(new Intent(this, SearchActivity.class));
//		startActivity(new Intent(this, GradesSheetActivity.class));
	}
	

 
}
