package com.technion.coolie.ug;

import android.os.Bundle;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.ug.db.UGDatabase;

public class MainActivity extends CoolieActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UGDatabase.INSTANCE.getCourseById("23513");

	}

}
