package com.technion.coolie.letmein;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class DemoActivity extends CoolieActivity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_demo);

		ImageView i = (ImageView) findViewById(R.id.lmi_demo_image);
		i.setBackgroundResource(R.drawable.lmi_demo_animation_list);
		AnimationDrawable anim = (AnimationDrawable) i.getBackground();

		// Make the images swap smoothly requires api >= 11.
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			anim.setEnterFadeDuration(500);
			anim.setExitFadeDuration(500);
		}

		anim.start();
	}
}
