package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class CoursesTrackingFragment extends CoolieActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_tracking_list);

		final Button registrationButton = (Button) findViewById(R.id.ug_button_registrate);
		registrationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				try {
					final HttpResponse h = new UGHttpGet().execute().get();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

		final ListView listview = (ListView) findViewById(R.id.ug_tracking_list);

		final String[] values = new String[] { "11111", "234503", "234141",
				"234123", "234503", "234141", "234123", "234503", "234141",
				"234123", "234503", "234141", "234123", "234503", "234141",
				"234123", "234503", "234141", "234123", "234503", "234141",
				"234123", "234503", "234141", "234123", "234503", "234141",
				"234123", "234503", "234141", "234123", "234503", "234141",
				"234123", "234503", "23414" };

		final List<String> list = new ArrayList<String>();
		for (final String value : values)
			list.add(value);

		// final TrackingListAdapter adapter = new TrackingListAdapter(this,
		// list);
		// listview.setAdapter(adapter);
	}

}
