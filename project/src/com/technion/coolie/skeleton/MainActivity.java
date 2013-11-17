package com.technion.coolie.skeleton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.technion.coolie.R;

import com.technion.coolie.CoolieActivity;

public class MainActivity extends CoolieActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skel_activity_main); 

        ViewStub btn;
		LinearLayout buttonLayout;
        for(int i=1; i<=10; i++)
        {
        	btn = (ViewStub) findViewById(HelpFunctions.findResourceByName("skel_btn"+i, R.id.class));

        	LayoutParams lp = (LayoutParams) btn.getLayoutParams();
    		buttonLayout = (LinearLayout) btn.inflate();
        	buttonLayout.setLayoutParams(lp);
        	
        	((ImageView)(buttonLayout.findViewById(R.id.skel_image)))
        		.setImageResource(HelpFunctions.findResourceByName("skel_module"+i, R.drawable.class));
        	
        	final TextView tv =  	((TextView)(buttonLayout.findViewById(R.id.skel_title)));
        	tv.setText(HelpFunctions.findResourceByName("module"+i+"_name", R.string.class));

        	buttonLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 try {
						 String actName ="com.technion.coolie." + tv.getText().toString().toLowerCase().replaceAll("\\s+","")+".MainActivity";
						 Log.v("ACTIVITY!", actName);
						 Class<?> c = Class.forName(actName);
						 Intent intent = new Intent(MainActivity.this, c);
						 startActivity(intent);
						 
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
        	
        	String desc;
        	String name = getResources().getResourceName(buttonLayout.getId());
        	if(name.startsWith("com.technion.coolie:id/small"))
        		desc = getString(HelpFunctions.findResourceByName("module"+i+"_short_description", R.string.class));
        	else
        		desc = getString(HelpFunctions.findResourceByName("module"+i+"_long_description", R.string.class));
        	((TextView)(buttonLayout.findViewById(R.id.skel_news_text))).setText(desc);
        }
    }    
}