package com.technion.coolie.ug;

import android.content.Intent;
import android.os.Bundle;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.ug.gradessheet.GradesSheetActivity;

public class MainActivity extends CoolieActivity {

	public final static String DEBUG_TAG = "coolie.technion.ug";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// startActivity(new Intent(this, SearchActivity.class));
		startActivity(new Intent(this, GradesSheetActivity.class));
	}
}
