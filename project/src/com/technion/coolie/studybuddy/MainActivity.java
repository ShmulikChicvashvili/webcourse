package com.technion.coolie.studybuddy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Adapters.CourseAdapter;
import com.technion.coolie.studybuddy.Views.NowLayout;
import com.technion.coolie.studybuddy.Views.TasksActicity;

public class MainActivity extends CoolieActivity
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.technion.coolie.CoolieActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stb_view_main);
		
		ImageView imageView = (ImageView) findViewById(R.id.graph_view);
		imageView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				v.getContext().startActivity(
						new Intent(v.getContext(), TasksActicity.class));
			}
		});
		NowLayout layout = (NowLayout) findViewById(R.id.course_list);
	
		CourseAdapter adapter = new CourseAdapter(this);
		layout.setAdapter(adapter);
	}

}
