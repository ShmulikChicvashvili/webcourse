package com.technion.coolie.skeleton;

import android.os.Bundle;
import android.widget.GridView;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skel_alphabetical);
        
        GridView gridview = (GridView) findViewById(R.id.skel_alphabetical_grid);
		gridview.setAdapter(new AlphabeticalModulesAdapter(this));
	}
}
