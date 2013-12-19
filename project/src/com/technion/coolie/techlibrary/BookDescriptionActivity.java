package com.technion.coolie.techlibrary;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.R.layout;
import com.technion.coolie.techlibrary.BookItems.HoldElement;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDescriptionActivity extends CoolieActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] data = getIntent().getExtras().getStringArray("description");
		setContentView(R.layout.lib_activity_book_description);
		((TextView) findViewById(R.id.lib_book_name)).setText(data[0]);
		((TextView) findViewById(R.id.lib_book_author)).setText(data[1]);
		((TextView) findViewById(R.id.lib_book_library)).setText(data[2]);
	}

}
