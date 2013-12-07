package com.technion.coolie.ug;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {

	public static final String DEBUG_TAG = "DEBUG";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.try2);
		
		//startActivity(new Intent(this, SearchActivity.class));
	}
 
}
